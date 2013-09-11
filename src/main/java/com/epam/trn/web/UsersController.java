package com.epam.trn.web;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.model.User;
import com.epam.trn.service.UsersService;
import com.epam.trn.web.grid.Grid;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@JsonSerialize
	public @ResponseBody()
	Grid<User> getUsers(HttpServletRequest req) {
		return usersService.getUsersGrid();
	}
}
