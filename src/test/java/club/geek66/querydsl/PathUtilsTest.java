package club.geek66.querydsl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午3:46
 * @copyright: Copyright 2020 by orange
 */
class PathUtilsTest {

	@Test
	public void testGetPath() {
		var nameResult = PathUtils.getSubPath(QUser.user, "name");
		Assertions.assertTrue(nameResult.isRight());
		var titleResult = PathUtils.getSubPath(QUser.user, "order.title");
		Assertions.assertTrue(titleResult.isRight());
		var xxxResult = PathUtils.getSubPath(QUser.user, "xxx");
		Assertions.assertTrue(xxxResult.isLeft());
	}

}