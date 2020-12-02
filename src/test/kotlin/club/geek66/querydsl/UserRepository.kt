package club.geek66.querydsl

import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author: orange
 * @date: 2020/12/2
 * @time: 下午9:53
 * @copyright: Copyright 2020 by orange
 */
interface UserRepository : JpaRepository<User, Long>