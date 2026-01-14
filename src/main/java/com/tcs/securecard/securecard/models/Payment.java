package com.tcs.securecard.securecard.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

	@NotNull
	@Min(value = 1, message = "Order ID must be a positive number")
	private int orderId;

	@NotNull
	@Min(value = 1, message = "Amount must be greater than 0")
	private int amount;

	@NotBlank
	private String status;

	private int uid;
	private int cardId;

}
