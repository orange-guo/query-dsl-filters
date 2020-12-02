package club.geek66.querydsl

import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * @author: orange
 * @date: 2020/12/2
 * @time: 下午8:43
 * @copyright: Copyright 2020 by orange
 */
class OpsParserTest {

	@Test
	fun parseOps() {
		assertTrue(OpsParser.parseOps(PropertyFilter("name", "LIKE", "tom")).isRight())
		assertTrue(OpsParser.parseOps(PropertyFilter("name", "X", "tom")).isLeft())
	}

}