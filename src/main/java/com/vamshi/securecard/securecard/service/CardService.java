package com.vamshi.securecard.securecard.service;

import com.vamshi.securecard.securecard.models.Card;
import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.repository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CardRepo cr;

    public List<Card> getAllCards() {
        return cr.findAll();
    }

    public Optional<Card> getCardById(String id) {
        return cr.findById(id);
    }

    public Card saveCard(Card card) {
        return cr.save(card);
    }

    public Card updateCard(Card card) {
        return cr.save(card);
    }

    public void deleteCard(String id) {
        cr.deleteById(id);
    }

	public List<Card> findByUser(User user) {
		// TODO Auto-generated method stub
		return cr.findByUser(user);
	}
}
