package com.vamshi.securecard.securecard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService service;

	@GetMapping("/getcurruser")
	public User getCurrUser() {
		System.out.println("Called>>>");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return service.findByUsername(username);
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
