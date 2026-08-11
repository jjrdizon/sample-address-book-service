package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.model.ContactNumber;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactNumberDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactDtoMapper {

    ContactDto map(Contact contact);

    @Mapping(target = "uuid", ignore = true)
    Contact map(CreateContactRequestDto contactRequestDto);

    ContactNumber map(ContactNumberDto contactNumberDto);
}
