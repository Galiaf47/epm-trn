
package com.epam.trn.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.web.grid.Grid;

@Service
public class UsersService {
	@Autowired
	private UserDao userDao;

	public Grid<User> getUsersGrid(String filters, Integer page, Integer rows, String sortBy, String sortDirrection) {
		return userDao.getUsersPage(null, page, rows, sortBy, sortDirrection);
	}
	
	public void createUser(User user) throws NoSuchAlgorithmException {
		byte[] digest = MessageDigest.getInstance("MD5").digest(Utf8.encode(user.getPassword()));
		String hashedPassword = Utf8.decode(Base64.encode(digest));

		user.setPassword(hashedPassword);
		if(user.getLogin().isEmpty()) {
			user.setLogin(user.getEmail());
		}
		
		userDao.insert(user);
	}
	
	public void updateUser(User user) throws NoSuchAlgorithmException {
		User existingUser = userDao.findById(user.getId());
	
		if(existingUser != null) {
			byte[] digest = MessageDigest.getInstance("MD5").digest(Utf8.encode(user.getPassword()));
			String hashedPassword = Utf8.decode(Base64.encode(digest));
			
			existingUser.setEmail(user.getEmail());
			existingUser.setLogin(user.getLogin());
			existingUser.setPassword(hashedPassword);
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setAddress(user.getAddress());
			existingUser.setPhone(user.getPhone());
			existingUser.setIsActive(user.getIsActive());
			
			userDao.updateUser(user);
		}
	}
	
	public void deleteUsers(String idString) {
		String[] parsedIds = idString.split(",");
		int count = parsedIds.length;
		ArrayList<Long> ids = new ArrayList<Long>();
		
		for(int i = 0; i < count; i++) {
			ids.add(Long.parseLong(parsedIds[i]));
		}
		
		userDao.deleteUsers(ids);
	}
}
