package com.tcs.securecard.securecard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.securecard.securecard.models.Payment;

import jakarta.transaction.Transactional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer>{


	@Query("SELECT p FROM Payment p WHERE p.uid = :uid AND p.status = :status")
	List<Payment> findPayments(@Param("uid") int uid,
	                           @Param("status") String status);

	@Modifying
	@Transactional
	@Query("UPDATE Payment p SET p.status = :status, p.cardId = :cid WHERE p.pid = :pid")
	int changePayment(@Param("pid") int pid,
	                  @Param("status") String status, int cid);
	
	@Modifying
	@Transactional
	@Query("UPDATE Payment p SET p.status = :status WHERE p.pid = :pid")
	int rejectPayment(@Param("pid") int pid,
	                  @Param("status") String status);

	List<Payment> findByCardId(int cid);


}
