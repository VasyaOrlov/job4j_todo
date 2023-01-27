create table if not exists priorities (
id serial primary key,
name text not null unique,
position int not null
)