--liquibase formatted sql
--changeset Pedro Pons:dbf7466f-6118-4ddd-993b-7a2272047a38
CREATE TABLE exam.car
(
    id uuid NOT NULL,
    length decimal NOT NULL,
    weight decimal NOT NULL,
    velocity decimal NOT NULL,
    color varchar (30) NOT NULL,
    CONSTRAINT car_pk PRIMARY KEY (id)
);