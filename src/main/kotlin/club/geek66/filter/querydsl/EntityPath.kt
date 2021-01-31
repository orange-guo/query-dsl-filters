package club.geek66.filter.querydsl

import arrow.core.Either
import arrow.core.extensions.list.foldable.foldLeft
import arrow.core.flatMap
import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.EntityPathBase

/**
 *
 * @author: orange
 * @date: 2021/1/31
 * @time: 下午5:23
 * @copyright: Copyright 2021 by orange
 */
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

fun filterRootPath(entityPath: EntityPathBase<*>): Either<PathError, EntityPathBase<*>> =
	when {
		entityPath.run { root.type != type } -> Either.left(PathError("Path $entityPath is not root"))
		else -> Either.right(entityPath)
	}