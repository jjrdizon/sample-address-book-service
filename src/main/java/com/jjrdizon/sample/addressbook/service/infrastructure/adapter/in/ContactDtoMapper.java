package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.model.ContactNumber;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactNumberDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactDtoMapper {

    @Mapping(target = "uuid", ignore = true)
    Contact map(CreateContactRequestDto contactRequestDto);

    CreateContactResponseDto mapToCreateContactResponseDto(Contact contact);

    ContactDto map(Contact contact);

    @Mapping(target = "uuid", ignore = true)
    ContactNumber map(ContactNumberDto contactNumberDto);

    ContactNumberDto map(ContactNumber contactNumber);
}
