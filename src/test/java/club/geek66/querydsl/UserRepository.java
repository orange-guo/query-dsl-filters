package club.geek66.querydsl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:58
 * @copyright: Copyright 2020 by orange
 */
interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

}
