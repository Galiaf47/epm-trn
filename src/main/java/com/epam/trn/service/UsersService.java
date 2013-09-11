package com.epam.trn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;

@Service
public class UsersService {
	@Autowired
	private UserDao userDao;

	public UsersList getUsers() {
		return new UsersList(userDao.getUsers());
	}

	public class UsersList extends ArrayList<User> {

		private static final long serialVersionUID = -257751975513522496L;

		public UsersList(List<User> users) {
			super(users);
		}
	}
}
