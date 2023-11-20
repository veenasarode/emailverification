package com.springemailverification.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserRegistrationSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      return  http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
              authorize -> authorize.requestMatchers("/register/**").permitAll())
                .authorizeHttpRequests(authorize ->authorize.requestMatchers("/users/**").hasAnyAuthority("USER","ADMIN"))
              .build();
    }
}


