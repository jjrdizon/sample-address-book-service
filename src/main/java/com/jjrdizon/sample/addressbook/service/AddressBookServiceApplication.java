package com.jjrdizon.sample.addressbook.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AddressBookServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressBookServiceApplication.class);
    }
}
