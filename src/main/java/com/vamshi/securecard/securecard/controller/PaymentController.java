package com.vamshi.securecard.securecard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vamshi.securecard.securecard.models.Payment;
import com.vamshi.securecard.securecard.service.CardService;
import com.vamshi.securecard.securecard.service.PaymentService;
import com.vamshi.securecard.securecard.service.UserService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	PaymentService ps;
	
	@Autowired
	UserService us;
	
	@Autowired
	CardService cs;

	@PostMapping
	public String newPayment(@RequestParam int orderId, @RequestParam int amount) {
		System.out.println(orderId + " " + amount);
		Payment p = new Payment();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		p.setOrderId(orderId);
		p.setAmount(amount);
		p.setStatus("pending");
		p.setUid(us.findByUsername(username).getId());
		ps.savePayment(p);
		return "payment saved successfully";
	}

	@GetMapping("/getPending")
	public List<Payment> getPending(@RequestParam int uid) {
		return ps.getPending(uid, "pending");
	}

	@PutMapping("/payNow")
	public ResponseEntity<String> changePayment(@RequestParam int pid,@RequestParam int amt, @RequestParam String status, @RequestParam int cid) {
		System.out.println(pid + " " + cid);
		if(cs.getCardById(cid).get().getBalance() < amt) {
			return new ResponseEntity<>("Amount low in that card try with another card", HttpStatus.OK);
		}
		cs.updateBalance(cid, cs.getCardById(cid).get().getBalance() - amt);
		
		return ResponseEntity.ok(ps.changePayment(pid, status, cid));

	}
	
	@GetMapping("/{cid}/transactions")
	public List<Payment> getByCid(@PathVariable int cid){
		return ps.getByCid(cid);
	}
}
