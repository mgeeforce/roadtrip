# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint not null,
  created_by_id             bigint,
  updated_by_id             bigint,
  name                      varchar(255),
  description               varchar(255),
  created                   timestamp not null,
  updated                   timestamp not null,
  constraint pk_category primary key (id))
;

create table expense (
  id                        bigint not null,
  created_by_id             bigint,
  updated_by_id             bigint,
  expense_date              timestamp not null,
  amount                    decimal(9,3) not null,
  merchant_name             varchar(255) not null,
  report_id                 bigint,
  category_id               bigint,
  currency                  integer not null,
  remimbursable             boolean,
  attachment                bytea,
  attachment_mime_type      integer,
  user_id                   bigint,
  created                   timestamp not null,
  updated                   timestamp not null,
  constraint ck_expense_currency check (currency in (0,1)),
  constraint ck_expense_attachment_mime_type check (attachment_mime_type in (0,1,2,3)),
  constraint pk_expense primary key (id))
;

create table receipt (
  id                        bigint not null,
  created_by_id             bigint,
  updated_by_id             bigint,
  expense_id                bigint,
  attachment                bytea,
  attachment_mime_type      integer,
  created                   timestamp not null,
  updated                   timestamp not null,
  constraint ck_receipt_attachment_mime_type check (attachment_mime_type in (0,1,2,3)),
  constraint pk_receipt primary key (id))
;

create table report (
  id                        bigint not null,
  created_by_id             bigint,
  updated_by_id             bigint,
  name                      varchar(255) not null,
  status                    integer,
  approved_by_id            bigint,
  submitted                 timestamp,
  approved                  timestamp,
  created                   timestamp not null,
  updated                   timestamp not null,
  constraint ck_report_status check (status in (0,1,2,3,4)),
  constraint pk_report primary key (id))
;

create table roadtrip_user (
  id                        bigint not null,
  created_by_id             bigint,
  updated_by_id             bigint,
  auth_token                varchar(255),
  email_address             varchar(255) not null,
  sha_password              bytea not null,
  full_name                 varchar(256) not null,
  created                   timestamp not null,
  updated                   timestamp not null,
  constraint uq_roadtrip_user_email_address unique (email_address),
  constraint pk_roadtrip_user primary key (id))
;

create sequence category_seq;

create sequence expense_seq;

create sequence receipt_seq;

create sequence report_seq;

create sequence roadtrip_user_seq;

alter table category add constraint fk_category_createdBy_1 foreign key (created_by_id) references roadtrip_user (id);
create index ix_category_createdBy_1 on category (created_by_id);
alter table category add constraint fk_category_updatedBy_2 foreign key (updated_by_id) references roadtrip_user (id);
create index ix_category_updatedBy_2 on category (updated_by_id);
alter table expense add constraint fk_expense_createdBy_3 foreign key (created_by_id) references roadtrip_user (id);
create index ix_expense_createdBy_3 on expense (created_by_id);
alter table expense add constraint fk_expense_updatedBy_4 foreign key (updated_by_id) references roadtrip_user (id);
create index ix_expense_updatedBy_4 on expense (updated_by_id);
alter table expense add constraint fk_expense_report_5 foreign key (report_id) references report (id);
create index ix_expense_report_5 on expense (report_id);
alter table expense add constraint fk_expense_category_6 foreign key (category_id) references category (id);
create index ix_expense_category_6 on expense (category_id);
alter table expense add constraint fk_expense_user_7 foreign key (user_id) references roadtrip_user (id);
create index ix_expense_user_7 on expense (user_id);
alter table receipt add constraint fk_receipt_createdBy_8 foreign key (created_by_id) references roadtrip_user (id);
create index ix_receipt_createdBy_8 on receipt (created_by_id);
alter table receipt add constraint fk_receipt_updatedBy_9 foreign key (updated_by_id) references roadtrip_user (id);
create index ix_receipt_updatedBy_9 on receipt (updated_by_id);
alter table receipt add constraint fk_receipt_expense_10 foreign key (expense_id) references expense (id);
create index ix_receipt_expense_10 on receipt (expense_id);
alter table report add constraint fk_report_createdBy_11 foreign key (created_by_id) references roadtrip_user (id);
create index ix_report_createdBy_11 on report (created_by_id);
alter table report add constraint fk_report_updatedBy_12 foreign key (updated_by_id) references roadtrip_user (id);
create index ix_report_updatedBy_12 on report (updated_by_id);
alter table report add constraint fk_report_approvedBy_13 foreign key (approved_by_id) references roadtrip_user (id);
create index ix_report_approvedBy_13 on report (approved_by_id);
alter table roadtrip_user add constraint fk_roadtrip_user_createdBy_14 foreign key (created_by_id) references roadtrip_user (id);
create index ix_roadtrip_user_createdBy_14 on roadtrip_user (created_by_id);
alter table roadtrip_user add constraint fk_roadtrip_user_updatedBy_15 foreign key (updated_by_id) references roadtrip_user (id);
create index ix_roadtrip_user_updatedBy_15 on roadtrip_user (updated_by_id);



# --- !Downs

drop table if exists category cascade;

drop table if exists expense cascade;

drop table if exists receipt cascade;

drop table if exists report cascade;

drop table if exists roadtrip_user cascade;

drop sequence if exists category_seq;

drop sequence if exists expense_seq;

drop sequence if exists receipt_seq;

drop sequence if exists report_seq;

drop sequence if exists roadtrip_user_seq;

