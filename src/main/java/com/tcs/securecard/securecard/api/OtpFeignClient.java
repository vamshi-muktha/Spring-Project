package com.tcs.securecard.securecard.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OTPService", url = "${otp.service.url}")
public interface OtpFeignClient {
	
	@PostMapping("/otp/send")
	public String sendOtp(@RequestParam String email);
	
	@PostMapping("/otp/verify")
	public boolean verifyOtp(@RequestParam String email, @RequestParam String otp);
}
