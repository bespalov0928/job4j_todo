
drop table items;
create table items(id serial primary key,
 description text,
 created boolean,
 done boolean,
 user_id int not null references users(id));

drop table users;
create table users(id serial primary key,
 login varchar (200),
 password varchar (200));