package com.jjrdizon.sample.addressbook.application.usecase;

import com.jjrdizon.sample.addressbook.domain.model.Contact;

public interface CreateContactUseCase {

    Contact createContact(Contact contact);
}
