create table red_packet_record
(
    id            varchar(255) not null,
    create_time   datetime,
    update_time   datetime,
    amount        integer      not null,
    red_packet_id varchar(255),
    user_id       varchar(255),
    primary key (id)
);

create table red_packet_info
(
    id            varchar(255) not null,
    create_time   datetime,
    update_time   datetime,
    red_packet_id varchar(255),
    total_amount  integer      not null,
    total_packet  integer      not null,
    user_id       varchar(255),
    primary key (id)
);