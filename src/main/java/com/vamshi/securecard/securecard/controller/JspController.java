package com.vamshi.securecard.securecard.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vamshi.securecard.securecard.dto.CardDto;
import com.vamshi.securecard.securecard.dto.UserDto;
import com.vamshi.securecard.securecard.models.Card;
import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.service.CardService;
import com.vamshi.securecard.securecard.service.UserService;

@Controller
public class JspController {

	@Autowired
	UserService service;

	@Autowired
	CardService cs;

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

	@GetMapping("/cardform")
	public String getCardForm() {
		return "cardform";
	}
	
	@GetMapping("/external")
	public String getExternal() {
		return "external";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute UserDto user, Model model) {
		if (service.existsByEmail(user.getEmail())) {
			model.addAttribute("msg", "User already exists. Please login.");
			return "login";
		}

		PasswordEncoder encoder = new BcryptPassword4jPasswordEncoder();
		String enc = encoder.encode(user.getPassword());

		User u = new User();
		BeanUtils.copyProperties(user, u);
		u.setRole("USER");
		u.setPassword(enc);
		u.setOriginalPassword(user.getPassword());


		service.create(u);

		model.addAttribute("msg", "Registration successful. Please login.");
		return "login";
	}

	@PostMapping("/applyCard")
	public String applyCard(@ModelAttribute CardDto card, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		System.out.println(username);
		Card c = new Card();
		BeanUtils.copyProperties(card, c);
		c.setCardExpiry("01/2030");
		User u = service.findByUsername(username);

		c.setUser(u);
		c.setCardName(u.getName());
		long cardNumber = (long) (Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L;
		c.setCardNumber(Long.toString(cardNumber));
		int cvv = (int) (Math.random() * 900) + 100;
		c.setCardCVV(String.valueOf(cvv));
		c.setActive(true);
		cs.saveCard(c);
		model.addAttribute("msg", "Card Applied successfully");
		return "Home";

	}
	
	

}
