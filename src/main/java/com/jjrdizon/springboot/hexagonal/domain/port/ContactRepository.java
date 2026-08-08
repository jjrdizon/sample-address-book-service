package com.jjrdizon.springboot.hexagonal.domain.port;

import com.jjrdizon.springboot.hexagonal.domain.model.Contact;

public interface ContactRepository {

    Contact save(Contact contact);
}
