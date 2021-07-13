
drop table items;
create table items(id serial primary key,
 description text,
 created boolean,
 done boolean);