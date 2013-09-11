package com.epam.trn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.web.grid.Grid;
import com.epam.trn.web.grid.impl.SimpleGrid;

@Service
public class UsersService {
	@Autowired
	private UserDao userDao;

	public Grid<User> getUsersGrid() {
		return new SimpleGrid<User>(this.userDao.getUsers());
	}
}
