package club.geek66.querydsl

import arrow.core.Either
import arrow.core.ListK
import arrow.core.Nel
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
import kotlin.reflect.KProperty

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
		entityPath.root.type != entityPath.type -> Either.left(PathError("Path $entityPath is not root"))
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


fun replacePathAliases(pathAliases: Map<String, String>, filters: Set<PathFilter>): Set<PathFilter> =
	(::replaceAlias)(pathAliases).let { replacePathAlias ->
		filters.map {
			it.copy(path = replacePathAlias(it.path))
		}.toSet()
	}

fun replaceAlias(aliasMap: Map<String, String>, alias: String): String =
	aliasMap[alias] ?: alias

fun splitLevel(path: String): ListK<String> = path.split("\\.".toRegex()).k()

fun convertSingle(entityPath: EntityPathBase<*>, filter: PathFilter): Either<PathError, QueryDslPathFilter> =
	runBlocking {
		either {
			QueryDslPathFilter(
				path = getProperty(entityPath, splitLevel(filter.path)).bind(),
				operator = fromAlias(filter.operator).mapLeft { PathError("This operator ${filter.operator} is unsupported") }.bind(),
				value = ConstantImpl.create(filter.value)
			)
		}
	}


// TODO support filter types
//  Number, Date, String, Boolean, Enumeration, CoQllection ...
fun convertFilters(entityPath: EntityPathBase<*>, filters: Set<PathFilter>): Set<Either<PathError, QueryDslPathFilter>> =
	(::convertSingle)(entityPath).let(filters::map).toSet()

fun QueryDslPathFilter.toOperation(): BooleanOperation = Expressions.booleanOperation(operator, path, value)

data class PathMapping(
	val source: Nel<KProperty<*>>,
	val target: Path<*>,
)

fun PathMapping.sourcePath(): String = source.foldLeft("") { x, y ->
	if (x.isEmpty()) y.name else "$x.${y.name}"
}

fun PathMapping.targetPath(): String = target.toString()

// annotation class Mapper(val value: Path<*>)

//@Suppress(names = ["UNCHECKED_CAST"])
//private fun create(rootPath: EntityPathBase<*>, filter: PathFilter): Constant<*> =
//	getSubPath(rootPath, filter.path).orNull()!!.let {
//		when {
//			Enum::class.java.isAssignableFrom(it.type) && filter.value is String -> ConstantImpl.create(java.lang.Enum.valueOf(it.type as Class<Enum<*>>, filter.value))
//			else -> ConstantImpl.create(filter.value)
//		}
//	}