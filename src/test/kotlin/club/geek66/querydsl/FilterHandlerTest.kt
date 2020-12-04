package club.geek66.querydsl

import arrow.core.nel
import club.geek66.querydsl.FilterHandler.generateExpression
import club.geek66.querydsl.db.Country
import club.geek66.querydsl.db.Orders
import club.geek66.querydsl.db.QUser
import club.geek66.querydsl.db.User
import club.geek66.querydsl.db.UserRepository
import com.google.common.collect.Lists
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:56
 * @copyright: Copyright 2020 by orange
 */
@EntityScan
@Transactional
@TestConfiguration
@EnableAutoConfiguration
@ExtendWith(SpringExtension::class)
class FilterHandlerTest {

	@Autowired
	private lateinit var repo: UserRepository

	@Test
	fun makesureDbIsEmpty() {
		Assertions.assertEquals(0, repo.findAll().size)
		val user = User()
		user.name = "Jack"
		repo.save(user)
		Assertions.assertEquals(1, repo.findAll().size)
	}

	@Test
	fun testStringLike() {
		val user = User()
		user.name = "Jack"
		repo.save(user)
		val expression = generateExpression(QUser.user, PropertyFilter("name", "LIKE", "Jack").nel().toSet())
		val savedUser = Lists.newArrayList(repo.findAll(expression)).first()!!
		Assertions.assertEquals("Jack", savedUser.name)
	}

	@Test
	fun testEnumEq() {
		val smith: User = User().apply {
			name = "Smith"
			country = Country.US
		}
		repo.save(smith)
		val expression = generateExpression(QUser.user, PropertyFilter("country", "EQ", "US").nel().toSet())
		val foundSmith = Lists.newArrayList(repo.findAll(expression)).first()!!
		Assertions.assertEquals(Country.US, foundSmith.country)
	}

	@Test
	fun testCascadingQuery() {
		val user1: User = User().apply {
			country = Country.US
			name = "User1"
			order = Orders().apply {
				title = "User1's title"
			}
		}

		val user2: User = User().apply {
			country = Country.US
			name = "User2"
			order = Orders().apply {
				title = "User2's title"
			}
		}
		repo.save(user1)
		repo.save(user2)
		val expression = generateExpression(QUser.user, PropertyFilter("order.title", "EQ", "User1's title").nel().toSet())
		val user = Lists.newArrayList(repo.findAll(expression)).first()!!
		Assertions.assertEquals(user.name, "User1")
		Assertions.assertEquals(user.order.title, "User1's title")
	}

	@Test
	fun testNumberCompare() {
		val user1: User = User().apply {
			country = Country.US
			name = "User1"
			order = Orders().apply {
				title = "User1's title"
			}
			money = 5L
		}
		repo.save(user1)
		val expression = generateExpression(QUser.user, PropertyFilter(property = "money", operator = "GT", value = 4).nel().toSet())
		val user = repo.findAll(expression).first()!!
		Assertions.assertEquals(user.name, "User1")
		Assertions.assertEquals(user.order.title, "User1's title")
	}

}