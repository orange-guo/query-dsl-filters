package club.geek66.filter.querydsl

import club.geek66.filter.Maybe
import com.querydsl.core.types.Ops
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午4:13
 * @copyright: Copyright 2020 by orange
 */
internal class OpsKtTest {

	@Test
	fun withParse() {
		parseOps(">").let {
			Assertions.assertEquals(Maybe.right(Ops.GT), it)
		}
		parseOps("<").let {
			Assertions.assertEquals(Maybe.right(Ops.LT), it)
		}
		parseOps(">=").let {
			Assertions.assertEquals(Maybe.right(Ops.GOE), it)
		}
		parseOps("<=").let {
			Assertions.assertEquals(Maybe.right(Ops.LOE), it)
		}
		parseOps("xxx").let {
			Assertions.assertEquals(Maybe.left(Unit), it)
		}
	}

}