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
import com.querydsl.core.types.dsl.EntityPathBase

/**
 *
 * @author: orange
 * @date: 2021/1/25
 * @time: 下午5:26
 * @copyright: Copyright 2021 by orange
 */
data class PathError(val msg: String)

fun isRootPathObject(rootPath: EntityPathBase<*>): Either<PathError, EntityPathBase<*>> =
	when {
		rootPath.root.type != rootPath.type -> Either.left(PathError("Path $rootPath is not root"))
		else -> Either.right(rootPath)
	}

fun getProperty(entityPath: EntityPathBase<*>, property: String): Either<PathError, Path<*>> =
	entityPath::class.java.let { clazz ->
		try {
			Either.right(clazz.getField(property)[entityPath] as Path<*>)
		} catch (_: NoSuchFieldException) {
			Either.left(PathError("No such property " + property + " in clazz " + clazz.name))
		} catch (_: IllegalAccessException) {
			Either.left(PathError("Private property " + property + " in clazz " + clazz.name))
		} catch (_: ClassCastException) {
			Either.left(PathError("Wrong property " + property + "in clazz " + clazz.name + ", It must be Path instance"))
		} catch (ex: Exception) {
			Either.left(PathError("Unknown exception when get property from clazz " + clazz.name + ", detail is " + ex.message))
		}
	}

fun getProperty(entityPath: Path<*>, property: String): Either<PathError, Path<*>> =
	when (entityPath) {
		is EntityPathBase<*> -> getProperty(entityPath, property)
		else -> Either.left(PathError("Property $property in $entityPath is not a entity path"))
	}

fun getProperty(rootPath: EntityPathBase<*>, propertyLevel: List<String>): Either<PathError, Path<*>> =
	propertyLevel.foldLeft(isRootPathObject(rootPath) as Either<PathError, Path<*>>) { entity, property ->
		entity.flatMap { getProperty(it, property) }
	}


fun replacePath(filters: Set<PathFilter>, pathAliases: Map<String, String>): Set<PathFilter> =
	filters.map { it.copy(path = pathAliases[it.path] ?: it.path) }.toSet()

fun splitLevel(path: String): ListK<String> = path.split("\\.".toRegex()).k()

fun convert(rootPath: EntityPathBase<*>, filter: PathFilter): Either<PathError, QueryDslPathFilter> =
	either {
		QueryDslPathFilter(
			path = getProperty(rootPath, splitLevel(filter.path)).bind(),
			operator = fromAlias(filter.operator).mapLeft { PathError("This operator ${filter.operator} is unsupported") }.bind(),
			value = ConstantImpl.create(filter.value)
		)
	}


// TODO support filter types
//  Number, Date, String, Boolean, Enumeration, Collection ...
suspend fun convertPathFilter(rootPath: EntityPathBase<*>, filters: Set<PathFilter>): Set<Either<PathError, QueryDslPathFilter>> =
	(::convert)(rootPath).let(filters::map).toSet()


//@Suppress(names = ["UNCHECKED_CAST"])
//private fun create(rootPath: EntityPathBase<*>, filter: PathFilter): Constant<*> =
//	getSubPath(rootPath, filter.path).orNull()!!.let {
//		when {
//			Enum::class.java.isAssignableFrom(it.type) && filter.value is String -> ConstantImpl.create(java.lang.Enum.valueOf(it.type as Class<Enum<*>>, filter.value))
//			else -> ConstantImpl.create(filter.value)
//		}
//	}