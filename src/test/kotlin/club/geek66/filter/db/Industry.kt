package club.geek66.filter.db

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

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