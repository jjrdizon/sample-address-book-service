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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetContactsUseCaseImplTest {

    @Mock
    ContactRepository mockRepository;

    @InjectMocks
    GetContactsUseCaseImpl testClass;

    @Nested
    class WhenGettingContacts {

        @Test
        void shouldReturnContacts() {

            var testContacts = List.of(
                    new Contact(
                            null,
                            new Name("Juan", "dela Cruz", null),
                            new ArrayList<>()
                    ),
                    new Contact(
                            null,
                            new Name("Jose", "dela Cruz", null),
                            new ArrayList<>()
                    )
            );

            when(mockRepository.findAll()).thenReturn(testContacts);

            var contacts = testClass.getContacts();

            assertThat(contacts).isEqualTo(testContacts);

        }
    }

}