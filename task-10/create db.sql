drop database if exists catalog;
create database catalog;
use catalog;

create table Product (
maker varchar(10), 
model varchar(50) primary key, 
type varchar(50));

create table PC (
code int primary key auto_increment, 
model varchar(50) references Product(model), 
speed smallint, 
ram smallint, 
hd real, cd varchar(10), 
price int not null);

create table Laptop (
code int primary key auto_increment,
model varchar(50) references Product(model), 
speed smallint, 
ram smallint, 
hd real, 
price int not null,
screen tinyint);

create table Printer (
code int primary key auto_increment, 
model varchar(50) references Product(model), 
color char(1), 
type varchar(10),
price int not null);