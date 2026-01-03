package com.vamshi.securecard.securecard.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    private int id;
    private String name;
    private String username;
    private String password;
    private String address;
    private String mobileNumber;
    @OneToMany(mappedBy = "user")
    private List<Card> cards;
    private String role;


}
