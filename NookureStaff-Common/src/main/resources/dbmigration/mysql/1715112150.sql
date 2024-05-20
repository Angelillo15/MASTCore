-- apply changes
create table nookure_staff_players (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  uuid                          varchar(40),
  last_login                    datetime(6) not null,
  first_login                   datetime(6) not null,
  last_ip                       varchar(255) not null,
  first_ip                      varchar(255) not null,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint uq_nookure_staff_players_uuid unique (uuid),
  constraint pk_nookure_staff_players primary key (id)
);

