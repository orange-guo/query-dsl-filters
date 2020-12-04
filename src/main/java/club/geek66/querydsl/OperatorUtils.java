package club.geek66.querydsl;

import com.querydsl.core.types.Ops;
import io.vavr.collection.HashMap;
import io.vavr.control.Either;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;


/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午4:11
 * @copyright: Copyright 2020 by orange
 */
@UtilityClass
public class OperatorUtils {

	@Data
	@RequiredArgsConstructor
	static class ParseOpsError {

		private final String msg;

	}

	public Either<ParseOpsError, Ops> parseOps(String operator) {
		return aliasMap.get(operator).toEither(new ParseOpsError("Nonsupport operator " + operator));
	}

	private final HashMap<String, Ops> aliasMap = HashMap.of(
			">", Ops.GT, "GT", Ops.GT,
			"<", Ops.LT, "LT", Ops.LT,
			">=", Ops.GOE, "GOE", Ops.GOE,
			"<=", Ops.LOE, "LOE", Ops.LOE,
			"==", Ops.EQ, "EQ", Ops.EQ
	).put("!=", Ops.NE).put("NE", Ops.NE);

}
