package com.epam.trn.web;

import java.security.NoSuchAlgorithmException;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.model.User;
import com.epam.trn.service.UsersService;
import com.epam.trn.web.grid.Grid;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@JsonSerialize
	public @ResponseBody Grid<User> getUsers(
			@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sortBy,
    		@RequestParam(value="sord", required=false) String sortDirrection) {
		
		return usersService.getUsersGrid(null, page, rows, sortBy, sortDirrection);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/create")
	public @ResponseBody void createUser(
				@RequestParam(required=true) String email,
				@RequestParam(required=false) String login,
				@RequestParam(required=false) String password,
				@RequestParam(required=false) String firstName,
				@RequestParam(required=false) String lastName,
				@RequestParam(required=false) String address,
				@RequestParam(required=false) String phone,
				@RequestParam(required=false) Boolean isActive) throws NoSuchAlgorithmException {
		
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setLogin(login);
		newUser.setPassword(password);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setAddress(address);
		newUser.setPhone(phone);
		newUser.setIsActive(isActive);
		
		usersService.createUser(newUser);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/update")
	public @ResponseBody void updateUser(	
				@RequestParam Integer id, 
				@RequestParam String email,
				@RequestParam String login,
				@RequestParam String firstName,
				@RequestParam String lastName,
				@RequestParam String address,
				@RequestParam String phone,
				@RequestParam Boolean isActive) throws NoSuchAlgorithmException {
		
		User newUser = new User();
		newUser.setId(id);
		newUser.setEmail(email);
		newUser.setLogin(login);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setAddress(address);
		newUser.setPhone(phone);
		newUser.setIsActive(isActive);
		
		usersService.updateUser(newUser);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/update/cell")
	public @ResponseBody void updateUser(
			@RequestParam(required=true)  String oper,
			@RequestParam(required=true)  Integer id, 
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String login,
			@RequestParam(required=false) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String address,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) Boolean isActive) {
		
		User newUser = new User();
		newUser.setId(id);
		newUser.setEmail(email);
		newUser.setLogin(login);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setAddress(address);
		newUser.setPhone(phone);
		newUser.setIsActive(isActive);
		usersService.updateUser(newUser);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/delete")
	public @ResponseBody void deleteUser(@RequestParam String id) {
		usersService.deleteUsers(id);
	}
}
