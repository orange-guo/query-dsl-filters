package club.geek66.querydsl

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.k
import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.BeanPath

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午3:45
 * @copyright: Copyright 2020 by orange
 */
object PathHandler {

	fun getSubPath(rootPath: BeanPath<*>, propExpression: String): Either<FindSubPathError, Path<*>> {
		val path: Either<FindSubPathError, Path<*>> = when {
			rootPath.root.type != rootPath.type -> Either.left(FindSubPathError("Path is not root"))
			else -> Either.right(rootPath as Path<*>)
		}

		return splitExpression(propExpression).foldLeft(path) { parentE, prop: String ->
			parentE.flatMap { parent ->
				val parentClazz: Class<*> = parent.javaClass
				try {
					val field = parentClazz.getField(prop)

					Either.right(field[parent] as Path<*>)
				} catch (_: NoSuchFieldException) {
					Either.left(FindSubPathError("No such property " + prop + " in clazz " + parentClazz.name))
				} catch (_: IllegalAccessException) {
					Either.left(FindSubPathError("Private property " + prop + " in clazz " + parentClazz.name))
				} catch (_: ClassCastException) {
					Either.left(FindSubPathError("Wrong property " + prop + "in clazz " + parentClazz.name + ", It must be Path instance"))
				} catch (ex: Exception) {
					Either.left(FindSubPathError("Unknown exception when get property from clazz " + parentClazz.name + ", detail is " + ex.message))
				}
			}
		}
	}

	private fun splitExpression(propExpression: String) = propExpression.split("\\.".toRegex()).k()


	data class FindSubPathError(val msg: String)

}