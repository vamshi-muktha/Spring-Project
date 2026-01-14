package com.tcs.securecard.securecard.controller;

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

import com.tcs.securecard.securecard.api.MailFeignClient;
import com.tcs.securecard.securecard.api.OtpFeignClient;
import com.tcs.securecard.securecard.dto.MailRequest;
import com.tcs.securecard.securecard.models.Payment;
import com.tcs.securecard.securecard.service.CardService;
import com.tcs.securecard.securecard.service.PaymentService;
import com.tcs.securecard.securecard.service.UserService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	PaymentService ps;
	
	@Autowired
	UserService us;
	
	@Autowired
	CardService cs;
	
	@Autowired
	OtpFeignClient ofc;
	
	@Autowired
	MailFeignClient mfc;

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
		if (cid == -1 || "REJECTED".equals(status)) {
		    return ResponseEntity.ok(ps.changePayment(pid, status, cid));
		}

		if(cs.getCardById(cid).get().getBalance() < amt) {
			return new ResponseEntity<>("Amount low in that card try with another card", HttpStatus.OK);
		}
		ofc.sendOtp(cs.getCardById(cid).get().getUser().getEmail());
		return new ResponseEntity<>("OTP_REQUIRED", HttpStatus.OK);
//		cs.updateBalance(cid, cs.getCardById(cid).get().getBalance() - amt);
//		
//		return ResponseEntity.ok(ps.changePayment(pid, status, cid));

	}
	@PostMapping("/verify")
	public boolean verifyOtp(@RequestParam String email, @RequestParam String otp, @RequestParam int cid, @RequestParam int pid, @RequestParam int amount, @RequestParam String status) {
		boolean res =  ofc.verifyOtp(email, otp);
		if(res)cs.updateBalance(cid, cs.getCardById(cid).get().getBalance() - amount);
		if(res)ps.changePayment(pid, status, cid);
		if(res) mfc.sendMail(new MailRequest(email, "Payment Successful ✅", "<div style='font-family:Arial,sans-serif;background:#f4f6f8;padding:20px'><div style='max-width:520px;margin:auto;background:#ffffff;padding:24px;border-radius:8px;box-shadow:0 4px 10px rgba(0,0,0,0.08)'><h2 style='color:#16a34a;margin-bottom:10px'>Payment Successful 🎉</h2><p style='color:#333;font-size:15px'>Hello,</p><p style='color:#555;font-size:14px;line-height:1.6'>We’re happy to inform you that your payment has been <b>successfully processed</b> using SecureCard.</p><p style='color:#555;font-size:14px;line-height:1.6'>You can now continue using our services without any interruption. All transactions are secured and encrypted for your safety.</p><p style='margin-top:20px;color:#333;font-size:14px'>If you did not make this payment or notice anything unusual, please contact our support team immediately.</p><p style='margin-top:30px;color:#777;font-size:13px'>💳 Thank you for choosing SecureCard<br/>— Team SecureCard</p></div></div>"));
		return res;
	}
	
	@GetMapping("/{cid}/transactions")
	public List<Payment> getByCid(@PathVariable int cid){
		return ps.getByCid(cid);
	}
}
