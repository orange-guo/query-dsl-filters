package club.geek66.querydsl

import com.querydsl.core.types.Constant
import com.querydsl.core.types.Ops
import com.querydsl.core.types.Path

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:52
 * @copyright: Copyright 2020 by orange
 */
data class PathFilter(
	val path: String,
	val operator: String,
	val value: Any? = null,
)

data class QueryDslPathFilter(
	val path: Path<*>,
	val operator: Ops,
	val value: Constant<*>? = null,
)