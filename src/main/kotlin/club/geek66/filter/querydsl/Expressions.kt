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

data class CombineBoolExpMonoid(
	val combine: (BooleanExpression, BooleanExpression) -> BooleanExpression,
) : Monoid<BooleanExpression> {

	override fun empty(): BooleanExpression =
		Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1))

	override fun BooleanExpression.combine(b: BooleanExpression): BooleanExpression = combine(this, b)

}

fun List<BooleanExpression>.combineByAnd(): BooleanExpression =
	fold(CombineBoolExpMonoid(BooleanExpression::and))

fun List<BooleanExpression>.combineByOr(): BooleanExpression =
	fold(CombineBoolExpMonoid(BooleanExpression::or))

fun generateExpressions(entityPath: EntityPathBase<*>, binding: Set<PathMapper>, filters: Set<PathFilter>): Set<Either<PathError, BooleanExpression>> =
	(::convertFilters)(entityPath).let { func ->
		withReplacePathAliases(binding.map(PathMapper::toAliasPair).toTypedArray().let(::mapOf), filters)
			.let(func)
			.map {
				it.map(QueryDslFilter::toOperation)
			}.toSet()
	}