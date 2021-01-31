package club.geek66.filter.querydsl

import club.geek66.filter.Maybe
import com.querydsl.core.types.Ops

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午4:11
 * @copyright: Copyright 2020 by orange
 */
fun parseOps(ops: String): Maybe<Ops> =
	Maybe.fromNullable(operatorMap[ops])

val operatorMap = mapOf(
	">" to Ops.GT, "GT" to Ops.GT,
	"<" to Ops.LT, "LT" to Ops.LT,
	">=" to Ops.GOE, "GOE" to Ops.GOE,
	"<=" to Ops.LOE, "LOE" to Ops.LOE,
	"=" to Ops.EQ, "EQ" to Ops.EQ,
	"!=" to Ops.NE, "NE" to Ops.NE,
	"LIKE" to Ops.LIKE, "ILIKE" to Ops.LIKE_IC
)