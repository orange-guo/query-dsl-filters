package club.geek66.querydsl

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 *
 * @author: orange
 * @date: 2020/12/2
 * @time: 下午9:54
 * @copyright: Copyright 2020 by orange
 */
@Entity
class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	val id: Long = -1

	val name: String = ""

}