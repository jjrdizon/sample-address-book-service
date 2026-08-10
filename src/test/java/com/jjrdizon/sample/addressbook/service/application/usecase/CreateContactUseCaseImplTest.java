package com.jjrdizon.sample.addressbook.service.application.usecase;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.model.Name;
import com.jjrdizon.sample.addressbook.service.domain.port.ContactRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateContactUseCaseImplTest {

    @Mock
    ContactRepository mockRepository;

    @InjectMocks
    CreateContactUseCaseImpl testClass;

    @Nested
    class WhenCreatingNewContact {

        @Nested
        class GivenContact {

            @Test
            void shouldSaveInContactRepository() {


                var testContact = new Contact(
                        null,
                        new Name("Juan", "dela Cruz", null),
                        new ArrayList<>()
                );

                testClass.createContact(testContact);

                verify(mockRepository).save(testContact);
            }

            @Test
            void shouldReturnSavedContact() {

                var savedContact = mock(Contact.class);
                when(mockRepository.save(any())).thenReturn(savedContact);

                var testContact = new Contact(
                        null,
                        new Name("Juan", "dela Cruz", null),
                        new ArrayList<>()
                );

                var returnedContact = testClass.createContact(testContact);

                assertThat(returnedContact).isEqualTo(savedContact);
            }
        }
    }
}