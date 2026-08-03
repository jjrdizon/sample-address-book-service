package com.jjrdizon.springboot.hexagonal.infrastructure.adapter.out;

import com.jjrdizon.springboot.hexagonal.domain.model.Contact;
import com.jjrdizon.springboot.hexagonal.domain.model.Name;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ContactDomainRepositoryAdapterTest {

    ContactJpaRepository repository = mock(ContactJpaRepository.class);

    ContactDomainMapper mapper = Mappers.getMapper(ContactDomainMapper.class);

    ContactDomainRepositoryAdapter adapter = new ContactDomainRepositoryAdapter(repository, mapper);

    @Nested
    class WhenSavingContact {

        @Nested
        class GivenNonNullContact {

            Contact contact = new Contact(new Name("Juan", "dela Cruz", null), new ArrayList<>());

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