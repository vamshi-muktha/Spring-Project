package com.vamshi.securecard.securecard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vamshi.securecard.securecard.models.Payment;
import com.vamshi.securecard.securecard.repository.PaymentRepo;

@Service
public class PaymentService {
	@Autowired
	PaymentRepo pr;

	
	public void savePayment(Payment p) {
		pr.save(p);
	}

	public List<Payment> getPending(int uid, String string) {
		// TODO Auto-generated method stub
		return pr.findPayments(uid, string);
	}

	public String changePayment(int pid, String status, int cid) {
		pr.changePayment(pid, status, cid);
		return "Done";
	}
}
