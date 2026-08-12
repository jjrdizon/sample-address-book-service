package com.jjrdizon.sample.addressbook.service.domain.model;


import java.util.List;
import java.util.UUID;

public record Contact(UUID uuid, Name name, List<ContactNumber> contactNumbers) {
}
