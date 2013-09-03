package com.epam.trn.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/index")
	public String listContacts(Map<String, Object> map) {
		return "main";
	}

	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}
}
