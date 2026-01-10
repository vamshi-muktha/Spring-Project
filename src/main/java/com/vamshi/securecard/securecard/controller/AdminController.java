package com.vamshi.securecard.securecard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vamshi.securecard.securecard.models.Card;
import com.vamshi.securecard.securecard.service.AdminService;
import com.vamshi.securecard.securecard.service.CardService;
import com.vamshi.securecard.securecard.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	CardService cs;
	
	@Autowired
	AdminService as;
	
	@Autowired
	UserService us;
	
	@GetMapping("/cardsbyuser/{uid}")
	public List<Card> getCardsByUserId(@PathVariable int uid){
		return cs.getCardsByUserId(uid);
	}
	@PutMapping("/changeStatus/{cid}")
	public void changeStatus(@PathVariable int cid, @PathVariable int userId) {
		as.changeStatus(cid, userId);
	}
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable int userId) {
		List<Card> al = getCardsByUserId(userId);
		for(Card c : al) {
			cs.deleteCard(c.getCid());
		}
		as.deleteUser(userId);
		return "User deleted successfully";
	}
	
	@GetMapping("/pendingRequests")
	public List<Card> pendingRequests(){
		List<Card> res =  cs.findByCardStatus("PENDING");
		for(Card c : res) {
			int uid = c.getUser().getId();
			
			c.setUser(us.getById(uid));
			System.out.println(c.getUser().getId());
		}
		return res;
	}
	
	@PutMapping("/accept/{cid}")
	public String accept(@PathVariable int cid) {
		cs.acceptCard(cid);
		return "Accepted";
	}
	
	@PutMapping("/reject/{cid}")
	public String reject(@PathVariable int cid) {
		cs.requestCard(cid);
		return "Rejected";
	}
}
