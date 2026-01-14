package com.tcs.securecard.securecard.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.tcs.securecard.securecard.api.MailFeignClient;
import com.tcs.securecard.securecard.dto.MailRequest;
import com.tcs.securecard.securecard.models.Query;
import com.tcs.securecard.securecard.models.User;
import com.tcs.securecard.securecard.service.QueryService;
import com.tcs.securecard.securecard.service.UserService;

@RestController
@RequestMapping("/query")
public class QueryController {
	@Autowired
	QueryService qs;
	@Autowired
	UserService us;
	@Autowired
	MailFeignClient mfc;
	@PostMapping
	public String saveQuery(@RequestParam String subject, @RequestParam String message) {
		Query q = new Query();
		q.setMessage(message);
		q.setSubject(subject);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		User u = us.findByUsername(username);
		q.setEmail(u.getEmail());
		q.setName(u.getName());
		q.setActive(true);
		q.setUserId(u.getId());
		qs.saveQuery(q);
		
		return "saved";
		
	}
	
	@GetMapping
	public List<Query> getQueries(){
		return qs.findAll();
	}
	
	@DeleteMapping("/{qid}")
	public String deleteQuery(@PathVariable int qid) {
		return qs.deleteById(qid);
	}
	
	@GetMapping("/activeQueries")
	public List<Query> getActiveQueries(){
		return getQueries().stream().filter(x -> x.isActive()).collect(Collectors.toList());
	}
	
	@PutMapping
	public String changeStatus(int qid) {
		qs.changeStatus(qid);
		return "changed";
	}
	@PostMapping("/sendmail")
	public String sendMail(@RequestBody MailRequest mr, @RequestParam int qid) {
		System.out.println(mr.toString());
		mfc.sendMail(mr);
		changeStatus(qid);
		return "Sent";
	}
	
}
