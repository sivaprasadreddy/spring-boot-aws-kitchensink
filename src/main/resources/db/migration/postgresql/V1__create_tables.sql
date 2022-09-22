create sequence msg_id_seq start with 1 increment by 10;

create table messages
(
    id      bigint DEFAULT nextval('msg_id_seq') not null,
    uuid    varchar(100)                         not null,
    content varchar(1024)                        not null,
    primary key (id)
);
