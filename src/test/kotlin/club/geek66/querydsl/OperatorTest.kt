package club.geek66.querydsl

import com.querydsl.core.types.Ops
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午4:13
 * @copyright: Copyright 2020 by orange
 */
internal class OperatorTest {

	@Test
	fun testParseOps() {
		fromAlias(">").let {
			Assertions.assertEquals(Maybe.right(Ops.GT), it)
		}
		fromAlias("<").let {
			Assertions.assertEquals(Maybe.right(Ops.LT), it)
		}
		fromAlias(">=").let {
			Assertions.assertEquals(Maybe.right(Ops.GOE), it)
		}
		fromAlias("<=").let {
			Assertions.assertEquals(Maybe.right(Ops.LOE), it)
		}
		fromAlias("xxx").let {
			Assertions.assertEquals(Maybe.left(Unit), it)
		}
	}
}