package com.jjrdizon.springboot.hexagonal.infrastructure.configuration;

import com.jjrdizon.springboot.hexagonal.application.port.CreateContactUseCaseImpl;
import com.jjrdizon.springboot.hexagonal.domain.port.ContactRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateContactConfiguration {



    @Bean
    public ContactRepository contactRepository() {
        return contact -> {
            //this is a dummy lambda for now
        };
    }

    @Bean
    public CreateContactUseCaseImpl createContactUseCase(ContactRepository contactRepository) {
        return new CreateContactUseCaseImpl(contactRepository);
    }
}
