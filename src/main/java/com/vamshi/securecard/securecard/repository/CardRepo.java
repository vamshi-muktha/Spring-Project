package com.vamshi.securecard.securecard.repository;

import com.vamshi.securecard.securecard.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<Card, String> {

}
