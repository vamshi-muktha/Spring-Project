package com.tcs.securecard.securecard.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.securecard.securecard.api.MailFeignClient;
import com.tcs.securecard.securecard.api.OtpFeignClient;
import com.tcs.securecard.securecard.dto.MailRequest;
import com.tcs.securecard.securecard.dto.UserDto;
import com.tcs.securecard.securecard.models.User;
import com.tcs.securecard.securecard.service.CardService;
import com.tcs.securecard.securecard.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService service;
	
	@Autowired
	CardService cs;
	
	@Autowired
	OtpFeignClient ofc;
	
	@Autowired
	MailFeignClient mfc;

	@GetMapping("/api/auth/check")
	public ResponseEntity<?> getCurrentUser(Authentication authentication) {

	    if (authentication == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    return ResponseEntity.ok(authentication.getPrincipal());
	}
	
	@GetMapping("/getcurruser")
	public User getCurrUser() {
		System.out.println("Called>>>");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		System.out.println(username);
		return service.findByUsername(username);
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") UserDto user,Model model, BindingResult result) {

		if (result.hasErrors()) {
			return "Errors";
		}
		if (service.existsByEmail(user.getEmail())) {
			return "User Exists with given email";
		}
		
		ofc.sendOtp(user.getEmail());
		return "Otp sent";
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
	}

    /*@PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        System.out.println("Called");
        PasswordEncoder p = new BcryptPassword4jPasswordEncoder();
        String pass = user.getPassword();
        String enc = p.encode(pass);
        System.out.println(enc);
        user.setPassword(enc);
        user.setOriginalPassword(pass);
        User savedUser = service.create(user);
        return ResponseEntity.status(201).body(savedUser);
    }*/
	
	@PostMapping("/verify")
	public boolean verifyOtp(@RequestBody UserDto user, @RequestParam String otp) {
		boolean res = ofc.verifyOtp(user.getEmail(), otp);
		PasswordEncoder encoder = new BcryptPassword4jPasswordEncoder();
		String enc = encoder.encode(user.getPassword());

		User u = new User();
		BeanUtils.copyProperties(user, u);
		u.setRole("USER");
		u.setPassword(enc);
		u.setOriginalPassword(user.getPassword());

		User x = service.create(u);
		mfc.sendMail(new MailRequest(x.getEmail(), "Welcome to SecureCard", "<div style='font-family:Arial,sans-serif;background:#f4f6f8;padding:20px'><div style='max-width:520px;margin:auto;background:#ffffff;padding:24px;border-radius:8px;box-shadow:0 4px 10px rgba(0,0,0,0.08)'><h2 style='color:#ff7a18;margin-bottom:10px'>Welcome to SecureCard 🎉</h2><p style='color:#333;font-size:15px'>Hello <b>"+x.getName()+"</b>,</p><p style='color:#555;font-size:14px;line-height:1.6'>Thank you for subscribing to <b>SecureCard</b>. Your account has been successfully created and you are now part of a secure, fast, and modern digital payment experience.</p><p style='color:#555;font-size:14px;line-height:1.6'>With SecureCard, you can safely manage your cards, complete transactions, and enjoy seamless authentication with OTP and Google login.</p><p style='margin-top:20px;color:#333;font-size:14px'>If you have any questions, feel free to reach out to us anytime.</p><p style='margin-top:30px;color:#777;font-size:13px'>🚀 Team SecureCard</p></div></div>"));
		return res;
	}
	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		User user = service.getById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @Valid @RequestBody User user) {

		User updated = service.update(id, user);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
