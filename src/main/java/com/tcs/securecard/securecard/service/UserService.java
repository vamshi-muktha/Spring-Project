package com.tcs.securecard.securecard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcs.securecard.securecard.models.User;
import com.tcs.securecard.securecard.repository.UserRepo;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo ur;


    public User create(User user) {
        return ur.save(user);
    }

    public List<User> getAll() {
        return ur.findAll();
    }

    public User getById(int id) {
        return ur.findById(id).orElse(null);
    }

    public User update(int id, User user) {
        user.setId(id);
        return ur.save(user);
    }

    public void delete(int id) {
        ur.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ur.findByUsername(username);
    }

	public boolean existsByUsername(String email) {
		// TODO Auto-generated method stub
		return ur.existsByUsername(email);
	}

	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return ur.existsByEmail(email);
	}

	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return ur.findByUsername(username);
	}
}
