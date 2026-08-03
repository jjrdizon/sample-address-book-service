package com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in;

import com.jjrdizon.springboot.hexagonal.application.port.CreateContactUseCaseImpl;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.NameDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContactsV1ControllerTest {

    @InjectMocks
    private ContactsV1Controller controller;

    @Mock
    private CreateContactUseCaseImpl createContactUseCase;

    @Nested
    class WhenCreatingContact {

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
                ResponseEntity<Void> response = controller.createContact(request);

                assertThat(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)).isTrue();
            }
        }
    }

}