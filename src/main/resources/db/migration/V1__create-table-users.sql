create table users (
    id serial primary key,
    name varchar(100) not null,
    email varchar(100) unique not null,
    password_hash varchar(255) not null,
    created_at timestamptz default NOW(),
    updated_at timestamptz default NOW(),
    verified boolean default true
);

create index idx_users_email on users(email);