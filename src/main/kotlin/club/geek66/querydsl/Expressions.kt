package club.geek66.querydsl

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.extensions.list.foldable.foldLeft
import arrow.syntax.function.invoke
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.BooleanOperation
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions

fun generateExpressions(entityPath: EntityPathBase<*>, pathFilters: Set<PathFilter>, pathAliases: Map<String, String>): Set<Either<PathError, BooleanExpression>> =
	replacePathAliases(pathFilters, pathAliases)
		.let((::convertFilters)(entityPath))
		.map {
			it.map(QueryDslPathFilter::toOperation)
		}.toSet()