create user 'emartuser'@'%' identified by '1234';
flush privileges;

drop database if exists emart;
create database emart DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,ALTER ON `emart`.* TO 'emartuser'@'%';
flush privileges;

use emart; 

drop table if exists buyer_brole;
drop table if exists brole;
drop table if exists buyer;
drop table if exists seller_srole;
drop table if exists srole;
drop table if exists seller;


create table buyer (
    id char(64) not null, 
    username varchar(30) not null, 
    password varchar(64) not null, 
    name varchar(64) not null, 
    email varchar(30) not null, 
    mobile varchar(22) not null, 
    expire_date datetime(6) not null, 
    status char(1) not null DEFAULT '0', 
    created_date datetime(6) not null,
    updated_date datetime(6) not null,
    primary key (id),
    unique key uk_buyer001 (username)
);

create table brole (
    id integer not null auto_increment, 
    name varchar(100), 
    note varchar(200), 
    created_date datetime(6), 
    updated_date datetime(6), 
    primary key (id)
);

create table buyer_brole (
    user_id varchar(64) not null, 
    role_id integer not null,
    CONSTRAINT fk_brole foreign key (role_id) references brole (id),
    CONSTRAINT fk_buyer foreign key (user_id) references buyer (id) on update cascade on delete cascade
);


create table seller (
    id varchar(64) not null, 
    username varchar(30) not null, 
    password varchar(64) not null, 
    email varchar(30), 
    expire_date datetime(6) not null, 
    status char(1) not null DEFAULT '0', 
    brief varchar(200) not null, 
    company_name varchar(120) not null, 
    contact_number varchar(22) not null, 
    gstin varchar(60) not null, 
    postal_address varchar(200) not null, 
    created_date datetime(6), 
    updated_date datetime(6), 
    primary key (id),
    unique key uk_seller001 (username)
);

create table srole (
    id integer not null auto_increment, 
    name varchar(100), 
    note varchar(200), 
    created_date datetime(6), 
    updated_date datetime(6), 
    primary key (id)
);

create table seller_srole (
    user_id varchar(64) not null, 
    role_id integer not null,
    CONSTRAINT fk_srole foreign key (role_id) references srole (id),
    CONSTRAINT fk_seller foreign key (user_id) references seller (id) on update cascade on delete cascade
);
