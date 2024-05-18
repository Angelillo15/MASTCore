-- apply changes
create table nookure_staff_players (
  id                            integer not null,
  name                          varchar(255),
  uuid                          varchar(40),
  last_login                    timestamp not null,
  first_login                   timestamp not null,
  last_ip                       varchar(255) not null,
  first_ip                      varchar(255) not null,
  version                       integer not null,
  when_created                  timestamp not null,
  when_modified                 timestamp not null,
  constraint uq_nookure_staff_players_uuid unique (uuid),
  constraint pk_nookure_staff_players primary key (id)
);

