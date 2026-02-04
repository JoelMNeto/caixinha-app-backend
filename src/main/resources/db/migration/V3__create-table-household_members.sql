create table household_members (
    id serial primary key,
    household_id int not null,
    user_id int not null,
    role varchar(20) default 'member',
    joined_at timestamptz default now(),
    foreign key (household_id) references households(id),
    foreign key (user_id) references users(id),
    unique(household_id, user_id)
);

create index idx_household_members_user on household_members(user_id);
create index idx_household_members_householdId on household_members(household_id);