-- apply changes
create table nookure_staff_pin (
  id                            integer not null,
  player_id                     integer,
  pin                           varchar(64) not null,
  last_login                    timestamp not null,
  ip                            varchar(15) not null,
  constraint uq_nookure_staff_pin_player_id unique (player_id),
  constraint pk_nookure_staff_pin primary key (id),
  foreign key (player_id) references nookure_staff_players (id) on delete restrict on update restrict
);

