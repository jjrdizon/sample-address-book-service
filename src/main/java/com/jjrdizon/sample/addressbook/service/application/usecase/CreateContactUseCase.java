package com.jjrdizon.sample.addressbook.service.application.usecase;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;

public interface CreateContactUseCase {

    Contact createContact(Contact contact);
}
