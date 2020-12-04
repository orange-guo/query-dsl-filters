package club.geek66.querydsl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:56
 * @copyright: Copyright 2020 by orange
 */
@Transactional
@SpringBootTest
@ContextConfiguration(classes = FilterUtilsTest.Config.class)
@ExtendWith(SpringExtension.class)
public class FilterUtilsTest {

	@EntityScan
	@TestConfiguration
	@EnableJpaRepositories
	@SpringBootConfiguration
	@EnableAutoConfiguration
	static class Config {

	}

	@Autowired
	private UserRepository repo;

	@Test
	public void testRollback() {
		Assertions.assertEquals(0, repo.findAll().size());
		var user = new User();
		user.setName("Jack");
		repo.save(user);
		Assertions.assertEquals(1, repo.findAll().size());
	}

	@Test
	public void testStringLike() {
		var user = new User();
		user.setName("Jack");
		repo.save(user);
		Set<PropertyFilter> filters = Set.of(
				new PropertyFilter("name", "LIKE", "Jack")
		);
		BooleanExpression expression = FilterUtils.generateExpression(QUser.user, filters);
		User savedUser = Lists.newArrayList(repo.findAll(expression)).get(0);
		Assertions.assertEquals("Jack", savedUser.getName());
	}

	@Test
	public void testEnumEq() {
		User smith = User.builder().name("Smith").country(Country.US).build();
		repo.save(smith);

		Set<PropertyFilter> filters = Set.of(
				new PropertyFilter(
						"country", "EQ", "US"
				)
		);
		BooleanExpression expression = FilterUtils.generateExpression(QUser.user, filters);
		User foundSmith = Lists.newArrayList(repo.findAll(expression)).get(0);
		Assertions.assertEquals(Country.US, foundSmith.getCountry());
	}

	@Test
	public void testCascadingQuery() {
		User user1 = User.builder().country(Country.US).name("User1").order(Orders.builder().title("User1's title").build()).build();
		User user2 = User.builder().country(Country.US).name("User2").order(Orders.builder().title("User2's title").build()).build();
		repo.save(user1);
		repo.save(user2);
		Set<PropertyFilter> filters = Set.of(
				new PropertyFilter(
						"order.title", "EQ", "User1's title"
				)
		);
		BooleanExpression expression = FilterUtils.generateExpression(QUser.user, filters);
		User user = Lists.newArrayList(repo.findAll(expression)).get(0);
		Assertions.assertEquals(user.getName(), "User1");
		Assertions.assertEquals(user.getOrder().getTitle(), "User1's title");
	}

	@Test
	public void testNumberCompare() {
		User user1 = User.builder().country(Country.US).name("User1").money(5L).order(Orders.builder().title("User1's title").build()).build();
		repo.save(user1);
		BooleanExpression expression = FilterUtils.generateExpression(QUser.user, Set.of(
				PropertyFilter.builder().property("money").operator("GT").value(4).build()
		));
		User user = Lists.newArrayList(repo.findAll(expression)).get(0);
		Assertions.assertEquals(user.getName(), "User1");
		Assertions.assertEquals(user.getOrder().getTitle(), "User1's title");
	}

}
