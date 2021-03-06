package com.be;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class Run {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Run.class);
        application.run(args);
    }
}
