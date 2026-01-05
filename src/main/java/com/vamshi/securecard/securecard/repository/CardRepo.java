package com.vamshi.securecard.securecard.repository;

import com.vamshi.securecard.securecard.models.Card;
import com.vamshi.securecard.securecard.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<Card, String> {

	List<Card> findByUser(User user);

}
