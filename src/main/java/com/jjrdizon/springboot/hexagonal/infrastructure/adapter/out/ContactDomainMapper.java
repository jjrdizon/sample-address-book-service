package com.jjrdizon.springboot.hexagonal.infrastructure.adapter.out;

import com.jjrdizon.springboot.hexagonal.domain.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactDomainMapper {

    @Mapping(target = "firstName", source = "contact.name.first")
    @Mapping(target = "lastName", source = "contact.name.last")
    @Mapping(target = "suffix", source = "contact.name.suffix")
    ContactEntity map(Contact contact);

}
