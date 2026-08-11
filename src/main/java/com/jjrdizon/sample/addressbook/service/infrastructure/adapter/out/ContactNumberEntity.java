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
public class ContactNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uuid;

    private UUID contactUuid;

    private String countryCode;

    private String areaCode;

    private String subscriberNumber;

    private String label;

}
