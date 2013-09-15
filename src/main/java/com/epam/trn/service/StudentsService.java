/**
 * 
 */
package com.epam.trn.service;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Siarhei Klimuts
 *
 */
@Controller
public class StudentsService {
	@RequestMapping(method=RequestMethod.GET, value="/students", 
			headers="Accept=application/json")
	public  @ResponseBody String getSmtn() {
		return "{'id':1,'name':'Huang Yi Ming'}";


	}
}
