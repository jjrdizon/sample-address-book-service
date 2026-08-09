package com.jjrdizon.sample.addressbook.service.infrastructure.configuration;

import com.jjrdizon.sample.addressbook.service.application.port.CreateContactUseCaseImpl;
import com.jjrdizon.sample.addressbook.service.domain.port.ContactRepository;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactDomainMapper;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactDomainRepositoryAdapter;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactJpaRepository;
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
