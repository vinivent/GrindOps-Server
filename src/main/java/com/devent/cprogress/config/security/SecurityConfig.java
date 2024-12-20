package com.devent.cprogress.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users", "/api/achievements", "/api/categories",
                                "/api/camouflages", "/api/challenges", "/api/user-progress")
                        .permitAll() // Torna essas rotas públicas
                        .anyRequest().authenticated() // Outras rotas exigem autenticação
                )
                .httpBasic(Customizer.withDefaults()); // Habilita autenticação HTTP básica

        return http.build();
    }
}
