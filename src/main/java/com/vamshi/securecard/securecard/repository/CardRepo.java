package com.vamshi.securecard.securecard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamshi.securecard.securecard.models.Card;
import com.vamshi.securecard.securecard.models.User;

public interface CardRepo extends JpaRepository<Card, Integer> {

	List<Card> findByUser(User user);

}
