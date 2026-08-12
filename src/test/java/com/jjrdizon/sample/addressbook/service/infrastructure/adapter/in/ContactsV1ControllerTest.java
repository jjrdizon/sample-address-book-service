package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in;

import com.jjrdizon.sample.addressbook.service.application.usecase.CreateContactUseCaseImpl;
import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.ContactNumberDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.CreateContactRequestDto;
import com.jjrdizon.sample.addressbook.service.infrastructure.adapter.in.dto.NameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactsV1ControllerTest {

    @InjectMocks
    private ContactsV1Controller controller;

    @Spy
    private ContactDtoMapper contactDtoMapper = Mappers.getMapper(ContactDtoMapper.class);

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
            ContactNumberDto contactNumberDto = new ContactNumberDto().countryCode("+63").areaCode("917").subscriberNumber("123456").label("Mobile");
            CreateContactRequestDto request = new CreateContactRequestDto().name(nameDto).addContactNumbersItem(contactNumberDto);

            @Test
            void thenShouldUseCreateContactUseCase() {
                controller.createContact(request);

                verify(createContactUseCase).createContact(assertArg( contact -> {
                    assertThat(contact.name().first()).isEqualTo(nameDto.getFirst());
                    assertThat(contact.name().last()).isEqualTo(nameDto.getLast());
                    assertThat(contact.contactNumbers()).hasSize(1);
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