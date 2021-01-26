package club.geek66.querydsl.db

import javax.persistence.*

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