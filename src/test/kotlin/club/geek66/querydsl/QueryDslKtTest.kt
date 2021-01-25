package club.geek66.querydsl

import club.geek66.querydsl.db.QUser
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
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
internal class QueryDslKtTest {

	@Test
	fun isRootPathObject() {
		assertTrue(isRootPathObject(QUser.user).isRight())
		assertTrue(isRootPathObject(QUser.user.order).isLeft())
	}

	@Test
	fun getProperty() {
		getProperty(QUser.user, "country").let {
			assertTrue(it.isRight())
			assertEquals(it.orNull(), QUser.user.country)
		}
	}

	@Test
	fun getProperty1() {
	}

	@Test
	fun getProperty2() {
		getProperty(QUser.user, listOf("job", "industry", "name")).let {
			assertTrue(it.isRight())
			assertEquals(QUser.user.job.industry.name, it.orNull())
		}
	}

	@Test
	fun replacePathAliases() {
		assertEquals(setOf(PathFilter(path = "job.industry.name", operator = "EQ", value = "Teacher")), replacePathAliases(setOf(PathFilter(path = "jobIndustryName", operator = "EQ", value = "Teacher")), mapOf("jobIndustryName" to "job.industry.name")))
	}

	@Test
	fun splitLevel() {
		assertEquals(listOf("job", "industry", "name"), splitLevel("job.industry.name"))
	}

	@Test
	fun convertSingle() {
		assertEquals(QueryDslPathFilter(QUser.user.name, Ops.EQ, ConstantImpl.create("Jack")), convertSingle(QUser.user, PathFilter("name", "EQ", "Jack")).orNull())
	}

	@Test
	fun convertFilters() {
	}

}