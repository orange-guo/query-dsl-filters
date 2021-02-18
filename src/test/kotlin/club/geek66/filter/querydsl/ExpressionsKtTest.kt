package club.geek66.filter.querydsl

import arrow.core.Either
import arrow.core.nel
import club.geek66.filter.PathFilter
import club.geek66.filter.integration.QUser.user
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ExpressionsKtTest {

	@Test
	fun withCombine() {
		listOf(
			user.id.gt(5),
			user.name.like("test")
		).combineByAnd().let { combineByAnd ->
			assertEquals(
				Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1))
					.and(user.id.gt(5))
					.and(user.name.like("test")),
				combineByAnd
			)
		}

		listOf(
			user.id.gt(5),
			user.name.like("test")
		).combineByOr().let { combineByOr ->
			assertEquals(
				Expressions.booleanOperation(Ops.EQ, ConstantImpl.create(1), ConstantImpl.create(1))
					.or(user.id.gt(5))
					.or(user.name.like("test")),
				combineByOr
			)
		}
	}

	@Test
	fun generateExpression() {
		data class UserDto(
			val jobIndustryName: String,
		)

		val expressions: Set<BooleanExpression> = generateExpressions(
			user,
			setOf(PathMapper(src = UserDto::jobIndustryName.nel(), dst = user.job.industry.name)),
			setOf(PathFilter(path = "jobIndustryName", operator = "=", "Java"))
		).filterIsInstance<Either.Right<BooleanExpression>>().map(Either.Right<BooleanExpression>::b).toSet()

		assertEquals(user.job.industry.name.eq("Java").toString(), expressions.first().toString())
	}

}