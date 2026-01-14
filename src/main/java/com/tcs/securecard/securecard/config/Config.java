//package com.vamshi.securecard.securecard.config;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.ForwardedHeaderFilter;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//@Configuration
////@EnableWebSecurity
//public class Config {
//
//	@Autowired
//	private UserDetailsService uds;
//
//	@Autowired
//	private OAuthSuccessHandler oAuthSuccessHandler;
//
//	@Bean
//	public ForwardedHeaderFilter forwardedHeaderFilter() {
//		return new ForwardedHeaderFilter();
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authicate() {
//		DaoAuthenticationProvider dp = new DaoAuthenticationProvider(uds);
//		dp.setPasswordEncoder(new BCryptPasswordEncoder());
//		return dp;
//	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//	    http.cors(Customizer.withDefaults())
//	        .csrf(csrf -> csrf.disable())
//
//	        .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
//	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	        }))
//
//	        .authorizeHttpRequests(auth -> auth
//	            .requestMatchers(
//	                "/securecard/login",
//	                "/securecard/register",
//	                "/securecard/users/**",
//	                "/securecard/otp/**",
//	                "/oauth2/**",
//	                "/login/oauth2/**"
//	            ).permitAll()
//	            .anyRequest().authenticated()
//	        )
//
//	        .formLogin(form -> form
//	        	    .loginPage("/securecard/login")
//	        	    .loginProcessingUrl("/securecard/login")
//	        	    .successHandler((request, response, authentication) -> {
//	        	        response.setStatus(HttpServletResponse.SC_OK);
//	        	    })
//	        	    .failureHandler((request, response, exception) -> {
//	        	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	        	    })
//	        	)
//
//
//	        .oauth2Login(oauth -> oauth
//	            .loginPage("/securecard/login")
//	            .successHandler(oAuthSuccessHandler)
//	        )
//
//	        .logout(logout -> logout
//	            .logoutUrl("/securecard/logout")
//	            .logoutSuccessUrl("/securecard/login")
//	            .invalidateHttpSession(true)
//	            .clearAuthentication(true)
//	            .deleteCookies("JSESSIONID")
//	        );
//
//	    return http.build();
//	}
//	
//	
////	@Bean
////	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////
////	    http
////	        .csrf(csrf -> csrf.disable())
////	        .cors(Customizer.withDefaults())
////
////	        .authorizeHttpRequests(auth -> auth
////	            .requestMatchers(
////	                "/securecard/login",
////	                "/securecard/register",
////	                "/securecard/users/**",
////	                "/securecard/otp/**",
////	                "/oauth2/**",
////	                "/login/oauth2/**"
////	            ).permitAll()
////	            .anyRequest().authenticated()
////	        )
////
////	        .formLogin(form -> form
////	            .loginProcessingUrl("/securecard/login")
////	            .successHandler((req, res, auth) -> res.setStatus(200))
////	            .failureHandler((req, res, ex) -> res.setStatus(401))
////	        )
////
////	        .oauth2Login(oauth -> oauth
////	            .loginPage("/securecard/login")
////	            .successHandler(oAuthSuccessHandler)
////	        )
////
////	        .logout(logout -> logout
////	            .logoutUrl("/securecard/logout")
////	            .logoutSuccessUrl("/securecard/login")
////	            .invalidateHttpSession(true)
////	            .deleteCookies("JSESSIONID")
////	        );
////
////	    return http.build();
////	}
//
//
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
//		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		config.setAllowedHeaders(List.of("*"));
//		config.setAllowCredentials(true);
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//
//		return source;
//	}
//
//}


package com.tcs.securecard.securecard.config;

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
import org.springframework.web.filter.ForwardedHeaderFilter;

import jakarta.servlet.http.HttpServletResponse;

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

		http.cors(Customizer.withDefaults())

				.csrf(csrf -> csrf.disable())

				.exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				})).authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/users/register", "/users/**", "/otp/**", "/oauth2/**",
						"/login/oauth2/**", "/WEB-INF/**", "/register", "/.well-known/**").permitAll()
//                        .requestMatchers("/cards").authenticated()
						.anyRequest().authenticated())

//				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/home")
//						.permitAll())
				.formLogin(form -> form
					    .loginProcessingUrl("/login")
					    .successHandler((req, res, auth) -> res.setStatus(200))
					    .failureHandler((req, res, ex) -> res.setStatus(401))
					)


				.oauth2Login(oauth -> oauth.loginPage("/login").successHandler(oAuthSuccessHandler))

				// 🚪 Logout
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true)
						.clearAuthentication(true).deleteCookies("JSESSIONID"));

		return http.build();
	}
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//	    http
//	        .cors(Customizer.withDefaults())
//	        .csrf(csrf -> csrf.disable())
//
//	        .exceptionHandling(ex -> ex
//	            .authenticationEntryPoint((request, response, authException) -> {
//	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	            })
//	        )
//
//	        .authorizeHttpRequests(auth -> auth
//	            .requestMatchers(
//	                "/securecard/login",
//	                "/securecard/register",
//	                "/securecard/users/**",
//	                "/securecard/otp/**",
//	                "/oauth2/**",
//	                "/login/oauth2/**"
//	            ).permitAll()
//	            .anyRequest().authenticated()
//	        )
//
//	        // SPA + Gateway login
//	        .formLogin(form -> form
//	            .loginProcessingUrl("/securecard/login")
//	            .successHandler((req, res, auth) -> res.setStatus(200))   // REST style
//	            .failureHandler((req, res, ex) -> res.setStatus(401))
//	        )
//
//	        // Google login
//	        .oauth2Login(oauth -> oauth
//	            .loginPage("/securecard/login")
//	            .successHandler(oAuthSuccessHandler)
//	        )
//
//	        // Logout
//	        .logout(logout -> logout
//	            .logoutUrl("/securecard/logout")
//	            .logoutSuccessUrl("/securecard/login")
//	            .invalidateHttpSession(true)
//	            .clearAuthentication(true)
//	            .deleteCookies("JSESSIONID")
//	        );
//
//	    return http.build();
//	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("https://spring-project-frontend.vercel.app", "http://localhost:3000", "http://localhost:5173"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
	
//	@Bean
//	public ForwardedHeaderFilter forwardedHeaderFilter() {
//	    return new ForwardedHeaderFilter();
//	}


}
