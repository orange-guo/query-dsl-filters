package club.geek66.querydsl

/**
 *
 * @author: orange
 * @date: 2020/12/2
 * @time: 下午5:35
 * @copyright: Copyright 2020 by orange
 */
data class PropertyFilter(
	val property: String,
	val filterOperator: String,
	val value: String?
)
