package club.geek66.querydsl

import club.geek66.querydsl.PathUtils.getSubPath
import club.geek66.querydsl.db.QUser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午3:46
 * @copyright: Copyright 2020 by orange
 */
class PathUtilsTest {

	@Test
	fun testGetPath() {
		val nameResult = getSubPath(QUser.user, "name")
		Assertions.assertTrue(nameResult.isRight())
		val titleResult = getSubPath(QUser.user, "order.title")
		Assertions.assertTrue(titleResult.isRight())
		val xxxResult = getSubPath(QUser.user, "xxx")
		Assertions.assertTrue(xxxResult.isLeft())
	}

}