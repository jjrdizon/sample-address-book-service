package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactDtoMapper {

    ContactDto map(Contact contact);

    Contact map(ContactDto contactDto);
}
