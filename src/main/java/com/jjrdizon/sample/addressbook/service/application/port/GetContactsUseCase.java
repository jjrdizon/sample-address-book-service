package com.jjrdizon.sample.addressbook.service.application.port;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;

import java.util.List;

public interface GetContactsUseCase {

    List<Contact> getContacts();
}
