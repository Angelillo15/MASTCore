-- apply changes
create table nookure_staff_pin (
  id                            bigint auto_increment not null,
  player_id                     bigint,
  pin                           varchar(64) not null,
  last_login                    datetime(6) not null,
  ip                            varchar(15) not null,
  constraint uq_nookure_staff_pin_player_id unique (player_id),
  constraint pk_nookure_staff_pin primary key (id)
);

-- foreign keys and indices
alter table nookure_staff_pin add constraint fk_nookure_staff_pin_player_id foreign key (player_id) references nookure_staff_players (id) on delete restrict on update restrict;

