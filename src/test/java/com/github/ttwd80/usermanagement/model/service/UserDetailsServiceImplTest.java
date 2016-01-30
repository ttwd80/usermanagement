package com.github.ttwd80.usermanagement.model.service;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.ttwd80.usermanagement.model.entity.User;
import com.github.ttwd80.usermanagement.model.repository.UserRepository;
import static org.mockito.Mockito.*;

import java.util.Arrays;

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
		UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getAuthorities().size(), equalTo(0));
	}

	@Test
	public void testLoadUserByUsernameEmptyRoles() {
		when(userRepository.findOne("good")).thenReturn(createUser(new String[] {}));
		UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getAuthorities().size(), equalTo(0));
	}

	@Test
	public void testLoadUserByUsernameSingleRoles() {
		when(userRepository.findOne("good")).thenReturn(createUser(new String[] { "ROLE_MANAGER" }));
		UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getAuthorities().size(), equalTo(1));
	}

	@Test
	public void testLoadUserByUsernameMultipleRoles() {
		when(userRepository.findOne("good")).thenReturn(createUser(new String[] { "ROLE_MANAGER", "ROLE_SUPERVISOR" }));
		UserDetails user = sut.loadUserByUsername("good");
		assertThat(user.getAuthorities().size(), equalTo(2));
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameNoUser() {
		when(userRepository.findOne("good")).thenReturn(null);
		sut.loadUserByUsername("good");
	}

	private User createUser(String[] roles) {
		User user = new User();
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
