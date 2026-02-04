create table transactions (
    id serial primary key,
    household_id int not null,
    user_id int not null,
    category_id int not null,
    type varchar(10) not null,
    amount numeric(12,2) not null,
    description varchar(255),
    transaction_date date not null,
    payment_method varchar(20),
    is_recurring boolean default false,
    recurring_transaction_id int,
    notes text,
    created_at timestamptz default now(),
    updated_at timestamptz default now(),
    foreign key (household_id) references households(id),
    foreign key (user_id) references users(id),
    foreign key (category_id) references categories(id),
    foreign key (recurring_transaction_id) references recurring_transactions(id)
);

create index idx_transactions_household on transactions(household_id);
create index idx_transactions_date on transactions(transaction_date);
create index idx_transactions_user on transactions(user_id);
create index idx_transactions_category on transactions(category_id);