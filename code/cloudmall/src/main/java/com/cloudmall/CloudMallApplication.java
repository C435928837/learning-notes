package com.cloudmall;

import com.cloudmall.demo.spring.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CloudMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudMallApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ProductService productService) {
        return args -> System.out.println(productService.getProduct("P001"));
    }
}
