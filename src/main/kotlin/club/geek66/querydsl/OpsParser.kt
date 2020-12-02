package club.geek66.querydsl

import arrow.core.Either
import com.querydsl.core.types.Ops

/**
 *
 * @author: orange
 * @date: 2020/12/2
 * @time: 下午5:43
 * @copyright: Copyright 2020 by orange
 */
object OpsParser {

	class NoSuchOpsError(val ops: String)

	fun parseOps(filter: PropertyFilter): Either<NoSuchOpsError, Ops> =
		try {
			filter.operator.toUpperCase().let(Ops::valueOf).let(Either.Companion::right)
		} catch (ex: IllegalArgumentException) {
			Either.left(NoSuchOpsError(filter.operator))
		}


}