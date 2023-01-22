CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   name varchar not null,
   description TEXT not null,
   created TIMESTAMP not null,
   done BOOLEAN not null
);