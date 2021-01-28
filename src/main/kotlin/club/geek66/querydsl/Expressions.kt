package club.geek66.querydsl

import arrow.core.Either
import arrow.core.extensions.list.foldable.foldLeft
import arrow.syntax.function.invoke
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions

fun List<BooleanExpression>.mergeByAnd(): BooleanExpression =
	foldLeft(Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1)), BooleanExpression::and)

fun List<BooleanExpression>.mergeByOr(): BooleanExpression =
	foldLeft(Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1)), BooleanExpression::or)

fun generateExpressions(entityPath: EntityPathBase<*>, pathAliases: Map<String, String>, pathExpressionFilters: Set<PathExpFilter>): Set<Either<PathError, BooleanExpression>> =
	(::convertFilters)(entityPath).let { convertFilter ->
		replacePathAliases(pathAliases, pathExpressionFilters)
			.let(convertFilter)
			.map {
				it.map(QueryDslPathExpFilter::toOperation)
			}.toSet()
	}

fun generateExpressions(entityPath: EntityPathBase<*>, pathBinding: Set<QueryDslPathBinding>, pathExpressionFilters: Set<PathExpFilter>): Set<Either<PathError, BooleanExpression>> =
	generateExpressions(entityPath, pathBinding.map { Pair(it.showSource(), it.showTarget()) }.toTypedArray().let(::mapOf), pathExpressionFilters)