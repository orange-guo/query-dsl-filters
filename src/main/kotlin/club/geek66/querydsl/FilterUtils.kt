package club.geek66.querydsl

import com.querydsl.core.types.Constant
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:47
 * @copyright: Copyright 2020 by orange
 */
object FilterUtils {

	fun generateExpression(entityPathBase: EntityPathBase<*>, filters: Set<PropertyFilter>): BooleanExpression {
		return filters.stream().map { filter: PropertyFilter ->
			val subPath: Path<*> = PathUtils.getSubPath(entityPathBase, filter.property).orNull()!!
			Expressions.booleanOperation(Ops.valueOf(filter.operator), subPath, create(entityPathBase, filter)) as BooleanExpression
		}.reduce(Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1)), BooleanExpression::and)
	}

	@Suppress
	private fun create(rootPath: EntityPathBase<*>, filter: PropertyFilter): Constant<*> {
		val newVal: Any? = filter.value
		try {
			val path: Path<*> = PathUtils.getSubPath(rootPath, filter.property).orNull()!!
			if (Enum::class.java.isAssignableFrom(path.type) && filter.value is String) {
				return ConstantImpl.create(java.lang.Enum.valueOf(path.type as Class<Enum<*>>, filter.value))
			}
		} catch (e: Exception) {
			throw RuntimeException(e)
		}
		return ConstantImpl.create<Any>(newVal)
	}
}