package club.geek66.filter.integration

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

/**
 *
 * @author: orange
 * @date: 2021/2/18
 * @time: 下午4:10
 * @copyright: Copyright 2021 by orange
 */
internal interface UserRepository : JpaRepository<User, Long>, QuerydslPredicateExecutor<User>