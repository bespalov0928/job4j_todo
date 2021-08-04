
create table cars(
 id serial primary key,
 name text,
 driver_id int references Driver(id),
 engine_id int not null references Engine(id)
 );
