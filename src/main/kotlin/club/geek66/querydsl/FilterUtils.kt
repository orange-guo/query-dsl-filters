package club.geek66.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase

/**
 *
 * @author: orange
 * @date: 2020/12/2
 * @time: 下午8:56
 * @copyright: Copyright 2020 by orange
 */
object FilterUtils {

	inline fun <reified T> generateExpression(rootPath: EntityPathBase<T>, filters: Set<PropertyFilter>): BooleanExpression {
		TODO()
	}

}