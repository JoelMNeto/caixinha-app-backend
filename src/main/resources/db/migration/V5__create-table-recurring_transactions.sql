create table recurring_transactions (
    id serial primary key,
    household_id int not null,
    user_id int not null,
    category_id int not null,
    type varchar(10) not null,
    amount numeric(12,2) not null,
    description varchar(255),
    frequency varchar(20) not null,
    start_date date not null,
    end_date date,
    payment_method varchar(20),
    is_active boolean default true,
    created_at timestamptz default now(),
    updated_at timestamptz default now(),
    foreign key (household_id) references households(id),
    foreign key (user_id) references users(id),
    foreign key (category_id) references categories(id)
);

create index idx_recurring_household on recurring_transactions(household_id);