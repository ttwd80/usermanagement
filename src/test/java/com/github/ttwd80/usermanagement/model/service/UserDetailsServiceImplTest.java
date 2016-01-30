package com.github.ttwd80.usermanagement.model.service;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.ttwd80.usermanagement.model.entity.User;
import com.github.ttwd80.usermanagement.model.repository.UserRepository;

public class UserDetailsServiceImplTest {

	UserDetailsServiceImpl sut;

	@Mock
	UserRepository userRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sut = new UserDetailsServiceImpl(userRepository);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadUserByUsernameNullRoles() {
		when(userRepository.findOne("good")).thenReturn(createUser(null));
		final UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getAuthorities().size(), equalTo(0));
	}

	@Test
	public void testLoadUserByUsernameEmptyRoles() {
		when(userRepository.findOne("good")).thenReturn(createUser(new String[] {}));
		final UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getAuthorities().size(), equalTo(0));
	}

	@Test
	public void testLoadUserByUsernameSingleRoles() {
		when(userRepository.findOne("good")).thenReturn(createUser(new String[] { "ROLE_MANAGER" }));
		final UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getAuthorities().size(), equalTo(1));
	}

	@Test
	public void testLoadUserByUsernameMultipleRoles() {
		when(userRepository.findOne("good")).thenReturn(createUser(new String[] { "ROLE_MANAGER", "ROLE_SUPERVISOR" }));
		final UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getAuthorities().size(), equalTo(2));
	}

	@Test
	public void testLoadUserByUsernameCheckId() {
		when(userRepository.findOne("good")).thenReturn(createUser(new String[] { "ROLE_MANAGER", "ROLE_SUPERVISOR" }));
		final UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getUsername(), equalTo("x"));
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameNoUser() {
		when(userRepository.findOne("good")).thenReturn(null);
		sut.loadUserByUsername("good");
	}

	private User createUser(final String[] roles) {
		final User user = new User();
		user.setId("x");
		user.setPassword("***");
		if (roles == null) {
			user.setRoles(null);
		} else {
			user.setRoles(Arrays.asList(roles));
		}
		// TODO Auto-generated method stub
		return user;
	}

}
