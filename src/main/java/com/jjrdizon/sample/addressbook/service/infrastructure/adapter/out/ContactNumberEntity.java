package com.jjrdizon.sample.addressbook.service.infrastructure.adapter.out;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity(name = "contact_numbers")
@AllArgsConstructor
@NoArgsConstructor
public class ContactNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contactUuid")
    private ContactEntity parentContact;

    private String countryCode;

    private String areaCode;

    private String subscriberNumber;

    private String label;

}
