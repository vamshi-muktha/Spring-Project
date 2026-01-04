package com.vamshi.securecard.securecard.controller;

import com.vamshi.securecard.securecard.models.Card;
import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.service.CardService;
import com.vamshi.securecard.securecard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    CardService cs;

    @Autowired
    UserService us;

    @GetMapping
    public List<Card> getAllCards() {
        return cs.getAllCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable String id) {
        Optional<Card> card = cs.getCardById(id);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Card createCard(@RequestBody Card card) {

        // 🔥 Extract userId safely
        int userId = card.getUser().getId();

        // 🔥 Fetch MANAGED user
        User managedUser = us.getById(userId);

        // 🔥 Replace detached user with managed user
        card.setUser(managedUser);

        return cs.saveCard(card);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable String id, @RequestBody Card card) {
        if (cs.getCardById(id).isPresent()) {
            card.setCardNumber(id);
            return ResponseEntity.ok(cs.updateCard(card));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable String id) {
        if (cs.getCardById(id).isPresent()) {
            cs.deleteCard(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
