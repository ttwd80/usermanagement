package db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.github.ttwd80.usermanagement.model.entity.User;
import com.github.ttwd80.usermanagement.model.repository.UserRepository;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.*;

@ContextConfiguration(locations = { "classpath:spring-properties.xml", "classpath:" })
public class ElasticSearchDbIT extends AbstractJUnit4SpringContextTests {

	@Autowired
	UserRepository userRepository;

	@Test
	public void testWipeAllThenCount() {
		userRepository.deleteAll();
		assertThat(userRepository.count(), equalTo(0L));
	}

	@Test
	public void testWrite2RowsThenCount() {
		userRepository.deleteAll();
		User user1 = new User();
		user1.setId("a@apache.org");
		userRepository.save(user1);
		User user2 = new User();
		user2.setId("b@apache.org");
		userRepository.save(user2);
		assertThat(userRepository.count(), equalTo(2L));
	}
}
