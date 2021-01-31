package club.geek66.filter.db

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

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