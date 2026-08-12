package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String firstName;

    private String lastName;

    private String suffix;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contactUuid")
    private List<ContactNumberEntity> contactNumbers = new ArrayList<>();

    public void addContactNumberEntity(ContactNumberEntity contactNumberEntity) {
        if(contactNumberEntity == null) return;

        if(contactNumbers == null) {
            contactNumbers = new ArrayList<>();
        }

        contactNumberEntity.setParentContact(this);
        contactNumbers.add(contactNumberEntity);
    }

}
