package club.geek66.filter.querydsl

import arrow.core.ListK
import arrow.core.k

/**
 *
 * @author: orange
 * @date: 2021/1/31
 * @time: 下午6:17
 * @copyright: Copyright 2021 by orange
 */
val pathExpSplit: Regex = "\\.".toRegex()

fun splitLevel(path: String): ListK<String> = path.split(pathExpSplit).k()