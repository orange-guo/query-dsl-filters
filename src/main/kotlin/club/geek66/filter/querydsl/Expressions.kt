package club.geek66.filter.querydsl

import arrow.core.Either
import arrow.core.extensions.list.foldable.fold
import arrow.syntax.function.invoke
import arrow.typeclasses.Monoid
import club.geek66.filter.PathFilter
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions

data class CombineBooleanExpressionMonoid(
	val combine: (BooleanExpression, BooleanExpression) -> BooleanExpression,
) : Monoid<BooleanExpression> {

	override fun empty(): BooleanExpression =
		Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1))

	override fun BooleanExpression.combine(b: BooleanExpression): BooleanExpression = combine(this, b)

}

fun List<BooleanExpression>.combineByAnd(): BooleanExpression =
	fold(CombineBooleanExpressionMonoid(BooleanExpression::and))

fun List<BooleanExpression>.combineByOr(): BooleanExpression =
	fold(CombineBooleanExpressionMonoid(BooleanExpression::or))

fun generateExpressions(entityPath: EntityPathBase<*>, pathAliases: Map<String, String>, pathExpressionFilters: Set<PathFilter>): Set<Either<PathError, BooleanExpression>> =
	(::convertFilters)(entityPath).let { convertFilter ->
		withReplacePathAliases(pathAliases, pathExpressionFilters)
			.let(convertFilter)
			.map {
				it.map(QueryDslFilter::toOperation)
			}.toSet()
	}

fun generateExpressions(entityPath: EntityPathBase<*>, binding: Set<QueryDslBinding>, pathExpressionFilters: Set<PathFilter>): Set<Either<PathError, BooleanExpression>> =
	generateExpressions(entityPath, binding.map { Pair(it.showSource(), it.showTarget()) }.toTypedArray().let(::mapOf), pathExpressionFilters)