package com.epam.trn.dao;

import java.util.ArrayList;
import java.util.List;

import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.web.grid.impl.SimpleGrid;

public interface UserDao {
	public void insert(User user);

	public User findByLogin(String username);

	public User findById(long id);

	public List<User> getUsers();
	
	public SimpleGrid<User> getUsersPage(String filters, Integer page, Integer rows, String sortBy, String sortDirrection);
	
	public Boolean deleteUser(long id);
	
	public Boolean deleteUsers(ArrayList<Long> ids);
	
	public Boolean updateUser(User user);

	public void insertUserRoles(User user);
	
	public List<UserRole> getUserRoles(Integer userId);

	public int getTotal();
}
