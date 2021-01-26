package club.geek66.querydsl.db

import javax.persistence.*

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