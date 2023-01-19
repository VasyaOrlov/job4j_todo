CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   name varchar not null unique,
   description TEXT not null,
   created TIMESTAMP not null,
   done BOOLEAN not null
);