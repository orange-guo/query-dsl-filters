package club.geek66.querydsl;

import com.querydsl.core.types.Ops;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午4:13
 * @copyright: Copyright 2020 by orange
 */
class OperatorUtilsTest {

	@Test
	public void testParseOps() {
		Either<OperatorUtils.ParseOpsError, Ops> gtResult = OperatorUtils.parseOps(">");
		Assertions.assertEquals(Either.right(Ops.GT), gtResult);

		Either<OperatorUtils.ParseOpsError, Ops> ltResult = OperatorUtils.parseOps("<");
		Assertions.assertEquals(Either.right(Ops.LT), ltResult);

		Either<OperatorUtils.ParseOpsError, Ops> goeResult = OperatorUtils.parseOps(">=");
		Assertions.assertEquals(Either.right(Ops.GOE), goeResult);

		Either<OperatorUtils.ParseOpsError, Ops> loeResult = OperatorUtils.parseOps("<=");
		Assertions.assertEquals(Either.right(Ops.LOE), loeResult);
	}

}