package club.geek66.filter.integration

import arrow.core.Either
import arrow.core.nel
import arrow.syntax.function.invoke
import club.geek66.filter.PathFilter
import club.geek66.filter.integration.*
import club.geek66.filter.querydsl.PathMapper
import club.geek66.filter.querydsl.combineByAnd
import club.geek66.filter.querydsl.generateExpressions
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

	internal data class UserDto(
		val jobIndustryName: String,
	)

	val generate = (::generateExpressions)(QUser.user)

	@Test
	fun testDb() {
		Assertions.assertEquals(0, repo.findAll().size)
		repo.save(User { name = "Jack" })
		Assertions.assertEquals(1, repo.findAll().size)
	}

	@Test
	fun byCascade() {
		User {
			name = "Smith"
			country = Country.US
			money = 5
			job = Job {
				name = "JAVA-SERVER"
				industry = Industry {
					name = "JAVA"
				}
			}
		}.apply {
			job.users = listOf(this)
			job.industry.jobs = listOf(job)
		}.let(repo::save)

		generate(
			(setOf(PathMapper(UserDto::jobIndustryName.nel(), QUser.user.job.industry.name))),
			PathFilter(path = "jobIndustryName", operator = "=", value = "JAVA").nel().toSet()
		).filterIsInstance<Either.Right<BooleanExpression>>()
			.map(Either.Right<BooleanExpression>::b)
			.toList()
			.combineByAnd()
			.let(repo::findAll)
			.first()!!
			.run {
				Assertions.assertEquals("Smith", name)
			}
	}

	@Test
	fun byEnum() {
		User {
			name = "Keven"
			country = Country.JP
			money = 5
			job = Job {
				name = "PHP-SERVER"
				industry = Industry {
					name = "PHP"
				}
			}
		}.apply {
			job.users = listOf(this)
			job.industry.jobs = listOf(job)
		}.let(repo::save)

		generate(
			emptySet(),
			PathFilter(path = "country", operator = "=", "JP").nel().toSet()
		).filterIsInstance<Either.Right<BooleanExpression>>()
			.map(Either.Right<BooleanExpression>::b)
			.toList()
			.combineByAnd()
			.let(repo::findAll)
			.first()!!
			.run {
				Assertions.assertEquals("Keven", name)
			}
	}

	@Test
	fun byNumber() {
		User {
			name = "Thompson"
			country = Country.US
			job = Job {
				name = "JAVASCRIPT-BROWSER"
				industry = Industry {
					name = "Javascript"
				}
			}
			money = 5L
		}.apply {
			job.users = listOf(this)
			job.industry.jobs = listOf(job)
		}.let(repo::save)

		generate(emptySet(), PathFilter(path = "money", operator = "GT", value = 4).nel().toSet())
			.filterIsInstance<Either.Right<BooleanExpression>>()
			.map(Either.Right<BooleanExpression>::b)
			.toList()
			.combineByAnd()
			.let(repo::findAll)
			.first()!!
			.run {
				Assertions.assertEquals("Thompson", name)
				Assertions.assertEquals("JAVASCRIPT-BROWSER", job.name)
			}
	}

}