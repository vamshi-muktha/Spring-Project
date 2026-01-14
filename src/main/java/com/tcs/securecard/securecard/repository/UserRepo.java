package com.tcs.securecard.securecard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.securecard.securecard.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
