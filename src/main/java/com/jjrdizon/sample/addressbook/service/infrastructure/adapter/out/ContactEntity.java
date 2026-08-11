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

    @OneToMany
    @JoinColumn(name = "contact_uuid")
    private List<ContactNumberEntity> contactNumbers = new ArrayList<>();

}
