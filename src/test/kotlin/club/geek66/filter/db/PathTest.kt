package club.geek66.filter.db/*
package club.geek66.querydsl

import club.geek66.querydsl.db.QUser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.persistence.Entity
import javax.persistence.Id

*/
/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午3:46
 * @copyright: Copyright 2020 by orange
 *//*


@Entity
class People {

	@Id
	var id: Long = -1

	lateinit var name: String

	var age: Int = -1

	lateinit var city: City

}

@Entity
class City {

	@Id
	var id: Long = -1

	lateinit var name: String

	lateinit var province: Province

}

@Entity
class Province {

	@Id
	var id: Long = -1

	lateinit var name: String

}

class PathTest {

	@Test
	fun testGetPath() {
		QPeople.people.city.province.name

		getSubPath(QPeople.people, "city.province.name").let { provinceName ->
			Assertions.assertTrue(provinceName.isRight())
			Assertions.assertEquals(QPeople.people.city.province.name, provinceName.orNull())
		}

		getSubPath(QPeople.people, "name").let { peopleName ->
			Assertions.assertTrue(peopleName.isRight())
			Assertions.assertEquals(QPeople.people.name, peopleName.orNull())
		}

		getSubPath(QUser.user, "xxx").let { unknown ->
			Assertions.assertTrue(unknown.isLeft())
		}

		// val function: (propExpression: String) -> Either<club.geek66.querydsl.PathHandler.Path.FindSubPathError, Path<*>> = (club.geek66.querydsl.Path::getSubPath)(QUser.user)
	}

}*/
