alter table tasks add column user_id INT not null references users(id);