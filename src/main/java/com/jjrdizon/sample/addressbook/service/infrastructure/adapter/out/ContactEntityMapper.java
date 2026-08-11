package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ContactEntityMapper {

    @Mapping(target = "firstName", source = "contact.name.first")
    @Mapping(target = "lastName", source = "contact.name.last")
    @Mapping(target = "suffix", source = "contact.name.suffix")
    ContactEntity map(Contact contact);

    @InheritInverseConfiguration
    Contact map(ContactEntity entity);

}
