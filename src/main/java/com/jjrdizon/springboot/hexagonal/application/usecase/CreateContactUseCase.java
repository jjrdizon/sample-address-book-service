package com.jjrdizon.springboot.hexagonal.application.usecase;

import com.jjrdizon.springboot.hexagonal.domain.model.Contact;

public interface CreateContactUseCase {

    void createContact(Contact contact);
}
