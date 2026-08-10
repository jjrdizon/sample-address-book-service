package com.jjrdizon.sample.addressbook.service.application.usecase;

import com.jjrdizon.sample.addressbook.service.application.port.CreateContactUseCase;
import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.port.ContactRepository;

public class CreateContactUseCaseImpl implements CreateContactUseCase {

    private final ContactRepository contactRepository;

    public CreateContactUseCaseImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }
}
