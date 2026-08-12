package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.model.ContactNumber;
import com.jjrdizon.sample.addressbook.service.domain.model.Name;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactDomainRepositoryAdapterTest {

    @Mock
    ContactJpaRepository repository;

    @Spy
    ContactEntityMapper mapper = Mappers.getMapper(ContactEntityMapper.class);

    @InjectMocks
    ContactDomainRepositoryAdapter adapter;

    @Nested
    class WhenSavingContact {

        @Nested
        class GivenNonNullContact {

            Contact contact = new Contact(null, new Name("Juan", "dela Cruz", null), new ArrayList<>());

            @Test
            void shouldSaveToJpaRepository() {

                adapter.save(contact);

                verify(repository).save(assertArg(entity -> {

                    assertThat(entity).isNotNull();
                    assertThat(entity.getFirstName()).isEqualTo(contact.name().first());
                    assertThat(entity.getLastName()).isEqualTo(contact.name().last());
                    assertThat(entity.getSuffix()).isEqualTo(contact.name().suffix());

                }));
            }

            @Test
            void shouldReturnSavedEntity() {

                when(repository.save(any(ContactEntity.class))).thenAnswer(invocation -> {
                    ContactEntity argument = invocation.getArgument(0, ContactEntity.class);
                    argument.setUuid(UUID.randomUUID());
                    return argument;
                });

                Contact saved = adapter.save(contact);

                assertThat(saved).isNotNull();
                assertThat(saved.uuid()).isNotNull();
                assertThat(saved.name().first()).isEqualTo(contact.name().first());
                assertThat(saved.name().last()).isEqualTo(contact.name().last());
                assertThat(saved.name().suffix()).isEqualTo(contact.name().suffix());
            }


        }

        @Nested
        class GivenNullContact {

            @Test
            void shouldThrowException() {

                assertThatThrownBy(() -> adapter.save(null)).isInstanceOf(Exception.class);

            }
        }

    }

    @Nested
    class WhenFindingAllContact {

        @Test
        void shouldReturnAllContacts() {

            var contact1 = new ContactEntity(UUID.randomUUID(), "Juan", "dela Cruz", "Sr.", new ArrayList<>());
            var contact2 = new ContactEntity(UUID.randomUUID(), "Juan", "dela Cruz", "Jr.", new ArrayList<>());

            contact1.addContactNumberEntity( new ContactNumberEntity(UUID.randomUUID(), null, "+63", "917", "123456", "mobile"));
            contact2.addContactNumberEntity( new ContactNumberEntity(UUID.randomUUID(), null, "+63", "917", "123456", "mobile"));

            var contactEntities = List.of(contact1, contact2);

            when(repository.findAll()).thenReturn(contactEntities);

            var contacts = adapter.findAll();

            assertThat(contacts)
                    .isNotEmpty()
                    .hasSize(contactEntities.size());

            contacts.forEach(contact -> {
                assertThat(contact.name().first()).isEqualTo("Juan");
                assertThat(contact.name().last()).isEqualTo("dela Cruz");

                var contactNumbers = contact.contactNumbers();
                assertThat(contactNumbers).hasSize(1);

                var contactNumber = contactNumbers.getFirst();
                assertThat(contactNumber.countryCode()).isEqualTo("+63");
                assertThat(contactNumber.areaCode()).isEqualTo("917");
                assertThat(contactNumber.subscriberNumber()).isEqualTo("123456");
                assertThat(contactNumber.label()).isEqualTo("mobile");
            });

            assertThat(contacts.stream().anyMatch(contact -> contact.name().suffix().equals("Sr."))).isTrue();
            assertThat(contacts.stream().anyMatch(contact -> contact.name().suffix().equals("Jr."))).isTrue();


        }
    }
}