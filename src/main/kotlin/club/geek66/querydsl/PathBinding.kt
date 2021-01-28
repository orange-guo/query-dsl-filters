package club.geek66.querydsl

import arrow.core.Nel
import arrow.core.Tuple2
import arrow.core.getOrElse
import com.querydsl.core.types.Path
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

/**
 *
 * @author: orange
 * @date: 2021/1/28
 * @time: 下午4:58
 * @copyright: Copyright 2021 by orange
 */
data class QueryDslPathBinding(
	val source: Nel<KProperty<*>>,
	val target: Path<*>,
)

fun Nel<String>.toPath() = foldLeft("") { x, y -> if (x.isNotEmpty()) "$x.$y" else y }

fun QueryDslPathBinding.showSource(): String =
	source.map(KProperty<*>::name).let(Nel<String>::toPath)

fun QueryDslPathBinding.showTarget(): String =
	Nel.fromList(target.toString().split(pathExpSplit).drop(1)).map(Nel<String>::toPath).getOrElse { "" }


/*fun QueryDslPathBinding.validateSource(): String {
	source.map {
		Tuple2(it.javaField!!.declaringClass, it.returnType.javaClass)
	}
}*/

//
//fun validate(binding: QueryDslPathBinding): Either<PathError, QueryDslPathBinding> {
//
//}