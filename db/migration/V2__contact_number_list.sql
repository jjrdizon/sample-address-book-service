
CREATE TABLE contact_numbers (
    uuid uuid NOT NULL,
    contact_uuid uuid NOT NULL,
    country_code character varying(10),
    area_code character varying(10),
    subscriber_number character varying(20),
    label character varying(255)
);

ALTER TABLE ONLY contact_numbers
    ADD CONSTRAINT contact_numbers_pkey PRIMARY KEY (uuid);

CREATE INDEX idx_contact_numbers_contact_uuid
    ON contact_numbers (contact_uuid);