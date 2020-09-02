drop database if exists bookstore;
create database bookstore;
use bookstore;

create table Book (
id int primary key auto_increment, 
title varchar(100) not null, 
author varchar(50) not null, 
publicationYear smallint not null);

create table Bookshelf (
id int primary key auto_increment, 
book_id int references Book(id), 
count smallint, 
price real, 
arrival_date date not null);


create table BookRequest (
id int primary key auto_increment,
book_id int references Book(id),  
count smallint, 
status enum('NEW', 'COMPLETED', 'CANCELED'));

create table BookOrder (
id int primary key auto_increment, 
fullName varchar(30) not null, 
address varchar(50) not null,
phoneNumber varchar(15) not null, 
order_date date not null,
completion_date date,
price real,
status enum('NEW', 'COMPLETED', 'CANCELED'));

create table Order_Request (
order_id int references BookOrder(id),  
request_id int references BookRequest(id));

create table Order_Book (
order_id int references BookOrder(id),  
book_id int references Book(id));