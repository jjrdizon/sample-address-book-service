package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.model.ContactNumber;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactNumberDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ContactEntityMapper {

    @Mapping(target = "firstName", source = "contact.name.first")
    @Mapping(target = "lastName", source = "contact.name.last")
    @Mapping(target = "suffix", source = "contact.name.suffix")
    ContactEntity map(Contact contact);

    @AfterMapping
    default void populateParentContact(@MappingTarget ContactEntity mappedEntity) {
        mappedEntity.getContactNumbers().stream().forEach(contactNumber -> { contactNumber.setParentContact(mappedEntity); });
    }

    @InheritInverseConfiguration
    Contact map(ContactEntity entity);

    @Mapping(target = "parentContact", ignore = true)
    ContactNumberEntity map(ContactNumber contactNumber);



}
