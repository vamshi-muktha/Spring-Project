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
		// TODO Auto-generated method stub
		pr.save(p);
	}

	public List<Payment> getPending(int uid, String string) {
		// TODO Auto-generated method stub
		return pr.findPayments(uid, string);
	}

	public int changePayment(int pid, String status) {
		// TODO Auto-generated method stub
		return pr.changePayment(pid, status);
	}
}
