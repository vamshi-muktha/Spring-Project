package com.vamshi.securecard.securecard.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
