package com.example.vuln_dashboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/dashboard", "/css/**", "/js/**").permitAll()
                .requestMatchers("/api/vulnerabilities/search").permitAll()
                .requestMatchers("/api/vulnerabilities/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/dashboard")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        String adminPass = System.getenv("ADMIN_PASSWORD") != null 
                       ? System.getenv("ADMIN_PASSWORD") : "dev-only-pass";

        String analystPass = System.getenv("ANALYST_PASSWORD") != null 
                       ? System.getenv("ANALYST_PASSWORD") : "password";    

        UserDetails admin = User.builder()
            .username("admin")
            .password(encoder.encode(adminPass))
            .roles("ADMIN")
            .build();

        UserDetails analyst = User.builder()
            .username("analyst")
            .password(encoder.encode(analystPass))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, analyst);
    }
}