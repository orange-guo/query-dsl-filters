package club.geek66.filter

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