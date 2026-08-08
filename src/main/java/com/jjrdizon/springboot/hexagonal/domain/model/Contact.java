package com.jjrdizon.springboot.hexagonal.domain.model;


import java.util.List;
import java.util.UUID;

public record Contact(UUID uuid, Name name, List<ContactNumber> contactNumberList) {
}
