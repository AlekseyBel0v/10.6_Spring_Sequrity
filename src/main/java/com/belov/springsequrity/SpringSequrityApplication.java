package com.belov.springsequrity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringSequrityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSequrityApplication.class, args);
    }

    @GetMapping("/hi")
    public String hi() {
        return "Hello, authenticated user";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, authenticated user";
    }

    @GetMapping("/read")
    public String read() {
        return "Read";
    }

    @GetMapping("/write")
    public String write() {
        return "Write";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello admin";
    }

    // UsernamePasswordAuthenticationFilter - Можно проследить за процессом
    // аутентификации, если запустить приложение к данному модулю в дебагере
    // и поставить точку астонова в данном библиотечном классе в методе
    // attemptAuthentication(HttpSerVletRequest...)
}
