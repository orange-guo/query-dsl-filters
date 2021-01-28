package club.geek66.querydsl

import arrow.core.Either
import arrow.core.ListK
import arrow.core.computations.either
import arrow.core.extensions.list.foldable.foldLeft
import arrow.core.flatMap
import arrow.core.k
import arrow.syntax.function.invoke
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Path
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
data class PathError(val msg: String)

fun filterRootPath(entityPath: EntityPathBase<*>): Either<PathError, EntityPathBase<*>> =
	when {
		entityPath.run { root.type != type } -> Either.left(PathError("Path $entityPath is not root"))
		else -> Either.right(entityPath)
	}

fun getProperty(entityPath: EntityPathBase<*>, property: String): Either<PathError, Path<*>> =
	runCatching {
		Either.right(entityPath::class.java.getField(property)[entityPath] as Path<*>)
	}.getOrElse { ex ->
		when (ex) {
			is NoSuchFieldException -> Either.left(PathError("No such property $property in class ${entityPath::class.java}"))
			is IllegalAccessException -> Either.left(PathError("Private property $property in class ${entityPath::class.java} "))
			is ClassCastException -> Either.left(PathError("Wrong property $property in clazz ${entityPath::class.java}, It must be instance of Path"))
			else -> Either.left(PathError("Unknown exception when get property $property from clazz ${entityPath::class.java}, detail is ${ex.message}"))
		}
	}

fun getProperty(entityPath: Path<*>, property: String): Either<PathError, Path<*>> =
	when (entityPath) {
		is EntityPathBase<*> -> getProperty(entityPath, property)
		else -> Either.left(PathError("Property $property in $entityPath is not a entity path"))
	}

fun getProperty(entityPath: EntityPathBase<*>, propertyLevel: List<String>): Either<PathError, Path<*>> =
	propertyLevel.foldLeft(filterRootPath(entityPath) as Either<PathError, Path<*>>) { entity, property ->
		entity.flatMap { getProperty(it, property) }
	}


fun replacePathAliases(pathAliases: Map<String, String>, expressionFilters: Set<PathExpFilter>): Set<PathExpFilter> =
	(::replaceAlias)(pathAliases).let { replacePathAlias ->
		expressionFilters.map {
			it.copy(path = replacePathAlias(it.path))
		}.toSet()
	}

fun replaceAlias(aliasMap: Map<String, String>, alias: String): String =
	aliasMap[alias] ?: alias

val pathExpSplit: Regex = "\\.".toRegex()

fun splitLevel(path: String): ListK<String> = path.split(pathExpSplit).k()

fun convertSingle(entityPath: EntityPathBase<*>, expressionFilter: PathExpFilter): Either<PathError, QueryDslPathExpFilter> =
	runBlocking {
		either {
			QueryDslPathExpFilter(
				path = getProperty(entityPath, splitLevel(expressionFilter.path)).bind(),
				operator = fromAlias(expressionFilter.operator).mapLeft { PathError("This operator ${expressionFilter.operator} is unsupported") }.bind(),
				value = ConstantImpl.create(expressionFilter.value)
			)
		}
	}


// TODO support filter types
//  Number, Date, String, Boolean, Enumeration, Collection ...
fun convertFilters(entityPath: EntityPathBase<*>, expressionFilters: Set<PathExpFilter>): Set<Either<PathError, QueryDslPathExpFilter>> =
	(::convertSingle)(entityPath).let(expressionFilters::map).toSet()

fun QueryDslPathExpFilter.toOperation(): BooleanOperation = Expressions.booleanOperation(operator, path, value)


// annotation class Mapper(val value: Path<*>)

//@Suppress(names = ["UNCHECKED_CAST"])
//private fun create(rootPath: EntityPathBase<*>, filter: PathFilter): Constant<*> =
//	getSubPath(rootPath, filter.path).orNull()!!.let {
//		when {
//			Enum::class.java.isAssignableFrom(it.type) && filter.value is String -> ConstantImpl.create(java.lang.Enum.valueOf(it.type as Class<Enum<*>>, filter.value))
//			else -> ConstantImpl.create(filter.value)
//		}
//	}