package com.expexchangeservice.controller;

import com.expexchangeservice.controller.config.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Import({SecurityConfiguration.class})
@EntityScan("com.expexchangeservice.model.entities")
@SpringBootApplication(scanBasePackages = {"com.expexchangeservice.repository",
        "com.expexchangeservice.service",
        "com.expexchangeservice.controller",
        "com.expexchangeservice.utils"})
@EnableAspectJAutoProxy
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
