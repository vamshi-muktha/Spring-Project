package com.tcs.securecard.securecard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
	@NotBlank(message = "Card type is required")
	private String type;

	@NotBlank(message = "PAN is required")
	private String PAN;

	@NotBlank(message = "Employment status is required")
	private String empStatus;

	@NotBlank(message = "Monthly income is required")
	private String monthlyIncome;

	@NotBlank(message = "Card type selection is required")
	private String cardType;
}
