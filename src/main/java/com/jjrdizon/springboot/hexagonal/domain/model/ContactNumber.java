package com.jjrdizon.springboot.hexagonal.domain.model;

public record ContactNumber(String countryCode, String areaCode, String subscriberNumber) {
}
