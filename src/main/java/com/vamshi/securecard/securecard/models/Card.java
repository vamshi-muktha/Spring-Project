package com.vamshi.securecard.securecard.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardNumber")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cid;

	@NotBlank
	@Pattern(regexp = "\\d{13,19}", message = "Invalid card number")
	private String cardNumber;

	@NotBlank
	@Size(max = 50)
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Card name must contain only letters")
	private String cardName;

	@NotBlank
	@Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Expiry must be in MM/YY format")
	private String cardExpiry;

	@NotBlank
	@Pattern(regexp = "\\d{3,4}", message = "Invalid CVV")
	private String cardCVV;

	@NotBlank
	private String type;

	@NotBlank
	@Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format")
	private String PAN;

	@NotBlank
	private String empStatus; // consider enum

	@NotBlank
	@Pattern(regexp = "\\d+", message = "Monthly income must be numeric")
	private String monthlyIncome;

	@NotBlank
	private String cardType;

	private boolean isActive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
