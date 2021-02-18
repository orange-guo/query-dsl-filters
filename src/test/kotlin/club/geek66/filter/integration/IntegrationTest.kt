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

	@Test
	fun testDb() {
		Assertions.assertEquals(0, repo.findAll().size)
		repo.save(User { name = "Jack" })
		Assertions.assertEquals(1, repo.findAll().size)
	}

	@Test
	fun test() {
		User {
			name = "Smith"
			country = Country.US
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

		val generate = (::generateExpressions)(QUser.user)(setOf(PathMapper(UserDto::jobIndustryName.nel(), QUser.user.job.industry.name)))

		// CascadingQuery
		generate(
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

		// EnumEq not support
		/*generate(
			PathFilter(path = "country", operator = "=", "US").nel().toSet()
		).filterIsInstance<Either.Right<BooleanExpression>>()
			.map(Either.Right<BooleanExpression>::b)
			.toList()
			.combineByAnd()
			.let(repo::findAll)
			.first()!!
			.run {
				Assertions.assertEquals("Smith", name)
			}*/
		//
	}

	/*@Test

	@Test
	fun testCascadingQuery() {
		setOf(
			User {
				country = Country.US
				name = "User1"
				order = Orders().apply {
					title = "User1's title"
				}
			},
			User {
				country = Country.US
				name = "User2"
				order = Orders().apply {
					title = "User2's title"
				}
			}
		).let(repo::saveAll)

		generateFilterExpression(QUser.user, PathFilter("order.title", "EQ", "User1's title").nel().toSet())
			.let(repo::findAll)
			.first()!!.let { user1 ->
				Assertions.assertEquals(user1.name, "User1")
				Assertions.assertEquals(user1.order.title, "User1's title")
			}
	}

	@Test
	fun testNumberCompare() {
		User {
			country = Country.US
			name = "User1"
			order = Orders().apply {
				title = "User1's title"
			}
			money = 5L
		}.let(repo::save)

		generateFilterExpression(QUser.user, PathFilter(path = "money", operator = "GT", value = 4).nel().toSet())
			.let(repo::findAll).first()!!.let { mooneyGt4User ->
				Assertions.assertEquals(mooneyGt4User.name, "User1")
				Assertions.assertEquals(mooneyGt4User.order.title, "User1's title")
			}
	}

	@Test
	fun testNameLike() {
		setOf(
			User { name = "Jack" },
			User { name = "Tom" },
			User { name = "Tim" },
			User { name = "Jobs" }
		).let(repo::saveAll)
		generateFilterExpression(QUser.user, PathFilter(path = "name", operator = "LIKE", value = "%T%").nel().toSet())
			.let(repo::findAll)
			.toSet()
			.let { nameLikeTUsers ->
				Assertions.assertEquals(2, nameLikeTUsers.size)
			}
	}*/

}