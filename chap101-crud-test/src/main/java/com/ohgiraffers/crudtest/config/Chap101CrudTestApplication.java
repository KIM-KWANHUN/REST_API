package com.ohgiraffers.crudtest.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.RepositoryDefinition;

@SpringBootApplication
@EntityScan(basePackages = "com.ohgiraffers.crudtest")
@EnableJpaRepositories(basePackages = "com.ohgiraffers.crudtest")
public class Chap101CrudTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap101CrudTestApplication.class, args);
    }

}
