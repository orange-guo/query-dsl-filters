package club.geek66.querydsl

import arrow.core.Either
import arrow.syntax.function.invoke
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase

fun generateExpressions(entityPath: EntityPathBase<*>, pathAliases: Map<String, String>, pathFilters: Set<PathFilter>): Set<Either<PathError, BooleanExpression>> =
	(::convertFilters)(entityPath).let { convertFilter ->
		replacePathAliases(pathAliases, pathFilters)
			.let(convertFilter)
			.map {
				it.map(QueryDslPathFilter::toOperation)
			}.toSet()
	}

fun generateExpression(entityPath: EntityPathBase<*>, pathMapping: Set<PathMapping>, pathFilters: Set<PathFilter>): Set<Either<PathError, BooleanExpression>> {

}