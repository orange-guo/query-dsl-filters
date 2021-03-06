package club.geek66.filter.querydsl

import arrow.core.nel
import club.geek66.filter.integration.QUser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 *
 * @author: orange
 * @date: 2021/1/31
 * @time: 下午6:22
 * @copyright: Copyright 2021 by orange
 */
internal class PathBindingKtTest {

	@Test
	fun pathMapping() {
		data class UserDto(
			val jobIndustryName: String,
		)
		PathMapper(
			src = UserDto::jobIndustryName.nel(),
			dst = QUser.user.job.industry.name
		).let {
			assertEquals("jobIndustryName", it.showSource())
			assertEquals("job.industry.name", it.showTarget())
		}
	}

}