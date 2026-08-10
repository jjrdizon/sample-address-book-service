package com.jjrdizon.sample.addressbook.service.application.usecase;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;

import java.util.List;

public interface GetContactsUseCase {

    List<Contact> getContacts();
}
