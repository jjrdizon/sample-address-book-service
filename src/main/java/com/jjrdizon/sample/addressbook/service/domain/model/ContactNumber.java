package com.jjrdizon.sample.addressbook.service.domain.model;

import java.util.UUID;

public record ContactNumber(UUID uuid, String countryCode, String areaCode, String subscriberNumber, String label) {
}
