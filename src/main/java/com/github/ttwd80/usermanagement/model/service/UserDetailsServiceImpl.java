package com.github.ttwd80.usermanagement.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.ttwd80.usermanagement.model.repository.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final com.github.ttwd80.usermanagement.model.entity.User user = userRepository.findOne(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		} else {
			final String password = user.getPassword();
			final List<? extends GrantedAuthority> authorities = toList(user.getRoles());
			final String id = user.getId();
			final org.springframework.security.core.userdetails.User result = new org.springframework.security.core.userdetails.User(
					id, password, authorities);
			return result;
		}
	}

	private List<? extends GrantedAuthority> toList(final List<String> roles) {
		if (roles == null) {
			return Collections.emptyList();
		}
		final List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		for (final String value : roles) {
			list.add(new SimpleGrantedAuthority(value));
		}
		return list;
	}
}
