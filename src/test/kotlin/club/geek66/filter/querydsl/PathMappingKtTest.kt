package club.geek66.filter.querydsl

import arrow.core.Nel
import club.geek66.filter.integration.QUser.user
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 *
 * @author: orange
 * @date: 2021/1/25
 * @time: 上午11:21
 * @copyright: Copyright 2021 by orange
 */
internal class PathMappingKtTest {

	@Test
	fun pathBindingTest() {

		data class UserDto(val jobIndustryName: String)

		val binding = PathMapper(
			src = Nel(UserDto::jobIndustryName),
			dst = user.job.industry.name
		)
		assertEquals("jobIndustryName", binding.showSource())
		assertEquals("job.industry.name", binding.showTarget())
	}

}