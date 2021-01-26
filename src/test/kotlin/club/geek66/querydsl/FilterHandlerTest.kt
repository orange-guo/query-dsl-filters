/*
package club.geek66.querydsl

import arrow.core.nel
import club.geek66.querydsl.db.Country
import club.geek66.querydsl.db.Orders
import club.geek66.querydsl.db.QUser
import club.geek66.querydsl.db.User
import club.geek66.querydsl.db.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@EntityScan
@Transactional
@TestConfiguration
@EnableAutoConfiguration
@ExtendWith(SpringExtension::class)
class FilterHandlerTest {

	@Autowired
	private lateinit var repo: UserRepository

	@Test
	fun makeSureDbIsEmpty() {
		Assertions.assertEquals(0, repo.findAll().size)

		repo.save(User { name = "Jack" })

		Assertions.assertEquals(1, repo.findAll().size)
	}

	@Test
	fun testStringLike() {
		User {
			name = "Jack"
		}.let(repo::save)
		val savedUser = generateFilterExpression(QUser.user, PathFilter("name", "LIKE", "Jack").nel().toSet())
			.let(repo::findAll)
			.first()!!
		Assertions.assertEquals("Jack", savedUser.name)
	}

	@Test
	fun testEnumEq() {
		User {
			name = "Smith"
			country = Country.US
		}.let(repo::save)

		generateFilterExpression(QUser.user, PathFilter("country", "EQ", "US").nel().toSet())
			.let(repo::findAll)
			.first()!!.let { smith ->
				Assertions.assertEquals(Country.US, smith.country)
			}
	}

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
	}

}
*/
