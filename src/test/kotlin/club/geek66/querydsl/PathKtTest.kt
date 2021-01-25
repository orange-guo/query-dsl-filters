package club.geek66.querydsl

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
		replacePathAliases(setOf(PathFilter("userAddressId", "=", 3)), mapOf("user.address.id" to "user.address.id")).let {
			Assertions.assertEquals(it.size, 1)
			Assertions.assertEquals(it.first(), PathFilter("user.address.id", "=", 3))
		}
	}

}