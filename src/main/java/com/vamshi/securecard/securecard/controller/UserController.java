package com.vamshi.securecard.securecard.controller;

import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService service;

//    @PostMapping("/register")
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        System.out.println("Called");
//        PasswordEncoder p = new BcryptPassword4jPasswordEncoder();
//        String pass = user.getPassword();
//        String enc = p.encode(pass);
//        System.out.println(enc);
//        user.setPassword(enc);
//        user.setOriginalPassword(pass);
//        User savedUser = service.create(user);
//        return ResponseEntity.status(201).body(savedUser);
//    }

	

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
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {

		User updated = service.update(id, user);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
