package org.practice.springsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello in basic authentication & Authorization principle";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello User in basic authentication & Authorization principle";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin in basic authentication & Authorization principle";
    }
}
