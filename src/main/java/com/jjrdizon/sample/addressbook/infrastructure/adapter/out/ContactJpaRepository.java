package com.jjrdizon.sample.addressbook.infrastructure.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactJpaRepository extends JpaRepository<ContactEntity, UUID> {
}
