package com.tcs.securecard.securecard.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.securecard.securecard.models.Card;
import com.tcs.securecard.securecard.models.User;
import com.tcs.securecard.securecard.service.CardService;
import com.tcs.securecard.securecard.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cards")
public class CardController {
	@Autowired
	CardService cs;

	@Autowired
	UserService us;

	@Autowired
	UserController uc;

	@GetMapping
	public List<Card> getAllCards() {
		return cs.getAllCards();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Card> getCardById(@PathVariable int id) {
		Optional<Card> card = cs.getCardById(id);
		return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/mycards")
	public ResponseEntity<List<Card>> getMyCards() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Card> cards = cs.findByUser(us.findByUsername(username));
		return new ResponseEntity<>(cards, HttpStatus.OK);
	}

//	@PostMapping()
//	public Card createCard(@Valid @RequestBody Card card) {
//
//		// 🔥 Extract userId safely
//		int userId = card.getUser().getId();
//
//		// 🔥 Fetch MANAGED user
//		User managedUser = us.getById(userId);
//
//		// 🔥 Replace detached user with managed user
//		card.setUser(managedUser);
//
//		return cs.saveCard(card);
//	}

	@PutMapping("/{id}")
	public ResponseEntity<Card> updateCard(@PathVariable int id, @Valid @RequestBody Card card) {
		if (cs.getCardById(id).isPresent()) {
			card.setCardNumber("");
			return ResponseEntity.ok(cs.updateCard(card));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCard(@PathVariable int id) {
		if (cs.getCardById(id).isPresent()) {
			cs.deleteCard(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/changeStatus/{cid}")
	public Card changeStatus(@PathVariable int cid) {
		return cs.changeStatus(cid);
	}
	
	@GetMapping("/activeCards")
	public List<Card> getActiveCards() {
		List<Card> al =  cs.getActiveCards();
		int uid = uc.getCurrUser().getId();
		al = al.stream().filter(x -> x.getUser().getId() == uid).filter(x -> x.getCardStatus().equals("ACCEPTED")).collect(Collectors.toList());
		return al;
	}
	
	@PutMapping("/updateBalance/{cid}/{amt}")
	public String updateBalance(@PathVariable int cid,@PathVariable int amt) {
		cs.updateBalance(cid, amt);
		return "Updated";
	}
	
	@PutMapping("/updateType/{cid}")
	public String updateType(@PathVariable int cid, @RequestParam String newType) {
		cs.updateType(cid, newType);
		return "Updated";
	}
}
