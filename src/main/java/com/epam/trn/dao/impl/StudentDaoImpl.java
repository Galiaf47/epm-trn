package com.epam.trn.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;

public class StudentDaoImpl extends JdbcDaoSupport implements UserDao {

	@Override
	public void insert(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findByLogin(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRole> getUserRoles(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

}
