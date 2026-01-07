package com.vamshi.securecard.securecard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vamshi.securecard.securecard.models.Card;
import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.repository.CardRepo;

import jakarta.validation.Valid;

@Service
public class CardService {

    @Autowired
    CardRepo cr;

    public List<Card> getAllCards() {
        return cr.findAll();
    }

    public Optional<Card> getCardById(int id) {
        return cr.findById(id);
    }

    public Card saveCard(@Valid Card card) {
        return cr.save(card);
    }

    public Card updateCard(@Valid Card card) {
        return cr.save(card);
    }

    public void deleteCard(int id) {
        cr.deleteById(id);
    }

	public List<Card> findByUser(User user) {
		// TODO Auto-generated method stub
		return cr.findByUser(user);
	}
}
