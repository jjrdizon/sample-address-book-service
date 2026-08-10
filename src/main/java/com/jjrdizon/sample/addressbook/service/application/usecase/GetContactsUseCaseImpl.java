package com.jjrdizon.sample.addressbook.service.application.usecase;

import com.jjrdizon.sample.addressbook.service.application.port.GetContactsUseCase;
import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.port.ContactRepository;

import java.util.List;

public class GetContactsUseCaseImpl implements GetContactsUseCase {

    private final ContactRepository contactRepository;

    public GetContactsUseCaseImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContacts(){
        return contactRepository.findAll();
    }
}
