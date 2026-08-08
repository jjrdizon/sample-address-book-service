package com.jjrdizon.sample.addressbook.infrastructure.configuration;

import com.jjrdizon.sample.addressbook.application.port.CreateContactUseCaseImpl;
import com.jjrdizon.sample.addressbook.domain.port.ContactRepository;
import com.jjrdizon.sample.addressbook.infrastructure.adapter.out.ContactDomainMapper;
import com.jjrdizon.sample.addressbook.infrastructure.adapter.out.ContactDomainRepositoryAdapter;
import com.jjrdizon.sample.addressbook.infrastructure.adapter.out.ContactJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateContactConfiguration {

    @Bean
    public ContactRepository contactRepository(ContactJpaRepository contactJpaRepository, ContactDomainMapper contractDomainMapper) {
        return new ContactDomainRepositoryAdapter(contactJpaRepository, contractDomainMapper);
    }

    @Bean
    public CreateContactUseCaseImpl createContactUseCase(ContactRepository contactRepository) {
        return new CreateContactUseCaseImpl(contactRepository);
    }
}
