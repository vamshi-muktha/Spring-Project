package com.tcs.securecard.securecard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.securecard.securecard.models.Card;
import com.tcs.securecard.securecard.models.User;

public interface CardRepo extends JpaRepository<Card, Integer> {

	List<Card> findByUser(User user);
	
	List<Card> findByIsActive(boolean b);

	List<Card> findByCardStatus(String string);
}
