package com.vamshi.securecard.securecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecureCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureCardApplication.class, args);
        System.out.println("SecureCardApplication started");
    }
}
