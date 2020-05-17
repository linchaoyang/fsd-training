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

drop table if exists subcategory;
drop table if exists category;

create table category (
    id varchar(64) not null comment 'category id', 
    name varchar(200) not null comment 'category name', 
    description varchar(2000) not null comment 'category description', 
    created_date datetime(6), 
    updated_date datetime(6), 
    primary key (id)
);

create table subcategory (
    id varchar(64) not null comment 'subcategory id', 
    category_id varchar(64) not null comment 'category id', 
    name varchar(200) not null comment 'subcategory name', 
    description varchar(2000) not null comment 'subcategory description', 
    tax DECIMAL(7,2) not null comment 'tax percent, 5.5 means 5.5%', 
    created_date datetime(6), 
    updated_date datetime(6), 
    primary key (id),
    CONSTRAINT fk_subcategory foreign key (category_id) references category (id) on update cascade on delete cascade
);


drop table if exists product_carousel;
drop table if exists suggest_carousel;
drop table if exists product;

create table product (
    id varchar(64) not nullcomment 'product id', 
    name varchar(200) not null comment 'product name', 
    price DECIMAL(7,2) not null comment 'product price', 
    description varchar(2000) not null comment 'product description', 
    remarks varchar(100) comment 'remarks', 
    status char(1) not null default '0' comment 'Normal:0 / Locked:1 / Disabled: 2', 
    stock_number int not null comment 'product stock number', 
    image_url varchar(200) not null comment 'image url', 
    category_id varchar(64) not null comment 'category id', 
    subcategory_id varchar(64) not null comment 'subcategory id', 
    seller_id varchar(64) not null comment 'seller id', 
    created_date datetime(6), 
    updated_date datetime(6), 
    primary key (id)
);

create table product_carousel (
    id integer not null auto_increment, 
    created_date datetime(6), 
    image_url varchar(200) not null comment 'image url', 
    seq char(1) not null comment 'image carousel index', 
    updated_date datetime(6), 
    product_id varchar(64) not null comment 'product id',
    primary key (id),
    CONSTRAINT fk_product_carousel foreign key (product_id) references product (id)
);

create table suggest_carousel (
    id integer not null auto_increment, 
    created_date datetime(6), 
    description varchar(200) not null comment 'image description', 
    end_time datetime not null comment 'show end time', 
    image_url varchar(200) not null comment 'image url', 
    product_id varchar(64) not null comment 'product id', 
    seq int not null comment 'image carousel index', 
    start_time datetime not null comment 'show start time', 
    title varchar(60) not null comment 'image title', 
    updated_date datetime(6), 
    primary key (id),
    CONSTRAINT fk_suggest_carousel foreign key (product_id) references product (id)
);


drop table if exists transaction_detail;
drop table if exists transaction;

create table transaction (
    id varchar(64) not null comment 'transaction id', 
    buyer_id varchar(64) not null comment 'buyer id', 
    buyer_name varchar(200) not null comment 'buyer name', 
    email varchar(30) not null comment 'buyer email', 
    mobile varchar(22) not null comment 'buyer mobile', 
    total_amount DECIMAL(7,2) not null comment 'purchase total money amount', 
    total_tax DECIMAL(7,2) not null comment 'purchase total money tax', 
    discount_code varchar(64) comment 'discount code', 
    status char(1) not null default '0' comment '0: normal; 1: deleted by buyer', 
    remarks varchar(100) comment 'remarks', 
    created_date datetime(6), 
    updated_date datetime(6), 
    primary key (id)
);

create table transaction_detail (
    id varchar(64) not null comment 'transaction detail id', 
    transaction_id varchar(64) not null comment 'transaction id', 
    seq char(1) not null comment 'detail index', 
    product_id varchar(64) not null comment 'product id', 
    product_name varchar(200) not null comment 'product name', 
    seller_id varchar(64) not null comment 'seller id', 
    seller_name varchar(200) not null comment 'seller name', 
    stock_number int not null comment 'purchase stock number', 
    image_url varchar(200) not null comment 'image url', 
    price DECIMAL(7,2) not null comment 'purchase price', 
    total_amount DECIMAL(7,2) not null comment 'purchase total money amount', 
    total_tax DECIMAL(7,2) not null comment 'purchase total money tax', 
    created_date datetime(6), 
    updated_date datetime(6), 
    primary key (id),
    CONSTRAINT fk_transaction_detail foreign key (transaction_id) references transaction (id)
);