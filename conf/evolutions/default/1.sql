# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table question (
  id                        bigint not null,
  text                      varchar(255),
  answer                    varchar(255),
  is_answered               boolean,
  constraint pk_question primary key (id))
;

create sequence question_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists question;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists question_seq;

