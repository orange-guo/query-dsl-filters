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
			val path = getProperty(entityPath, splitLevel(expFilter.path)).bind()
			val value = expFilter.value.let { value ->
				when {
					path.type.isEnum -> java.lang.Enum.valueOf((path.type as Class<Enum<*>>), value as String)
					else -> value
				}
			}.let {
				ConstantImpl.create(it)
			}
			QueryDslFilter(
				path = path,
				operator = parseOps(expFilter.operator).mapLeft { PathError("This operator ${expFilter.operator} is unsupported") }.bind(),
				value = value
			)
		}
	}

// TODO support filter types
//  Number, Date, String, Boolean, Enumeration, Collection ...
fun convertFilters(entityPath: EntityPathBase<*>, expressionFilters: Set<PathFilter>): Set<Either<PathError, QueryDslFilter>> =
	expressionFilters.map((::convertSingle)(entityPath)).toSet()


fun withReplacePathAliases(pathAliases: Map<String, String>, filters: Set<PathFilter>): Set<PathFilter> =
	filters.map { filter ->
		if (pathAliases[filter.path] != null) {
			filter.copy(path = pathAliases[filter.path]!!)
		} else {
			filter
		}
	}.toSet()

fun QueryDslFilter.toOperation(): BooleanOperation = Expressions.booleanOperation(operator, path, value)