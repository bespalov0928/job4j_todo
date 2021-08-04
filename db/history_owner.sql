
create table history_owner(
 id serial primary key,
 driver_id int not null references driver(id),
 car_id int not null references car(id)
 );
