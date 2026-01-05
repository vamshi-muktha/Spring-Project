package com.vamshi.securecard.securecard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
	private String type;
    private String PAN;
    private String empStatus;
    private String monthlyIncome;
    private String cardType;
}
