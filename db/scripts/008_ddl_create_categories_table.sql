create table if not exists categories (
id serial primary key,
name text not null unique
);