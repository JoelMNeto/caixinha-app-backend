create table budgets (
    id serial primary key,
    household_id int not null,
    category_id int not null,
    amount numeric(12,2) not null,
    period varchar(20) default 'monthly',
    budget_month int,
    budget_year int,
    created_at timestamptz default now(),
    updated_at timestamptz default now(),
    foreign key (household_id) references households(id),
    foreign key (category_id) references categories(id),
    unique(household_id, category_id, budget_month, budget_year)
);

create index idx_budgets_household on budgets(household_id);