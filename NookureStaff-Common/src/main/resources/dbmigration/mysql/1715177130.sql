-- apply changes
create table nookure_staff_notes (
  id                            bigint auto_increment not null,
  player_id                     bigint not null,
  note                          varchar(1024) not null,
  active                        tinyint(1) not null,
  show_on_join                  tinyint(1) not null,
  show_only_to_administrators   tinyint(1) not null,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_nookure_staff_notes primary key (id)
);

-- foreign keys and indices
create index ix_nookure_staff_notes_player_id on nookure_staff_notes (player_id);
alter table nookure_staff_notes add constraint fk_nookure_staff_notes_player_id foreign key (player_id) references nookure_staff_players (id) on delete restrict on update restrict;

