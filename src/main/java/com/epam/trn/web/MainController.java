package com.epam.trn.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.trn.security.AuthService;

@Controller
public class MainController {

	@RequestMapping("/index")
	public String getMainPage(Map<String, Object> map) {
		return "main";
	}

	@RequestMapping("/admin")
	public String getAdminPage(Map<String, Object> map) {
		return "admin";
	}

	@RequestMapping("/studentpage")
	public String getStudentPage(Map<String, Object> map) {
		return "students";
	}

	@RequestMapping("/")
	public String home() {
		if (AuthService.hasAdminRole())
			return "redirect:/index";
		else
			return "redirect:/admin";
	}
}
