package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out;

import com.jjrdizon.sample.addressbook.service.domain.model.Contact;
import com.jjrdizon.sample.addressbook.service.domain.model.Name;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ContactDomainRepositoryAdapterTest {

    ContactJpaRepository repository = mock(ContactJpaRepository.class);

    ContactEntityMapper mapper = Mappers.getMapper(ContactEntityMapper.class);

    ContactDomainRepositoryAdapter adapter = new ContactDomainRepositoryAdapter(repository, mapper);

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

}