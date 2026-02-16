create table users (
    id serial primary key,
    name varchar(100) not null,
    nickname varchar(100) not null unique,
    email varchar(100) unique not null unique,
    password_hash varchar(255) not null unique,
    created_at timestamptz default NOW(),
    updated_at timestamptz default NOW(),
    verified boolean default false,
    active boolean default false,
    confirmation_code integer,
    confirmation_code_expires timestamptz
);

create index idx_users_email on users(email);