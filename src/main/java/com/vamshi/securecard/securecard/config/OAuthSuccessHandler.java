package com.vamshi.securecard.securecard.config;


import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.vamshi.securecard.securecard.models.User;
import com.vamshi.securecard.securecard.service.UserService;

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
        System.out.println("coming to google");
        if (!userService.existsByEmail(email)) {
        	System.out.println("coming to google and no email");
            User user = new User();
            user.setUsername(email);
            user.setEmail(email);
            user.setName(name);
            user.setPassword(UUID.randomUUID().toString());
            user.setOriginalPassword("GOOGLE");
            user.setRole("USER");

            userService.create(user);
        }

        response.sendRedirect("/home");
    }
}

