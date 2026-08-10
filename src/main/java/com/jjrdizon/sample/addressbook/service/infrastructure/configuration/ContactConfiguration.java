package com.jjrdizon.sample.addressbook.service.infrastructure.configuration;

import com.jjrdizon.sample.addressbook.service.application.usecase.CreateContactUseCaseImpl;
import com.jjrdizon.sample.addressbook.service.application.usecase.GetContactsUseCaseImpl;
import com.jjrdizon.sample.addressbook.service.application.port.CreateContactUseCase;
import com.jjrdizon.sample.addressbook.service.application.port.GetContactsUseCase;
import com.jjrdizon.sample.addressbook.service.domain.port.ContactRepository;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactEntityMapper;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactDomainRepositoryAdapter;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public GetContactsUseCase getContactsUseCase(ContactRepository contactRepository) {
        return new GetContactsUseCaseImpl(contactRepository);
    }
}
