package com.vamshi.securecard.securecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.service.UserService;

@Controller
public class JspController {
	
	@Autowired
	UserService service;
	
	@GetMapping("/home")
	public String getHome() {
		return "Home";
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(
	        @RequestParam int id,
	        @RequestParam String name,
	        @RequestParam String username,
	        @RequestParam String email,
	        @RequestParam String password,
	        @RequestParam String address,
	        @RequestParam String mobileNumber,
	        Model model
	) {
	    if (service.existsByEmail(email)) {
	        model.addAttribute("msg", "User already exists. Please login.");
	        return "login";
	    }

	    PasswordEncoder encoder = new BcryptPassword4jPasswordEncoder();
	    String enc = encoder.encode(password);

	    User user = new User();
	    user.setId(id);
	    user.setName(name);
	    user.setUsername(username);
	    user.setEmail(email);
	    user.setPassword(enc);
	    user.setOriginalPassword(password);
	    user.setAddress(address);
	    user.setMobileNumber(mobileNumber);
	    user.setRole("USER");

	    service.create(user);

	    model.addAttribute("msg", "Registration successful. Please login.");
	    return "login";
	}

}
