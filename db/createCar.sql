create table drivers(
 id serial primary key
 );

create table engines(
 id serial primary key
 );

create table cars(
 id serial primary key,
 name text,
 driver_id int references Drivers(id),
 engine_id int not null references Engines(id)
 );

create table history_owner(
 id serial primary key,
 driver_id int not null references drivers(id),
 car_id int not null references cars(id)
 );