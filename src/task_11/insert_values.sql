use bookstore;

truncate table Book;
truncate table Bookshelf;
truncate table BookRequest;
truncate table BookOrder;
truncate table Order_Request;
truncate table Order_Book;

insert into Book
values 
(null,'Алхимик','Пауло Коэльо',2019),
(null,'Шантарам','Грэгори Дэвид Робертс',2013),
(null,'Чистый код','Роберт Мартин',2016),
(null,'Совершенный код','Стив Макконнелл',2016),
(null,'Гибкая разработка программ на Java и C ++','Роберт Мартин',2019),
(null,'Идеальный программист','Роберт Мартин',2020),
(null,'Дневник домового','Евгений ЧеширКо',2019),
(null,'Точка обмана','Дэн Браун',2014),
(null,'Код да Винчи','Дэн Браун',2013),
(null,'Цифровая крепость','Дэн Браун',2014);

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
 