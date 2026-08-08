package com.jjrdizon.sample.addressbook.infrastructure.adapter.out;

import com.jjrdizon.sample.addressbook.domain.model.Contact;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ContactDomainMapper {

    @Mapping(target = "firstName", source = "contact.name.first")
    @Mapping(target = "lastName", source = "contact.name.last")
    @Mapping(target = "suffix", source = "contact.name.suffix")
    ContactEntity map(Contact contact);

    @InheritInverseConfiguration
    @Mapping(target = "contactNumberList", ignore = true)
    Contact map(ContactEntity entity);

}
