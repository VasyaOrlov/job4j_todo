create table if not exists task_categories (
id serial primary key,
task_id int not null references tasks(id),
categories_id int not null references categories(id)
);