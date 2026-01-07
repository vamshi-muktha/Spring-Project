package com.vamshi.securecard.securecard.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
	private int orderId;
	private int amount;
	private String status;
	private int uid;
	private int cardId;

}
