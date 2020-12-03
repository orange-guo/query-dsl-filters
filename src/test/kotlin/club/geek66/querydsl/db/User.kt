package club.geek66.querydsl.db

import club.geek66.querydsl.db.Country
import club.geek66.querydsl.db.Orders
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:36
 * @copyright: Copyright 2020 by orange
 */
@Entity
class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = -1

	lateinit var name: String

	@Enumerated(EnumType.STRING)
	lateinit var country: Country

	var money: Long = -1

	@OneToOne(cascade = [CascadeType.ALL])
	lateinit var order: Orders

}