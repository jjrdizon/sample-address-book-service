package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in;

import com.jjrdizon.sample.addressbook.service.application.usecase.CreateContactUseCase;
import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.model.ContactNumber;
import com.jjrdizon.sample.addressbook.service.domain.model.Name;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.api.ContactsV1Api;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactNumberDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsV1Controller implements ContactsV1Api {

    private final CreateContactUseCase createContactUseCase;

    public ContactsV1Controller(CreateContactUseCase createContactUseCase){
        this.createContactUseCase = createContactUseCase;
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

    private ContactNumber mapContactNumber(ContactNumberDto contactNumberDto) {
        return new ContactNumber(contactNumberDto.getCountryCode(), contactNumberDto.getAreaCode(), contactNumberDto.getSubscriberNumber());
    }
}
