package com.tcs.securecard.securecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
public class SecureCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureCardApplication.class, args);
        System.out.println("SecureCardApplication started");
    }
}
