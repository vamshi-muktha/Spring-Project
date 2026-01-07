package com.vamshi.securecard.securecard.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
//@EnableWebSecurity
public class Config {

    @Autowired
    private UserDetailsService uds;




    @Autowired
	private OAuthSuccessHandler oAuthSuccessHandler;




    @Bean
    public DaoAuthenticationProvider authicate() {
        DaoAuthenticationProvider dp = new DaoAuthenticationProvider(uds);
        dp.setPasswordEncoder(new BCryptPasswordEncoder());
        return dp;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        		.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/users/register",
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/WEB-INF/**",
                                "/register",
                                "/.well-known/**"
                        ).permitAll()
//                        .requestMatchers("/cards").authenticated()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )

                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .successHandler(oAuthSuccessHandler)
                )

                // 🚪 Logout
                .logout(logout -> logout
                		 .logoutUrl("/logout")
                	        .logoutSuccessUrl("/login")
                	        .invalidateHttpSession(true)
                	        .clearAuthentication(true)
                	        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
