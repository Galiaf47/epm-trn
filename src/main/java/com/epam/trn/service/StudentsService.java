/**
 * 
 */
package com.epam.trn.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.web.grid.impl.SimpleGrid;

/**
 * @author Siarhei Klimuts
 *
 */
//TODO: configure to RESTful (GET, POST, PUT, DELETE)
@Controller
public class StudentsService {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(method=RequestMethod.GET, value="/students", headers="Accept=application/json")
	public @ResponseBody SimpleGrid<User> getStudents(
			@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sortBy,
    		@RequestParam(value="sord", required=false) String sortDirrection) {
				
		return userDao.getUsersPage(null, page, rows, sortBy, sortDirrection);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/students/create", headers="Accept=application/json")
	public @ResponseBody Boolean createStudent(
			@RequestParam String login,
			@RequestParam String password,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String address,
			@RequestParam String phone,
			@RequestParam Boolean isActive) {
		
		User newUser = new User();
		newUser.setLogin(login);
		newUser.setPassword(password);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setAddress(address);
		newUser.setPhone(phone);
		newUser.setIsActive(isActive);
		
		userDao.insert(newUser);
		return true;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/students/update", headers="Accept=application/json")
	public @ResponseBody Boolean updateStudent(	@RequestParam long id, @RequestParam String login, @RequestParam String password, @RequestParam String firstName,
												@RequestParam String lastName, @RequestParam String address, @RequestParam String phone, @RequestParam Boolean isActive) {
		
		boolean result = false;
		User user = userDao.findById(id);
		
		if(user != null) {
			user.setLogin(login);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setAddress(address);
			user.setPhone(phone);
			user.setIsActive(isActive);
			
			result = userDao.updateUser(user);
		}
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/students/delete", headers="Accept=application/json")
	public @ResponseBody Boolean deleteStudent(
			@RequestParam String id) {
		
		String[] parsedIds = id.split(",");
		int count = parsedIds.length;
		ArrayList<Long> ids = new ArrayList<Long>();
		
		for(int i = 0; i < count; i++) {
			ids.add(Long.parseLong(parsedIds[i]));
		}
		
		return userDao.deleteUsers(ids);
	}
}