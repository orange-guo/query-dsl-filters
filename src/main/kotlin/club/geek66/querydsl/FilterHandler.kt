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
object FilterHandler {

	// 数字
	// 日期
	// 字符串
	// 布尔
	// 枚举
	// 集合
	fun generateFilterExpression(entityPath: EntityPathBase<*>, filters: Set<PropertyFilter>): BooleanExpression {
		return filters.stream().map { filter: PropertyFilter ->
			val subPath: Path<*> = PathHandler.getSubPath(entityPath, filter.property).orNull()!!
			Expressions.booleanOperation(Ops.valueOf(filter.operator), subPath, create(entityPath, filter)) as BooleanExpression
		}.reduce(Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1)), BooleanExpression::and)
	}

	@Suppress(names = ["UNCHECKED_CAST"])
	private fun create(rootPath: EntityPathBase<*>, filter: PropertyFilter): Constant<*> =
		PathHandler.getSubPath(rootPath, filter.property).orNull()!!.let {
			when {
				Enum::class.java.isAssignableFrom(it.type) && filter.value is String -> ConstantImpl.create(java.lang.Enum.valueOf(it.type as Class<Enum<*>>, filter.value))
				else -> ConstantImpl.create(filter.value)
			}
		}

}