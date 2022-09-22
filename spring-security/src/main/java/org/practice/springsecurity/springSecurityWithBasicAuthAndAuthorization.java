package org.practice.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Unable it when to use spring security with predefined users for web request
 */
@EnableWebSecurity
public class springSecurityWithBasicAuthAndAuthorization {

    /**
     * Implementing authentication in spring boot
     */
    @Bean
    public UserDetailsService users() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user")
                .password("password")
                .roles("USER").build();
        UserDetails admin = users
                .username("admin")
                .password("password")
                .roles("ADMIN", "USER").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * Implementing authorization in spring boot
     */
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            try {
                authorize.mvcMatchers("/admin")
                        .hasRole("ADMIN")
                        .mvcMatchers("/user")
                        .hasRole("USER")
                        .mvcMatchers("/").permitAll().and().formLogin();
            } catch (Exception e) {
                System.out.println("Exception occurred");
            }
        });
        return http.build();
    }
}
