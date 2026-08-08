package com.jjrdizon.springboot.hexagonal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.CreateContactResponseDto;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.NameDto;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.out.ContactEntity;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.out.ContactJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CreateContactV1FlowIT {

    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

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
        nameDto.first("Juan").last("dela Cruz").suffix("Jr.");

        createContactRequestDto.setName(nameDto);

        var response = restTemplate.postForEntity("http://localhost:8080/v1/contacts", createContactRequestDto, CreateContactResponseDto.class);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        var responseDto = response.getBody();

        UUID contactUuid = responseDto.getUuid();

        assertThat(contactUuid).isNotNull();
        var optionalContactEntity = jpaRepository.findById(contactUuid);

        assertThat(optionalContactEntity).isNotEmpty();
        ContactEntity contactEntity = optionalContactEntity.get();

        assertThat(contactEntity.getFirstName()).isEqualTo(nameDto.getFirst());
        assertThat(contactEntity.getLastName()).isEqualTo(nameDto.getLast());
        assertThat(contactEntity.getSuffix()).isEqualTo(nameDto.getSuffix());
    }
}
