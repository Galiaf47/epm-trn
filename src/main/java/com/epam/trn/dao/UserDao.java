package com.epam.trn.dao;

import java.util.List;

import com.epam.trn.model.User;

public interface UserDao {
	public void insert(User user);

	public User findByLogin(String username);

	public User findById(String id);

	public List<User> getUsers();

	public int getTotal();
}
