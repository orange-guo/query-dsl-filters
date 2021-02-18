package club.geek66.filter.integration

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne

/**
 *
 * @author: orange
 * @date: 2021/2/18
 * @time: 下午3:45
 * @copyright: Copyright 2021 by orange
 */
@Entity
class User {

	constructor()

	constructor(consume: User.() -> Unit) {
		this.consume()
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = -1

	lateinit var name: String

	@Enumerated(EnumType.STRING)
	lateinit var country: Country

	var money: Long = -1

	@OneToOne(cascade = [CascadeType.ALL])
	lateinit var order: Orders

	@ManyToOne(cascade = [CascadeType.ALL])
	lateinit var job: Job

}

enum class Country {

	CN, JP, KR, US

}

@Entity
class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = -1

	lateinit var title: String

	@OneToOne(cascade = [CascadeType.PERSIST], mappedBy = "order")
	lateinit var user: User

}

@Entity
class Job {

	constructor()

	constructor(consume: Job.() -> Unit) {
		this.consume()
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = -1

	lateinit var name: String

	@ManyToOne(cascade = [CascadeType.ALL])
	lateinit var industry: Industry

	@OneToMany(cascade = [CascadeType.ALL])
	lateinit var users: List<User>

}

@Entity
class Industry {

	constructor()

	constructor(consume: Industry.() -> Unit) {
		this.consume()
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = -1

	lateinit var name: String

	@OneToMany(cascade = [CascadeType.ALL])
	lateinit var jobs: List<Job>

}