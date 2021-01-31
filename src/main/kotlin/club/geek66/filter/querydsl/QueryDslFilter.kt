package club.geek66.filter.querydsl

import com.querydsl.core.types.Constant
import com.querydsl.core.types.Ops
import com.querydsl.core.types.Path

/**
 *
 * @author: orange
 * @date: 2021/1/31
 * @time: 下午5:24
 * @copyright: Copyright 2021 by orange
 */
data class QueryDslFilter(
	val path: Path<*>,
	val operator: Ops,
	val value: Constant<*>? = null,
)