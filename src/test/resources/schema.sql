drop table discount;
create table discount (
    id serial primary key,
    type varchar(255),
    value double precision
);