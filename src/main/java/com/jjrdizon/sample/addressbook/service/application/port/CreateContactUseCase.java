package com.jjrdizon.sample.addressbook.service.application.port;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;

public interface CreateContactUseCase {

    Contact createContact(Contact contact);
}
