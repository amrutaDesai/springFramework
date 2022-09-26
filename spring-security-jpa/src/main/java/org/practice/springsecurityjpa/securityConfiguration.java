package org.practice.springsecurityjpa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
public class securityConfiguration {

    @Autowired
    public MyUserDetailsService MyUserDetailsService;

    @Autowired
    public DataSource dataSource;

    /** TODO
     *
     * 1. Access the user name from url
     * 2. Connect the correct DB
     * 3. Verify with current set up mysql DB gets connected or not
     * 4. Get the user details provided on the front end retrieve it from the DB
     *
     * **/
    @Bean
    public UserDetailsManager users() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.createUser(MyUserDetailsService.loadUserByUsername("userName"));
        return manager;
    }

    @Bean
    SecurityFilterChain webAuth(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            try {
                authorize
                        .mvcMatchers("/admin").hasRole("ADMIN")
                        .mvcMatchers("/user").hasRole("USER")
                        .mvcMatchers("/").permitAll()
                        .and().formLogin();
            } catch (Exception e) {
                System.out.println("Exception occurred");
            }
        });
        return http.build();
    }
}