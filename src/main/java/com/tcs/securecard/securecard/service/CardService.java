package com.tcs.securecard.securecard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.tcs.securecard.securecard.api.MailFeignClient;
import com.tcs.securecard.securecard.dto.MailRequest;
import com.tcs.securecard.securecard.models.Card;
import com.tcs.securecard.securecard.models.User;
import com.tcs.securecard.securecard.repository.CardRepo;

import jakarta.validation.Valid;

@Service
public class CardService {

    @Autowired
    CardRepo cr;
    
    @Autowired
    UserService us;
    
    @Autowired
    MailFeignClient mfc;

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

	public Card changeStatus(int cid) {
		Card c = getCardById(cid).get();
		c.setActive(!c.isActive());
		cr.save(c);
		return c;
	}
	
	public List<Card> getActiveCards() {
//		System.out.println("called");
		return cr.findByIsActive(true);
	}

	public List<Card> getCardsByUserId(int uid) {
		// TODO Auto-generated method stub
		User u = us.getById(uid);
		return findByUser(u);
		
	}

	public List<Card> findByCardStatus(String string) {
		// TODO Auto-generated method stub
		return cr.findByCardStatus(string);
	}

	public void acceptCard(int cid) {
		// TODO Auto-generated method stub
		getCardById(cid).get().setCardStatus("ACCEPTED");
		cr.save(getCardById(cid).get());
		//send mail
	}

	public void requestCard(int cid) {
		// TODO Auto-generated method stub
		getCardById(cid).get().setCardStatus("REJECTED");
		cr.save(getCardById(cid).get());
		//send mail
	}

	public void updateBalance(int cid, int amt) {
		// TODO Auto-generated method stub
		getCardById(cid).get().setBalance(amt);
		cr.save(getCardById(cid).get());
	}

	public void updateType(int cid, String newType) {
		// TODO Auto-generated method stub
		getCardById(cid).get().setCardType(newType);
		cr.save(getCardById(cid).get());
	}
}
