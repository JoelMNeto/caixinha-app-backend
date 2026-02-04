create table categories (
    id serial primary key,
    household_id int not null,
    name varchar(50) not null,
    type varchar(10) not null,
    icon varchar(50),
    color varchar(7),
    is_default boolean default false,
    created_at timestamptz default now(),
    foreign key (household_id) references households(id)
);

create index idx_categories_household on categories(household_id);