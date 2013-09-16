/**
 * 
 */
package com.epam.trn.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.model.UsersPage;

/**
 * @author Siarhei Klimuts
 *
 */
@Controller
public class StudentsService {
	private static Integer asd = 0;
	@Autowired
	private UserDao userDao;

	@RequestMapping(method=RequestMethod.GET, value="/students", headers="Accept=application/json")
	public @ResponseBody UsersPage getStudents(
			@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sortBy,
    		@RequestParam(value="sord", required=false) String sortDirrection) {
		
		return userDao.getUsersPage(null, page, rows, sortBy, sortDirrection);
	}
	
	@RequestMapping(method=RequestMethod.POST/*DELETE*/, value="/students")
	public void getStudent() {
		asd = 1;
	}
}
