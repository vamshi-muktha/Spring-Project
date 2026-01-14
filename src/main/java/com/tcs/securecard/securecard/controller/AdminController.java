package com.tcs.securecard.securecard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.securecard.securecard.api.MailFeignClient;
import com.tcs.securecard.securecard.dto.MailRequest;
import com.tcs.securecard.securecard.models.Card;
import com.tcs.securecard.securecard.service.AdminService;
import com.tcs.securecard.securecard.service.CardService;
import com.tcs.securecard.securecard.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	CardService cs;
	
	@Autowired
	AdminService as;
	
	@Autowired
	UserService us;
	
	@Autowired
	MailFeignClient mfc;
	
	@GetMapping("/cardsbyuser/{uid}")
	public List<Card> getCardsByUserId(@PathVariable int uid){
		return cs.getCardsByUserId(uid);
	}
	@PutMapping("/changeStatus/{cid}")
	public void changeStatus(@PathVariable int cid, @PathVariable int userId) {
		as.changeStatus(cid, userId);
		String email = us.getById(userId).getEmail();
		mfc.sendMail(new MailRequest(email, "Card Temporarily Blocked ⚠️", "<div style='font-family:Arial,sans-serif;background:#f4f6f8;padding:20px'><div style='max-width:520px;margin:auto;background:#ffffff;padding:24px;border-radius:8px;box-shadow:0 4px 10px rgba(0,0,0,0.08)'><h2 style='color:#dc2626;margin-bottom:10px'>Card Temporarily Blocked</h2><p style='color:#333;font-size:15px'>Hello,</p><p style='color:#555;font-size:14px;line-height:1.6'>Your SecureCard with <b>Card ID: "+cid+"</b> has been <b>temporarily blocked</b> due to security reasons.</p><p style='color:#555;font-size:14px;line-height:1.6'>To protect your account, we have restricted transactions on this card until further review.</p><p style='margin-top:20px;color:#333;font-size:14px'>Please contact the administrator or SecureCard support team for more details and assistance.</p><p style='margin-top:30px;color:#777;font-size:13px'>🔒 Your security is our priority<br/>— Team SecureCard</p></div></div>"));
	}
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable int userId) {
		List<Card> al = getCardsByUserId(userId);
		for(Card c : al) {
			cs.deleteCard(c.getCid());
		}
		as.deleteUser(userId);
		String email = us.getById(userId).getEmail();
		mfc.sendMail(new MailRequest(email, "Card Request Approved 🎉", "<div style='font-family:Arial,sans-serif;background:#f4f6f8;padding:20px'><div style='max-width:520px;margin:auto;background:#ffffff;padding:24px;border-radius:8px;box-shadow:0 4px 10px rgba(0,0,0,0.08)'><h2 style='color:#16a34a;margin-bottom:10px'>Your SecureCard Is Ready 🚀</h2><p style='color:#333;font-size:15px'>Hello,</p><p style='color:#555;font-size:14px;line-height:1.6'>Great news! Your SecureCard request has been <b>approved</b> successfully.</p><p style='color:#555;font-size:14px;line-height:1.6'>You can now start using your card for secure transactions and enjoy seamless digital payments.</p><p style='margin-top:20px;color:#333;font-size:14px'>If you have any questions or need assistance, our support team is always here to help.</p><p style='margin-top:30px;color:#777;font-size:13px'>💳 Happy spending!<br/>— Team SecureCard</p></div></div>"));
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
		String email = cs.getCardById(cid).get().getUser().getEmail();
		mfc.sendMail(new MailRequest(email, "Your card request accepted", "You can start using your card"));
		return "Accepted";
	}
	
	@PutMapping("/reject/{cid}")
	public String reject(@PathVariable int cid) {
		cs.requestCard(cid);
		String email = cs.getCardById(cid).get().getUser().getEmail();
		mfc.sendMail(new MailRequest(email, "Your card request was rejected", "For more details contact admin"));
		return "Rejected";
	}
	
	@PostMapping("/sendmail")
	public String sendMail(@RequestBody MailRequest mr) {
		mfc.sendMail(mr);
		return "Mail sent successfully";
	}
}
