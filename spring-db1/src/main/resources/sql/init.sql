drop table if exists member cascade;

create table member (
  member_id varchar(10),
  money integer not null default 0,
  primary key (member_id)
);

insert into member values('1',10000);
insert into member values('2',20000);