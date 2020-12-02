package club.geek66.querydsl

import arrow.core.Either

/**
 *
 * @author: orange
 * @date: 2020/12/2
 * @time: 下午5:43
 * @copyright: Copyright 2020 by orange
 */
object FilterConverter {

	sealed class ConvertError {

	}

	fun convert(filter: PropertyFilter): Either<ConvertError, Any> = filter.run {
		when {
			filterOperator == ">" || filterOperator == "GT" -> Either.right("")
			else -> Either.right("")
		}
	}


}