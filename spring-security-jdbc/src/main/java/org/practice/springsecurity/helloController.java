package org.practice.springsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello in JDBC authentication & Authorization principle";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello User in JDBC authentication & Authorization principle";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin in JDBC authentication & Authorization principle";
    }
}
