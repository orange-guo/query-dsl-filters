package club.geek66.filter.querydsl

import club.geek66.filter.db.QUser.user
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 *
 * @author: orange
 * @date: 2021/1/25
 * @time: 下午6:15
 * @copyright: Copyright 2021 by orange
 */
internal class EntityPathKtTest {

	@Test
	fun withIsRootPathObject() {
		assertTrue(filterRootPath(user).isRight())
		assertTrue(filterRootPath(user.order).isLeft())
	}

	@Test
	fun withGetProperty() {
		getProperty(user, "country").let {
			assertTrue(it.isRight())
			assertEquals(it.orNull(), user.country)
		}

		getProperty(user, listOf("job", "industry", "name")).let {
			assertTrue(it.isRight())
			assertEquals(user.job.industry.name, it.orNull())
		}
	}


}