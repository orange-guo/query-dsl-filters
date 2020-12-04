package club.geek66.querydsl;

import com.querydsl.core.types.Constant;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import lombok.experimental.UtilityClass;

import java.util.Set;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:47
 * @copyright: Copyright 2020 by orange
 */
@UtilityClass
public class FilterUtils {

	public BooleanExpression generateExpression(EntityPathBase<?> entityPathBase, Set<PropertyFilter> filters) {
		return filters.stream().map(filter -> {
			Path<?> subPath = PathUtils.getSubPath(entityPathBase, filter.getProperty()).get();
			return (BooleanExpression) Expressions.booleanOperation(Ops.valueOf(filter.getOperator()), subPath, create(entityPathBase, filter));
		}).reduce(Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1)), BooleanExpression::and);
	}

	private Constant<?> create(EntityPathBase<?> rootPath, PropertyFilter filter) {
		Object newVal = filter.getValue();
		try {
			Path<?> path = PathUtils.getSubPath(rootPath, filter.getProperty()).get();
			Class<?> type = path.getType();
			if (Enum.class.isAssignableFrom(type) && filter.getValue() instanceof String) {
				return ConstantImpl.create(Enum.valueOf((Class) type, (String) filter.getValue()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ConstantImpl.create(newVal);
	}

}
