package club.geek66.querydsl

import arrow.core.Either
import arrow.core.nel
import club.geek66.querydsl.db.*
import com.querydsl.core.types.dsl.BooleanExpression
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@EntityScan
@Transactional
@TestConfiguration
@EnableAutoConfiguration
@ExtendWith(SpringExtension::class)
internal class IntegrationTest {

	@Autowired
	lateinit var repo: UserRepository

	@Test
	fun test() {
		val user = User {
			name = "Smith"
			country = Country.US
			job = Job {
				name = "JAVA-SERVER"
				industry = Industry {
					name = "JAVA"
				}
				// users = listOf(user)
			}
		}.apply {
			job.users = listOf(this)
			job.industry.jobs = listOf(job)
		}.let(repo::save)

		data class UserDto(
			val jobIndustryName: String
		)

		val expression: BooleanExpression = generateExpressions(
			QUser.user,
			setOf(QueryDslPathBinding(UserDto::jobIndustryName.nel(), QUser.user.job.industry.name)),
			PathExpFilter(path = "jobIndustryName", "=", "JAVA").nel().toSet()
		).filterIsInstance<Either.Right<BooleanExpression>>()
			.map(Either.Right<BooleanExpression>::b).toList().mergeByAnd()
		val savedUser: User = repo.findAll(expression).first()!!
		Assertions.assertEquals("Smith", savedUser.name)
	}

}