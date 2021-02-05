package club.geek66.filter.querydsl

import club.geek66.filter.PathFilter
import club.geek66.filter.db.QUser
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


/**
 *
 * @author: orange
 * @date: 2021/1/25
 * @time: 下午5:26
 * @copyright: Copyright 2021 by orange
 */
internal class ConvertKtTest {

	@Test
	fun withConvertSingle() {
		assertEquals(QueryDslFilter(QUser.user.name, Ops.EQ, ConstantImpl.create("Jack")), convertSingle(QUser.user, PathFilter("name", "EQ", "Jack")).orNull())
	}

	@Test
	fun withReplacePathAliases() {
		assertEquals(setOf(PathFilter(path = "job.industry.name", operator = "EQ", value = "Teacher")), withReplacePathAliases(mapOf("jobIndustryName" to "job.industry.name"), setOf(PathFilter(path = "jobIndustryName", operator = "EQ", value = "Teacher"))))
		withReplacePathAliases(mapOf("addressId" to "address.id"), setOf(PathFilter("addressId", "=", 3))).let {
			Assertions.assertEquals(it.size, 1)
			Assertions.assertEquals(it.first(), PathFilter("address.id", "=", 3))
		}
	}

	/*@Test
	fun withFunc() {
		val a: (String, String) -> Int = ::hasGenericFun
		// val ab = ::hasGenericFun compile failed
		val value: Int = (a)("123")("123")
		val s = (::noGenericFun)(123)(456)
		ctxFun(::hasGenericFun)
	}*/

}

/*fun <T1, T2, R1> hasGenericFun(t1: T1, t2: T2): R1 = TODO()

fun noGenericFun(a: Int, b: Int): String = "123"

fun ctxFun(a: (String, String) -> Int): Int = a("123", "123")*/
