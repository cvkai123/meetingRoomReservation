package com.meetingroomreservation.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/users").hasRole("ROLE_ADMIN")
	            .anyRequest().permitAll()
	            )
				.formLogin(form -> form
	            .loginPage("/login")
	            .loginProcessingUrl("/login")
	            .successHandler(successHandler())
	            .permitAll())
				.logout(logout -> logout
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .permitAll());
		return http.build();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
	    return new SimpleUrlAuthenticationSuccessHandler() {
	        @Override
	        protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	        	
	        	return "/mainScreen";
	        }
	    };
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
