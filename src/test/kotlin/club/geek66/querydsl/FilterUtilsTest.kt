package club.geek66.querydsl

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * @author: orange
 * @date: 2020/12/2
 * @time: 下午9:00
 * @copyright: Copyright 2020 by orange
 */
@SpringBootTest
@ContextConfiguration(classes = [FilterUtilsTest.Config::class])
@ExtendWith(SpringExtension::class)
open class FilterUtilsTest {

	@TestConfiguration
	@EnableAutoConfiguration
	@EntityScan(basePackages = ["club/geek66/querydsl"])
	@EnableJpaRepositories(basePackages = ["club/geek66/querydsl"])
	open class Config


	@Autowired
	private lateinit var userRepository: UserRepository

	@Test
	fun test() {
		print(userRepository)
	}

}