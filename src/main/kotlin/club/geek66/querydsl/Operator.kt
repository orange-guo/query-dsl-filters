package club.geek66.querydsl

import arrow.core.Either
import com.querydsl.core.types.Ops

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午4:11
 * @copyright: Copyright 2020 by orange
 */

typealias Maybe<T> = Either<Unit, T>

fun fromAlias(operatorAlias: String): Maybe<Ops> =
	Maybe.fromNullable(operatorAliases[operatorAlias])

val operatorAliases = mapOf(
	">" to Ops.GT, "GT" to Ops.GT,
	"<" to Ops.LT, "LT" to Ops.LT,
	">=" to Ops.GOE, "GOE" to Ops.GOE,
	"<=" to Ops.LOE, "LOE" to Ops.LOE,
	"==" to Ops.EQ, "EQ" to Ops.EQ,
	"!=" to Ops.NE, "NE" to Ops.NE,
	"LIKE" to Ops.LIKE, "ILIKE" to Ops.LIKE_IC
)