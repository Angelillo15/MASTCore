-- apply changes
create table nookure_staff_notes (
  id                            integer not null,
  player_id                     integer not null,
  note                          varchar(1024) not null,
  active                        int not null,
  show_on_join                  int not null,
  show_only_to_administrators   int not null,
  version                       integer not null,
  when_created                  timestamp not null,
  when_modified                 timestamp not null,
  constraint pk_nookure_staff_notes primary key (id),
  foreign key (player_id) references nookure_staff_players (id) on delete restrict on update restrict
);

