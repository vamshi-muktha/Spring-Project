package com.tcs.securecard.securecard.config;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tcs.securecard.securecard.models.User;
import com.tcs.securecard.securecard.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
    UserService userService;



    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = token.getPrincipal();
        System.out.println(oauthUser);
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String googleSub = oauthUser.getAttribute("sub"); // Google unique ID

        System.out.println("coming to google");
        if (!userService.existsByEmail(email)) {
        	System.out.println("coming to google and no email");
            User user = new User();
            user.setUsername(googleSub);
            user.setEmail(email);
            user.setName(name);
            user.setPassword("Default Password");
            user.setOriginalPassword("GOOGLE");
            user.setRole("USER");
            user.setAddress("Default");
            user.setMobileNumber("9090909090");
            user.setDob("01/01/2000");
            User x = userService.create(user);
            System.out.println("Useeee" + x.toString());
        }

        response.sendRedirect("http://spring-project-frontend.vercel.app/home");
    }
}

