package club.geek66.querydsl.db

import javax.persistence.*

@Entity
class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = -1

	lateinit var name: String

	@ManyToOne
	lateinit var industry: Industry

}