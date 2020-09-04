drop schema if exists bookstore;
create schema bookstore default character set utf8mb4;
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

insert into Book
values 
(1,'Алхимик','Пауло Коэльо',2019),
(2,'Шантарам','Грэгори Дэвид Робертс',2013),
(3,'Чистый код','Роберт Мартин',2016),
(4,'Совершенный код','Стив Макконнелл',2016),
(5,'Гибкая разработка программ на Java и C ++','Роберт Мартин',2019),
(6,'Идеальный программист','Роберт Мартин',2020),
(7,'Дневник домового','Евгений ЧеширКо',2019),
(8,'Точка обмана','Дэн Браун',2014),
(9,'Код да Винчи','Дэн Браун',2013),
(10,'Цифровая крепость','Дэн Браун',2014);

insert into Bookshelf
values 
(1,1,7,14.220,'2020-08-03'),
(2,2,0,29.020,'2020-03-21'),
(3,3,6,40.170,'2020-07-05'),
(4,4,4,66.450,'2019-11-13'),
(5,5,3,109.170,'2020-02-28'),
(6,6,3,23.720,'2020-07-05'),
(7,7,9,24.050,'2019-01-16'),
(8,8,5,32.720,'2018-07-07'),
(9,9,5,32.570,'2018-07-07'),
(10,10,5,32.720,'2018-07-07');

insert into BookRequest
values 
(1,6,1,'CANCELED'),
(2,3,1,'COMPLETED'),
(3,2,1,'NEW');
 
insert into BookOrder
values 
(1,'John Snow','Alabama Street 30, New-York','741258963','2020-07-05','2020-07-05',282.75,'COMPLETED'),
(2,'Alibaba','Chaina Town','789654123','2020-07-05',null,145.78,'CANCELED'),
(3,'Ivan Ivanovich','Moscov','85214693','2020-07-05','2020-07-05',93.24,'COMPLETED');
 
insert into Order_Request
values 
(2,1),
(3,2);
 
insert into Order_Book
values 
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(2,10),
(2,9),
(2,8),
(2,7),
(2,6),
(3,3),
(3,2),
(3,7);