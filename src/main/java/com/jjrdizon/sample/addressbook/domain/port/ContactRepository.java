package com.jjrdizon.sample.addressbook.domain.port;

import com.jjrdizon.sample.addressbook.domain.model.Contact;

public interface ContactRepository {

    Contact save(Contact contact);
}
