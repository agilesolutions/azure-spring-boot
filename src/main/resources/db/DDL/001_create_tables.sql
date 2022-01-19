--liquibase formatted sql

--changeset user:system-api-1.0.0 context:system-api-1.0.0 dbms:oracle

CREATE TABLE PERSON (
    id      number(19) not null,
    name varchar2(25),
    constraint pk_test primary key (id)
        using index (create unique index idx_test_pk on PERSON(id))
);

insert into ${schema}.PERSON (id, name) values (1, ‘name 1′);
insert into ${schema}.PERSON (id,  name) values (2, ‘name 2′);

--rollback drop table test;