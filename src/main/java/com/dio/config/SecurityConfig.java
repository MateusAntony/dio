package com.dio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF (para facilitar testes com Postman, por exemplo)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) // Todas as requisições precisam de autenticação
                .httpBasic(httpBasic -> {}); // Mantém autenticação básica

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("kaique")
                .password("{noop}123456") // `{noop}` indica que a senha não está criptografada
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
