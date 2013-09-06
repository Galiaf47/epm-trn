package com.epam.trn.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;

public class AuthService implements UserDetailsService, Serializable {

	private static final long serialVersionUID = -4807898101977350488L;
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		User user = userDao.findByLogin(login);
		UserDetails userDetails = null;
		if (user != null) {
			userDetails = new org.springframework.security.core.userdetails.User(
					user.getLogin(), user.getPassword(),
					getGrantedAuthority(user));
		}
		return userDetails;
	}

	private Collection<GrantedAuthority> getGrantedAuthority(User user) {
		List<UserRole> userRoles = userDao.getUserRoles(user.getId());
		Set<GrantedAuthority> result = new HashSet<GrantedAuthority>();
		for (UserRole role : userRoles) {
			result.add(new SimpleGrantedAuthority(role.getName()));
			user.addRole(role);
		}
		return result;
	}
}
