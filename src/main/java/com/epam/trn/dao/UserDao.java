package com.epam.trn.dao;

import java.util.List;

import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;

public interface UserDao {
	public void insert(User user);

	public User findByLogin(String username);

	public User findById(String id);

	public List<User> getUsers();

	public List<UserRole> getUserRoles(Integer userId);

	public int getTotal();
}
