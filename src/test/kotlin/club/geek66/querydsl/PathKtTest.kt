package club.geek66.querydsl

import club.geek66.querydsl.db.QUser.user
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 *
 * @author: orange
 * @date: 2021/1/25
 * @time: 上午11:21
 * @copyright: Copyright 2021 by orange
 */
internal class PathKtTest {

	@Test
	fun withPathMap() {
		replacePathAliases(mapOf("addressId" to "address.id"), setOf(PathExpFilter("addressId", "=", 3))).let {
			Assertions.assertEquals(it.size, 1)
			Assertions.assertEquals(it.first(), PathExpFilter("address.id", "=", 3))
		}

		data class UserDto(val jobIndustryName: String)

		val mapping = QueryDslPathBinding(
			source = Nel(UserDto::jobIndustryName),
			target = user.job.industry.name
		)
		assertEquals("jobIndustryName", mapping.showSource())
		assertEquals("job.industry.name", mapping.showTarget())
	}

}