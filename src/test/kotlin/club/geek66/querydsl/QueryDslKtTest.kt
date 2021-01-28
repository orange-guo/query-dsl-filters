package club.geek66.querydsl

import arrow.core.nel
import club.geek66.querydsl.db.QUser.user
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
		assertTrue(filterRootPath(user).isRight())
		assertTrue(filterRootPath(user.order).isLeft())
	}

	@Test
	fun getProperty() {
		getProperty(user, "country").let {
			assertTrue(it.isRight())
			assertEquals(it.orNull(), user.country)
		}
	}

	@Test
	fun getProperty1() {
	}

	@Test
	fun getProperty2() {
		getProperty(user, listOf("job", "industry", "name")).let {
			assertTrue(it.isRight())
			assertEquals(user.job.industry.name, it.orNull())
		}
	}

	@Test
	fun replacePathAliases() {
		assertEquals(setOf(PathExpFilter(path = "job.industry.name", operator = "EQ", value = "Teacher")), replacePathAliases(mapOf("jobIndustryName" to "job.industry.name"), setOf(PathExpFilter(path = "jobIndustryName", operator = "EQ", value = "Teacher"))))
	}

	@Test
	fun splitLevel() {
		assertEquals(listOf("job", "industry", "name"), splitLevel("job.industry.name"))
	}

	@Test
	fun convertSingle() {
		assertEquals(QueryDslPathExpFilter(user.name, Ops.EQ, ConstantImpl.create("Jack")), convertSingle(user, PathExpFilter("name", "EQ", "Jack")).orNull())
	}

	@Test
	fun pathMapping() {
		data class UserDto(
			val jobIndustryName: String,
		)
		QueryDslPathBinding(
			source = UserDto::jobIndustryName.nel(),
			target = user.job.industry.name
		).let {
			assertEquals("jobIndustryName", it.showSource())
			assertEquals("job.industry.name", it.showTarget())
		}
	}

	@Test
	fun convertFilters() {
	}

}