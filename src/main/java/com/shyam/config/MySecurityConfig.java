package com.shyam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class MySecurityConfig {

    private String[] WHITELIST_URLS = {
        "auth/**",
        "css/**",
        "fonts/**",
        "img/**",
        "lib/**",
        "js/**",
        "webfonts/**",
        "test",
        "contact",
        "/forgot-password", 
        "/process-set-password",
        "/sign-in", 
        "/customer-register", 
        "/register-vender", 
        "/activate-user/**", 
        "/set-password/**"
    };

    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService myUserDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    AuthenticationProvider myAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(getEncoder());
        authProvider.setUserDetailsService(myUserDetailsService());

        return authProvider;
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(
            csrf -> csrf.disable()
        );

        httpSecurity.authorizeHttpRequests(
            authorize -> authorize
                            .requestMatchers(WHITELIST_URLS).permitAll()                 
                            .requestMatchers("/vender/**").hasAuthority("VENDER")
                            .requestMatchers("/customer/**").hasAuthority("CUSTOMER")
                            .anyRequest().authenticated()
        );

        httpSecurity.formLogin(
            form -> form
                        .loginPage("/sign-in")
                        .loginProcessingUrl("/process-login")
                        .defaultSuccessUrl("/")
                        .permitAll()
        );

        httpSecurity.rememberMe(
            remember -> remember
                        .key("this-is-secret")
                        .tokenValiditySeconds(60 * 60 * 24)
                        .rememberMeParameter("remember-me") // one day
        );

        return httpSecurity.build();
    }
}
