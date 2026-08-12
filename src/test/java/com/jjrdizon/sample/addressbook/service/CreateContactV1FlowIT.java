package com.jjrdizon.sample.addressbook.service;

import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactNumberDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactResponseDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.NameDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
class CreateContactV1FlowIT {

    private RestTemplate restTemplate;

    @Autowired
    private ContactJpaRepository jpaRepository;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    void createContact_withValidRequest_returns201() throws Exception {
        var createContactRequestDto = new CreateContactRequestDto();

        var nameDto = new NameDto();
        nameDto.first("Juan")
                .last("dela Cruz")
                .suffix("Jr.");

        var contactNumberDto = new ContactNumberDto();
        contactNumberDto.countryCode("+63")
                .areaCode("917")
                .subscriberNumber("123456")
                .label("Mobile");

        createContactRequestDto.setName(nameDto);
        createContactRequestDto.setContactNumbers(List.of(contactNumberDto));

        var response = restTemplate.postForEntity("http://localhost:8080/v1/contacts", createContactRequestDto, CreateContactResponseDto.class);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        var responseDto = response.getBody();

        UUID contactUuid = responseDto.getUuid();

        assertThat(contactUuid).isNotNull();
        var optionalContactEntity = jpaRepository.findById(contactUuid);

        assertThat(optionalContactEntity).isNotEmpty();
        var contactEntity = optionalContactEntity.get();

        assertThat(contactEntity.getFirstName()).isEqualTo(nameDto.getFirst());
        assertThat(contactEntity.getLastName()).isEqualTo(nameDto.getLast());
        assertThat(contactEntity.getSuffix()).isEqualTo(nameDto.getSuffix());

        var contactNumbers = contactEntity.getContactNumbers();

        assertThat(contactNumbers).hasSize(1);

        var contactNumber = contactNumbers.getFirst();

        assertThat(contactNumber.getUuid()).isNotNull();
        assertThat(contactNumber.getParentContact().getUuid()).isEqualTo(contactUuid);
        assertThat(contactNumber.getCountryCode()).isEqualTo("+63");
        assertThat(contactNumber.getAreaCode()).isEqualTo("917");
        assertThat(contactNumber.getSubscriberNumber()).isEqualTo("123456");
        assertThat(contactNumber.getLabel()).isEqualTo("Mobile");
    }
}
