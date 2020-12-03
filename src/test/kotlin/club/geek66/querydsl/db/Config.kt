package club.geek66.querydsl.db

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 *
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午5:59
 * @copyright: Copyright 2020 by orange
 */
@EntityScan
@TestConfiguration
@EnableJpaRepositories
@SpringBootConfiguration
@EnableAutoConfiguration
internal class Config