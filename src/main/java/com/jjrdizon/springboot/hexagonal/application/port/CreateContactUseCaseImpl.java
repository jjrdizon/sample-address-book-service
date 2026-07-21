package com.jjrdizon.springboot.hexagonal.application.port;

import com.jjrdizon.springboot.hexagonal.application.usecase.CreateContactUseCase;
import com.jjrdizon.springboot.hexagonal.domain.model.Contact;
import com.jjrdizon.springboot.hexagonal.domain.port.ContactRepository;

public class CreateContactUseCaseImpl implements CreateContactUseCase {

    private final ContactRepository contactRepository;

    public CreateContactUseCaseImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }
}
