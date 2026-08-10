package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
