package com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in;

import com.jjrdizon.springboot.hexagonal.application.port.CreateContactUseCaseImpl;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.springboot.hexagonal.infrastructure.adapter.in.dto.NameDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ContactsV1ControllerTest {

    @Autowired
    private ContactsV1Controller controller;

    @MockBean
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

            }
        }
    }

}