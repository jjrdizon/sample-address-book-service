package com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in;

import com.jjrdizon.springboot.hexagonal.application.usecase.CreateContactUseCase;
import com.jjrdizon.springboot.hexagonal.domain.model.Contact;
import com.jjrdizon.springboot.hexagonal.domain.model.ContactNumber;
import com.jjrdizon.springboot.hexagonal.domain.model.Name;
import com.jjrdizon.springboot.hexagonal.domain.port.ContactRepository;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.api.ContactsV1Api;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.ContactNumberDto;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.CreateContactRequestDto;
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
    public ResponseEntity<Void> createContact(CreateContactRequestDto createContact) {

        var nameDto = createContact.getName();
        var contactNumberList = createContact.getContactNumberList().stream().map(this::mapContactNumber).toList();
        createContactUseCase.createContact(new Contact(new Name(nameDto.getFirst(), nameDto.getLast(), nameDto.getSuffix()), contactNumberList));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private ContactNumber mapContactNumber(ContactNumberDto contactNumberDto) {
        return new ContactNumber(contactNumberDto.getCountryCode(), contactNumberDto.getAreaCode(), contactNumberDto.getSubscriberNumber());
    }
}
