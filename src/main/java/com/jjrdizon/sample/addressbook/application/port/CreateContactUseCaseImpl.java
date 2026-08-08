package com.jjrdizon.sample.addressbook.application.port;

import com.jjrdizon.sample.addressbook.application.usecase.CreateContactUseCase;
import com.jjrdizon.sample.addressbook.domain.model.Contact;
import com.jjrdizon.sample.addressbook.domain.port.ContactRepository;

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
