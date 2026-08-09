package com.jjrdizon.sample.addressbook.service.domain.port;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;

public interface ContactRepository {

    Contact save(Contact contact);
}
