package com.jjrdizon.sample.addressbook.service.domain.port;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;

import java.util.List;

public interface ContactRepository {

    Contact save(Contact contact);

    List<Contact> findAll();
}
