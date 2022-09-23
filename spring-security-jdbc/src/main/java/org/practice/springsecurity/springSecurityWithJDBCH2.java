package org.practice.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
public class springSecurityWithJDBCH2 {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sql/users.sql")
                .build();
    }

    @Bean
    public UserDetailsManager users() {

        UserDetails user = User.builder()
                .username("user")
                .password("***")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("@@@")
                .roles("USER", "ADMIN")
                .build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource());
        users.createUser(user);
        users.createUser(admin);

        return users;
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

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
