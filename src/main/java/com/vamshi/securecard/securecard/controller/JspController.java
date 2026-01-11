package com.vamshi.securecard.securecard.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vamshi.securecard.securecard.api.MailFeignClient;
import com.vamshi.securecard.securecard.dto.CardDto;
import com.vamshi.securecard.securecard.dto.MailRequest;
import com.vamshi.securecard.securecard.models.Card;
import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.service.CardService;
import com.vamshi.securecard.securecard.service.UserService;

import jakarta.validation.Valid;

@Controller
public class JspController {

	@Autowired
	UserService service;

	@Autowired
	CardService cs;
	
	@Autowired
	MailFeignClient mfc;

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

//	@PostMapping("/register")
//	public String register(@Valid @ModelAttribute("user") UserDto user,Model model, BindingResult result) {
//
//		if (result.hasErrors()) {
//			return "register";
//		}
//		if (service.existsByEmail(user.getEmail())) {
//			model.addAttribute("msg", "User already exists. Please login.");
//			return "login";
//		}
//		PasswordEncoder encoder = new BcryptPassword4jPasswordEncoder();
//		String enc = encoder.encode(user.getPassword());
//
//		User u = new User();
//		BeanUtils.copyProperties(user, u);
//		u.setRole("USER");
//		u.setPassword(enc);
//		u.setOriginalPassword(user.getPassword());
//
//		User x = service.create(u);
//
//		model.addAttribute("msg", "Registration successful. Please login.");
//		return "login";
//	}

	@PostMapping("/applyCard")
	public String applyCard(@Valid @ModelAttribute CardDto card, Model model, BindingResult result) {
		System.out.println("called card");
		if (result.hasErrors()) {
			return "cardform";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		System.out.println(username);
		Card c = new Card();
		BeanUtils.copyProperties(card, c);
		c.setCardExpiry("01/2030");
		if(c.getType().equals("Debit")) {
			c.setBalance(0);
		}
		else {
			if(c.getCardType().equalsIgnoreCase("platinum")) {
				c.setBalance(100000);
			}
			if(c.getCardType().equalsIgnoreCase("gold")) {
				c.setBalance(50000);
			}
			if(c.getCardType().equalsIgnoreCase("silver")) {
				c.setBalance(30000);
			}
		}
		c.setCardStatus("PENDING");
		User u = service.findByUsername(username);
		c.setUser(u);
		c.setCardName(u.getName());
		long cardNumber = (long) (Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L;
		c.setCardNumber(Long.toString(cardNumber));
		int cvv = (int) (Math.random() * 900) + 100;
		c.setCardCVV(String.valueOf(cvv));
		c.setActive(true);
		System.out.println("called");
		cs.saveCard(c);
		mfc.sendMail(new MailRequest(u.getEmail(), "New Card Applied", "Card Details : no : " + c.getCardNumber() + " cvv : " + c.getCardCVV() + " Waiting for admin approval"));
		mfc.sendMail(new MailRequest("mukthavamshi123@gmail.com", "New Card request", u.getUsername() + " applied card"));
		model.addAttribute("msg", "Card Applied successfully");
		return "Home";

	}
	
	


}
