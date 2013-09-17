package com.epam.trn.dao;

import java.util.List;

import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.model.UsersPage;

public interface UserDao {
	public void insert(User user);

	public User findByLogin(String username);

	public User findById(String id);

	public List<User> getUsers();
	
	public UsersPage getUsersPage(String filters, Integer page, Integer rows, String sortBy, String sortDirrection);
	
	public Boolean deleteUser(long id);

	public List<UserRole> getUserRoles(Integer userId);

	public int getTotal();
}
