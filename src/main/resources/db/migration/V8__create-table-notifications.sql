create table notifications (
    id serial primary key,
    user_id int not null,
    type varchar (50),
    title varchar(100),
    message text,
    is_read boolean default false,
    created_at timestamptz default now(),
    foreign key (user_id) references users(id)
);

create index idx_notifications_user on notifications(user_id);