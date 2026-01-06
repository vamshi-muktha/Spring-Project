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
import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardNumber")
public class Card {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;
    private String cardNumber;
    private String cardName;
    private String cardExpiry;
    private String cardCVV;
    private String type;
    private String PAN;
    private String empStatus;
    private String monthlyIncome;
    private String cardType;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
