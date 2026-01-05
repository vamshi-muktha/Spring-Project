package com.vamshi.securecard.securecard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private String address;
    private String mobileNumber;
    private String dob;
}
