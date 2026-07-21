package com.jjrdizon.springboot.hexagonal.application.port;

import com.jjrdizon.springboot.hexagonal.domain.model.Contact;
import com.jjrdizon.springboot.hexagonal.domain.model.ContactNumber;
import com.jjrdizon.springboot.hexagonal.domain.model.Name;
import com.jjrdizon.springboot.hexagonal.domain.port.ContactRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateContactUseCaseImplTest {

    @Nested
    class WhenCreatingNewContact {

        @Nested
        class GivenContact {

            @Test
            void shouldSaveInContactRepository() {

                var mockRepository = mock(ContactRepository.class);

                var testClass = new CreateContactUseCaseImpl(mockRepository);
                var testContact = new Contact(
                        new Name("Juan", "dela Cruz", null),
                        List.of(
                                new ContactNumber("+63", null, "917123456")
                        )
                );

                testClass.createContact(testContact);

                verify(mockRepository).save(testContact);
            }
        }
    }
}