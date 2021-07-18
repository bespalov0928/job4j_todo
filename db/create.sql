
drop table items;
create table items(id serial primary key,
 description text,
 created boolean,
 done boolean,
 user_id int not null references Acaunt(id));

drop table Acaunt;
create table Acaunt(id serial primary key,
 login varchar (200) UNIQUE,
 password varchar (200));