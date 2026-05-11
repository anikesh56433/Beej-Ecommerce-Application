package com.beej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeejApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeejApplication.class, args);
        System.out.println("Beej Application started successfully");
    }

}
