package com.vamshi.securecard.securecard.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.engine.internal.Cascade;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardNumber")
public class Card {
	
	
	
    @Id
    private String cardNumber;
    private String cardName;
    private String cardExpiry;
    private String cardCVV;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
