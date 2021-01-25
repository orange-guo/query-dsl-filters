package club.geek66.querydsl

import club.geek66.querydsl.db.QUser
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 *
 * @author: orange
 * @date: 2021/1/25
 * @time: 下午6:15
 * @copyright: Copyright 2021 by orange
 */
internal class QueryDslKtTest {

	@Test
	fun isRootPathObject() {
		isRootPathObject(QUser.user).let {
			assertTrue(it.isRight())
		}
		isRootPathObject(QUser.user.order).let {
			assertTrue(it.isLeft())
		}
	}

	@Test
	fun getProperty() {

	}

	@Test
	fun testGetProperty() {
	}

	@Test
	fun testGetProperty1() {
	}

	@Test
	fun replacePath() {
	}

	@Test
	fun splitLevel() {
	}

	@Test
	fun convertPathFilter() {
	}

}