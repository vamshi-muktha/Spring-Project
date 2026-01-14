package com.tcs.securecard.securecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.securecard.securecard.models.Card;

@Service
public class AdminService {
	@Autowired
	CardService cs;
	
	@Autowired
	UserService us;

	public void changeStatus(int cid, int userid) {
		// TODO Auto-generated method stub
		cs.changeStatus(cid) ;
		//send main to user
	}

	public void deleteUser(int userId) {
		us.delete(userId);
		//send mail
		
	}
	
	
}
