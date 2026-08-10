package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.port.ContactRepository;

import java.util.List;

import static java.util.Objects.isNull;

public class ContactDomainRepositoryAdapter implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;

    private final ContactEntityMapper mapper;

    public ContactDomainRepositoryAdapter(ContactJpaRepository contactJpaRepository, ContactEntityMapper mapper) {
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

    @Override
    public List<Contact> findAll() {
        var all = contactJpaRepository.findAll();

        return all.stream().map(mapper::map).toList();
    }
}
