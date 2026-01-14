package com.tcs.securecard.securecard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.securecard.securecard.models.Query;
import com.tcs.securecard.securecard.repository.QueryRepo;

@Service
public class QueryService {
	@Autowired
	QueryRepo qr;

	public void saveQuery(Query q) {
		// TODO Auto-generated method stub
		qr.save(q);
	}

	public List<Query> findAll() {
		// TODO Auto-generated method stub
		return qr.findAll();
	}

	public String deleteById(int qid) {
		// TODO Auto-generated method stub
		qr.deleteById(qid);
		return "Deleted";
	}

	public void changeStatus(int qid) {
		// TODO Auto-generated method stub
		Query q = qr.findById(qid).get();
		q.setActive(!q.isActive());
		qr.save(q);
	}
	
	
}
