package com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in;

import com.jjrdizon.springboot.hexagonal.application.port.CreateContactUseCaseImpl;
import com.jjrdizon.springboot.hexagonal.domain.model.Contact;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.CreateContactResponseDto;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.NameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactsV1ControllerTest {

    @InjectMocks
    private ContactsV1Controller controller;

    @Mock
    private CreateContactUseCaseImpl createContactUseCase;

    @Nested
    class WhenCreatingContact {

        @BeforeEach
        public void setUp() {
            Contact mock = mock(Contact.class);
            when(mock.uuid()).thenReturn(UUID.randomUUID());

            when(createContactUseCase.createContact(any(Contact.class))).thenReturn(mock);
        }

        @Nested
        class GivenCreateContactRequest {

            NameDto nameDto = new NameDto().first("Juan").last("dela Cruz");
            CreateContactRequestDto request = new CreateContactRequestDto().name(nameDto);

            @Test
            void thenShouldUseCreateContactUseCase() {
                controller.createContact(request);

                verify(createContactUseCase).createContact(assertArg( contact -> {
                    assertThat(contact.name().first()).isEqualTo(nameDto.getFirst());
                    assertThat(contact.name().last()).isEqualTo(nameDto.getLast());
                }));
            }

            @Test
            void thenShouldReturnHttpCreated() {
                var response = controller.createContact(request);

                assertThat(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)).isTrue();
            }

            @Test
            void thenShouldReturnCreateContactResponse() {
                var response = controller.createContact(request);

                assertThat(response.getBody()).isNotNull();
            }
        }
    }

}