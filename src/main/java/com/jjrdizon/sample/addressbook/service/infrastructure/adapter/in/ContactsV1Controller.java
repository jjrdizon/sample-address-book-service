package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in;

import com.jjrdizon.sample.addressbook.service.application.port.CreateContactUseCase;
import com.jjrdizon.sample.addressbook.service.application.port.GetContactsUseCase;
import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.model.ContactNumber;
import com.jjrdizon.sample.addressbook.service.domain.model.Name;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.api.ContactsV1Api;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactNumberDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ContactsV1Controller implements ContactsV1Api {

    private final CreateContactUseCase createContactUseCase;

    private final GetContactsUseCase getContactsUseCase;

    private final ContactDtoMapper contactDtoMapper;

    public ContactsV1Controller(CreateContactUseCase createContactUseCase, GetContactsUseCase getContactsUseCase, ContactDtoMapper contactDtoMapper){
        this.createContactUseCase = createContactUseCase;
        this.getContactsUseCase = getContactsUseCase;
        this.contactDtoMapper = contactDtoMapper;
    }

    @Override
    public ResponseEntity<CreateContactResponseDto> createContact(CreateContactRequestDto createContact) {

        var nameDto = createContact.getName();
        var contactNumberList = createContact.getContactNumberList().stream().map(this::mapContactNumber).toList();

        var contact = createContactUseCase.createContact(new Contact(null, new Name(nameDto.getFirst(), nameDto.getLast(), nameDto.getSuffix()), contactNumberList));

        CreateContactResponseDto contactResponseDto = new CreateContactResponseDto().uuid(contact.uuid());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactResponseDto);
    }

    @Override
    public ResponseEntity<List<ContactDto>> getContacts() {
        List<Contact> contacts = getContactsUseCase.getContacts();

        var contactDtos = contacts.stream().map(contactDtoMapper::map).toList();

        return ResponseEntity.of(Optional.of(contactDtos));
    }

    private ContactNumber mapContactNumber(ContactNumberDto contactNumberDto) {
        return new ContactNumber(contactNumberDto.getCountryCode(), contactNumberDto.getAreaCode(), contactNumberDto.getSubscriberNumber());
    }
}
