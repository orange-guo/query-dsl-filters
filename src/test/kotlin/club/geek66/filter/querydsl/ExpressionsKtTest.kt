package club.geek66.filter.querydsl

import arrow.core.Either
import arrow.core.nel
import club.geek66.filter.PathFilter
import club.geek66.filter.db.QUser
import com.querydsl.core.types.dsl.BooleanExpression
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ExpressionsKtTest {

	@Test
	fun generateExpression() {
		data class UserDto(
			val jobIndustryName: String,
		)

		val expressions: Set<BooleanExpression> = generateExpressions(
			QUser.user,
			setOf(QueryDslBinding(source = UserDto::jobIndustryName.nel(), target = QUser.user.job.industry.name)),
			setOf(PathFilter(path = "jobIndustryName", operator = "=", "Java"))
		).filterIsInstance<Either.Right<BooleanExpression>>().map(Either.Right<BooleanExpression>::b).toSet()

		assertEquals(QUser.user.job.industry.name.eq("Java").toString(), expressions.first().toString())
	}

}