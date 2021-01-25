package club.geek66.querydsl.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Industry {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = -1

	lateinit var name: String

}