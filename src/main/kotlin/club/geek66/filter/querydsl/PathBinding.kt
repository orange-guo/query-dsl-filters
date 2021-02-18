package club.geek66.filter.querydsl

import arrow.core.Nel
import arrow.core.extensions.StringMonoid
import arrow.core.extensions.list.foldable.fold
import com.querydsl.core.types.Path
import kotlin.reflect.KProperty

/**
 *
 * @author: orange
 * @date: 2021/1/28
 * @time: 下午4:58
 * @copyright: Copyright 2021 by orange
 */
data class PathMapper(
	val src: Nel<KProperty<*>>,
	val dst: Path<*>,
)

fun PathMapper.showSource(): String = src.map(KProperty<*>::name).fold(PathMonoid)

fun PathMapper.showTarget(): String = dst.toString().split(pathExpSplit).drop(1).fold(PathMonoid)

fun PathMapper.toAliasPair(): Pair<String, String> = Pair(showSource(), showTarget())

object PathMonoid : StringMonoid {

	override fun String.combine(b: String): String = takeIf(String::isNotEmpty)?.run { "$this.$b" } ?: b

}