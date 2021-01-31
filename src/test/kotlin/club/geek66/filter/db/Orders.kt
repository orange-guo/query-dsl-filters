package club.geek66.filter.db

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午12:03
 * @copyright: Copyright 2020 by orange
 */
@Entity
class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = -1

	lateinit var title: String

	@OneToOne(cascade = [CascadeType.PERSIST], mappedBy = "order")
	lateinit var user: User

}