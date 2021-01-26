package club.geek66.querydsl

import arrow.core.Either
import arrow.core.nel
import club.geek66.querydsl.db.QUser
import com.querydsl.core.types.dsl.BooleanExpression
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

/**
 *
 * @author: 郭相成
 * @date: 2021/1/26
 * @time: 20:51
 * @copyright: Copyright 2021 by 郭相成
 */
internal class ExpressionsKtTest {

	@Test
	fun generateExpression() {
		data class UserDto(
			val jobIndustryName: String
		)

		val expressions: Set<BooleanExpression> = generateExpressions(
			QUser.user,
			setOf(PathMapping(source = UserDto::jobIndustryName.nel(), target = QUser.user.job.industry.name)),
			setOf(PathFilter(path = "jobIndustryName", operator = "=", "Java"))
		).filterIsInstance<Either.Right<BooleanExpression>>().map(Either.Right<BooleanExpression>::b).toSet()

		assertEquals(QUser.user.job.industry.name.eq("Java").toString(), expressions.first().toString())
	}
}