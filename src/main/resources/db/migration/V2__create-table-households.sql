create table households (
    id serial primary key,
    name varchar(100) not null,
    created_by int not null,
    created_at timestamptz default NOW(),
    updated_at timestamptz default NOW(),
    foreign key (created_by) references users(id)
);