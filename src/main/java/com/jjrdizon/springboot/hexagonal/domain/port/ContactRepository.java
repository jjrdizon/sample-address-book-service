package com.jjrdizon.springboot.hexagonal.domain.port;

import com.jjrdizon.springboot.hexagonal.domain.model.Contact;

public interface ContactRepository {

    void save(Contact contact);
}
