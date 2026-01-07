package com.vamshi.securecard.securecard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vamshi.securecard.securecard.models.Payment;
import com.vamshi.securecard.securecard.service.PaymentService;
import com.vamshi.securecard.securecard.service.UserService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	PaymentService ps;
	
	@Autowired
	UserService us;

	@PostMapping
	public String newPayment(@RequestParam int orderId, @RequestParam int amount) {
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
	public int changePayment(@RequestParam int pid, @RequestParam String status) {

		return ps.changePayment(pid, status);

	}
}
