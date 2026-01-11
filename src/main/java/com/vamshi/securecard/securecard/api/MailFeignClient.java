package com.vamshi.securecard.securecard.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vamshi.securecard.securecard.dto.MailRequest;

@FeignClient(name = "MAILSERVICE")
public interface MailFeignClient {

    @PostMapping("/mail/send")
    void sendMail(@RequestBody MailRequest request);
}
