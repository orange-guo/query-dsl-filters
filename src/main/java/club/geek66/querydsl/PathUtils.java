package club.geek66.querydsl;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BeanPath;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午3:45
 * @copyright: Copyright 2020 by orange
 */
@UtilityClass
public class PathUtils {

	@Data
	@RequiredArgsConstructor
	static class FindSubPathError {

		private final String msg;

	}

	public static Either<FindSubPathError, Path<?>> getSubPath(BeanPath<?> rootPath, String property) {
		Either<FindSubPathError, Path<?>> beanPaths = Either.<FindSubPathError, Path<?>>right(rootPath).flatMap(root -> {
			if (root == null) {
				return Either.left(new FindSubPathError("Empty rootPath"));
			} else if (root.getRoot().getType() != rootPath.getType()) {
				return Either.left(new FindSubPathError("Path is not root"));
			} else {
				return Either.right(root);
			}
		});

		return Stream.of(property.split("\\.")).foldLeft(beanPaths, (parentEither, prop) ->
				parentEither.<Path<?>>flatMap(parent -> {
					var parentClazz = parent.getClass();
					try {
						Field field = parent.getClass().getField(prop);
						return Either.right((Path<?>) field.get(parent));
					} catch (NoSuchFieldException ex) {
						return Either.left(new FindSubPathError("No such property " + prop + " in clazz " + parentClazz.getName()));
					} catch (IllegalAccessException ex) {
						return Either.left(new FindSubPathError("Private property " + prop + " in clazz " + parentClazz.getName()));
					} catch (ClassCastException ex) {
						return Either.left(new FindSubPathError("Wrong property " + prop + "in clazz " + parentClazz.getName() + ", It must be Path instance"));
					} catch (Exception ex) {
						return Either.left(new FindSubPathError("Unknown exception when get property from clazz " + parentClazz.getName() + ", detail is " + ex.getMessage()));
					}
				})
		);
	}

}
