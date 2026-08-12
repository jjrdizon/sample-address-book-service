package com.jjrdizon.sample.addressbook.service;

import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactEntity;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactJpaRepository;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out.ContactNumberEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class GetContactsV1FlowIT {

    private RestTemplate restTemplate;

    @Autowired
    private ContactJpaRepository jpaRepository;

    @BeforeEach
    public void setUp() {

        restTemplate = new RestTemplate();
        jpaRepository.deleteAll();

        var contact1 = new ContactEntity(UUID.randomUUID(), "Juan", "dela Cruz", "Sr.", new ArrayList<>());
        var contact2 = new ContactEntity(UUID.randomUUID(), "Juan", "dela Cruz", "Jr.", new ArrayList<>());

        contact1.addContactNumberEntity( new ContactNumberEntity(UUID.randomUUID(), null, "+63", "917", "123456", "mobile"));
        contact2.addContactNumberEntity( new ContactNumberEntity(UUID.randomUUID(), null, "+63", "917", "123456", "mobile"));

        var contactEntities = List.of(contact1, contact2);

        jpaRepository.saveAllAndFlush(contactEntities);
    }

    @AfterEach
    public void tearDown() {
        jpaRepository.deleteAll();
    }

    @Test
    void getContacts_returns200() throws Exception {

        var response = restTemplate.exchange("http://localhost:8080/v1/contacts", HttpMethod.GET, null, new ParameterizedTypeReference<List<ContactDto>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var contactDtos = response.getBody();

        assertThat(contactDtos)
                .hasSize(2);

        contactDtos.forEach(contact -> {
            assertThat(contact.getContactNumbers().stream().allMatch(contactNumberDto -> contactNumberDto.getSubscriberNumber().equals("123456"))).isTrue();
        });

        assertThat(contactDtos.stream().anyMatch(contact -> contact.getName().getSuffix().equals("Sr."))).isTrue();
        assertThat(contactDtos.stream().anyMatch(contact -> contact.getName().getSuffix().equals("Jr."))).isTrue();
    }
}
