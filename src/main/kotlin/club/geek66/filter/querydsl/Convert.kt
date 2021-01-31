package club.geek66.filter.querydsl

import arrow.core.Either
import arrow.core.computations.either
import arrow.syntax.function.invoke
import club.geek66.filter.PathFilter
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.dsl.BooleanOperation
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions
import kotlinx.coroutines.runBlocking


/**
 *
 * @author: orange
 * @date: 2021/1/25
 * @time: 下午5:26
 * @copyright: Copyright 2021 by orange
 */
fun convertSingle(entityPath: EntityPathBase<*>, expFilter: PathFilter): Either<PathError, QueryDslFilter> =
	runBlocking {
		either {
			QueryDslFilter(
				path = getProperty(entityPath, splitLevel(expFilter.path)).bind(),
				operator = parseOps(expFilter.operator).mapLeft { PathError("This operator ${expFilter.operator} is unsupported") }.bind(),
				value = ConstantImpl.create(expFilter.value)
			)
		}
	}

// TODO support filter types
//  Number, Date, String, Boolean, Enumeration, Collection ...
fun convertFilters(entityPath: EntityPathBase<*>, expressionFilters: Set<PathFilter>): Set<Either<PathError, QueryDslFilter>> =
	(::convertSingle)(entityPath).let(expressionFilters::map).toSet()

fun replaceAlias(aliasMap: Map<String, String>, alias: String): String =
	aliasMap[alias] ?: alias

fun withReplacePathAliases(pathAliases: Map<String, String>, expressionFilters: Set<PathFilter>): Set<PathFilter> =
	(::replaceAlias)(pathAliases).let { replacePathAlias ->
		expressionFilters.map {
			it.copy(path = replacePathAlias(it.path))
		}.toSet()
	}

fun QueryDslFilter.toOperation(): BooleanOperation = Expressions.booleanOperation(operator, path, value)