package com.epam.trn.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.service.UsersService;
import com.epam.trn.service.UsersService.UsersList;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody()
	UsersList getUsers(HttpServletRequest req) {
		return usersService.getUsers();
	}
}
