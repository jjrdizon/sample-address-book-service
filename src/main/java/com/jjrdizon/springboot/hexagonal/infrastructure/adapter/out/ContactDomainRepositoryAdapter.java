package com.jjrdizon.springboot.hexagonal.infrastructure.adapter.out;

import com.jjrdizon.springboot.hexagonal.domain.model.Contact;
import com.jjrdizon.springboot.hexagonal.domain.port.ContactRepository;

import static java.util.Objects.isNull;

public class ContactDomainRepositoryAdapter implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;

    private final ContactDomainMapper mapper;

    public ContactDomainRepositoryAdapter(ContactJpaRepository contactJpaRepository, ContactDomainMapper mapper) {
        this.contactJpaRepository = contactJpaRepository;
        this.mapper = mapper;
    }


    @Override
    public Contact save(Contact contact) {

        if (isNull(contact)) {
           throw new IllegalArgumentException();
        }

        var entity = mapper.map(contact);

        ContactEntity saved = contactJpaRepository.save(entity);

        return mapper.map(saved);
    }
}
