package com.jjrdizon.springboot.hexagonal.domain.model;


import java.util.List;

public record Contact(Name name, List<ContactNumber> contactNumberList) {
}
