package club.geek66.querydsl

import arrow.core.Either
import club.geek66.querydsl.OperatorUtils.parseOps
import com.querydsl.core.types.Ops
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午4:13
 * @copyright: Copyright 2020 by orange
 */
internal class OperatorUtilsTest {

	@Test
	fun testParseOps() {
		val gtResult = parseOps(">")
		Assertions.assertEquals(Either.right(Ops.GT), gtResult)
		val ltResult = parseOps("<")
		Assertions.assertEquals(Either.right(Ops.LT), ltResult)
		val goeResult = parseOps(">=")
		Assertions.assertEquals(Either.right(Ops.GOE), goeResult)
		val loeResult = parseOps("<=")
		Assertions.assertEquals(Either.right(Ops.LOE), loeResult)
	}
}