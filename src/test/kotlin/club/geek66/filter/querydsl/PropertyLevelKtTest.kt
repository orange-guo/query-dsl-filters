package club.geek66.filter.querydsl

import org.junit.jupiter.api.Test

/**
 *
 * @author: orange
 * @date: 2021/1/31
 * @time: 下午6:25
 * @copyright: Copyright 2021 by orange
 */
internal class PropertyLevelKtTest {

	@Test
	fun withSplitLevel() {
		kotlin.test.assertEquals(listOf("job", "industry", "name"), splitLevel("job.industry.name"))
	}

}