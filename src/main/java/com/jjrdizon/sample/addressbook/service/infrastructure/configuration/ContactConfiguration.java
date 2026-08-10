package com.jjrdizon.sample.addressbook.service.infrastructure.configuration;

import com.jjrdizon.sample.addressbook.service.application.port.CreateContactUseCaseImpl;
import com.jjrdizon.sample.addressbook.service.application.usecase.CreateContactUseCase;
import com.jjrdizon.sample.addressbook.service.application.usecase.GetContactsUseCase;
import com.jjrdizon.sample.addressbook.service.domain.port.ContactRepository;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactEntityMapper;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactDomainRepositoryAdapter;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class ContactConfiguration {

    @Bean
    public ContactRepository contactRepository(ContactJpaRepository contactJpaRepository, ContactEntityMapper contractDomainMapper) {
        return new ContactDomainRepositoryAdapter(contactJpaRepository, contractDomainMapper);
    }

    @Bean
    public CreateContactUseCase createContactUseCase(ContactRepository contactRepository) {
        return new CreateContactUseCaseImpl(contactRepository);
    }

    @Bean
    public GetContactsUseCase getContactsUseCase() {
        return Collections::emptyList;
    }
}
