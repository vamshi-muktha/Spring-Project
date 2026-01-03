package com.vamshi.securecard.securecard.repository;

import com.vamshi.securecard.securecard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}
