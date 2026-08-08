package com.jjrdizon.sample.addressbook.application.port;

import com.jjrdizon.sample.addressbook.domain.model.Contact;
import com.jjrdizon.sample.addressbook.domain.model.Name;
import com.jjrdizon.sample.addressbook.domain.port.ContactRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateContactUseCaseImplTest {

    @Nested
    class WhenCreatingNewContact {

        ContactRepository mockRepository = mock(ContactRepository.class);

        CreateContactUseCaseImpl testClass = new CreateContactUseCaseImpl(mockRepository);

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