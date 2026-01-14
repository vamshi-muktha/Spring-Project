package com.tcs.securecard.securecard.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tcs.securecard.securecard.dto.MailRequest;

@FeignClient(name = "mail-service", url = "${mail.service.url}")
public interface MailFeignClient {

    @PostMapping("/mail/send")
    void sendMail(@RequestBody MailRequest request);
}
