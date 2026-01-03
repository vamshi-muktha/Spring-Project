package com.vamshi.securecard.securecard.service;

import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
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
}
