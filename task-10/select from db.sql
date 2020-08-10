USE catalog;

/*Задание 1
Найдите номер модели, скорость и размер жесткого диска для всех ПК стоимостью менее 500 дол.
Вывести: model, speed и hd*/
SELECT model, speed, hd
FROM PC
WHERE price < 500;

/*Задание 2
Найдите производителей принтеров. Вывести: maker*/
SELECT DISTINCT maker
FROM Product
WHERE type = 'printer';

/*Задание 3
Найдите номер модели, объем памяти и размеры экранов ПК-блокнотов, цена которых превышает 1000 дол.*/
SELECT model, ram, screen
FROM Laptop
WHERE price > 1000;

/*Задание 4
Найдите все записи таблицы Printer для цветных принтеров.*/
SELECT *
FROM Printer
WHERE color = 'y';

/*Задание 5
Найдите номер модели, скорость и размер жесткого диска ПК, имеющих 12x или 24x CD и цену менее 600 дол.*/
SELECT model, speed, hd
FROM PC
WHERE (cd = '12x' OR cd = '24x') AND (price < 600);

/*Задание 6
Укажите производителя и скорость для тех ПК-блокнотов,
которые имеют жесткий диск объемом не менее 100 Гбайт.*/
SELECT maker, speed
FROM Product JOIN Laptop ON Laptop.model = Product.model
WHERE hd >= 100;

/*Задание 7
Найдите номера моделей и цены всех продуктов (любого типа),
выпущенных производителем B (латинская буква).*/
SELECT PC.model, PC.price 
FROM PC JOIN Product ON PC.model=Product.model 
WHERE maker='B'
UNION
SELECT Printer.model, Printer.price 
FROM Printer JOIN Product ON Printer.model=Product.model 
WHERE maker='B'
UNION
SELECT Laptop.model, Laptop.price 
FROM Laptop JOIN Product ON Laptop.model=Product.model 
WHERE maker='B';

/*Задание 8
Найдите производителя, выпускающего ПК, но не ПК-блокноты.*/
/*SELECT maker
FROM Product
WHERE type = 'PC'
EXCEPT
SELECT maker
FROM Product
WHERE type = 'Laptop';*/

/*Задание 9
Найдите производителей ПК с процессором не менее 450 Мгц. Вывести: Maker*/
SELECT DISTINCT maker
FROM Product JOIN PC ON PC.model = Product.model
WHERE speed >= 450;

/*Задание 10
Найдите принтеры, имеющие самую высокую цену. Вывести: model, price*/
SELECT  model, price
FROM Printer
WHERE price = (SELECT MAX(price) FROM Printer);

/*Задание 11
Найдите среднюю скорость ПК.*/
SELECT AVG (speed)
FROM PC;

/*Задание 12
Найдите среднюю скорость ПК-блокнотов, цена которых превышает 1000 дол.*/
SELECT AVG (speed)
FROM Laptop
WHERE price > 1000;

/*Задание 13
Найдите среднюю скорость ПК, выпущенных производителем A.*/
SELECT AVG (speed)
FROM PC JOIN Product ON PC.model = Product.model
WHERE maker = 'A';

/*Задание 14
Для каждого значения скорости найдите среднюю стоимость ПК с такой же скоростью процессора.
Вывести: скорость, средняя цена*/
/*SELECT DISTINCT speed, AVG (price)
FROM PC
WHERE maker = 'A';*/

/*Задание 15
Найдите размеры жестких дисков, совпадающих у двух и более PC. Вывести: HD*/
SELECT DISTINCT hd
FROM PC
GROUP BY hd
HAVING COUNT(hd) >= 2;

/*Задание 16
Найдите пары моделей PC, имеющих одинаковые скорость и RAM.
В результате каждая пара указывается только один раз, т.е. (i,j), но не (j,i).
Порядок вывода: модель с большим номером, модель с меньшим номером, скорость и RAM.*/
SELECT DISTINCT a.model, b.model, a.speed, a.ram
FROM PC AS a, PC AS b
WHERE a.speed=b.speed AND a.ram=b.ram AND a.model>b.model;

/*Задание 17
Найдите модели ПК-блокнотов, скорость которых меньше скорости любого из ПК. 
Вывести: type, model, speed*/
SELECT DISTINCT type, Laptop.model, speed
FROM Laptop, Product
WHERE Laptop.model=Product.model AND
speed < ALL (SELECT speed FROM PC);

/*Задание 18
Найдите производителей самых дешевых цветных принтеров. Вывести: maker, price*/
SELECT DISTINCT maker, price
FROM Printer JOIN Product 
 ON Printer.model=Product.model
WHERE color = 'y' AND price = (SELECT MIN(price) FROM Printer WHERE color = 'y');

/*Задание 19
Для каждого производителя найдите средний размер экрана выпускаемых им ПК-блокнотов.
Вывести: maker, средний размер экрана.*/
SELECT maker, AVG(screen)
FROM Laptop JOIN Product ON Laptop.model = Product.model
GROUP BY maker;

/*Задание 20
Найдите производителей, выпускающих по меньшей мере три различных модели ПК.
Вывести: Maker, число моделей*/
SELECT maker, COUNT(model) AS COUNT_MODEL
FROM Product
WHERE type = 'PC'
GROUP BY maker
HAVING COUNT(model) >= 3;

/*Задание 21
Найдите максимальную цену ПК, выпускаемых каждым производителем.
Вывести: maker, максимальная цена.*/
SELECT maker, MAX(price)
FROM PC JOIN Product ON PC.model = Product.model
GROUP BY maker;

/*Задание 22
Для каждого значения скорости ПК, превышающего 600 МГц,
определите среднюю цену ПК с такой же скоростью.
Вывести: speed, средняя цена.*/
SELECT speed, AVG(price)
FROM PC
WHERE speed > 600
GROUP BY speed;

/*Задание 23
Найдите производителей, которые производили бы как ПК со скоростью не менее 750 МГц,
так и ПК-блокноты со скоростью не менее 750 МГц. Вывести: Maker*/
/*SELECT maker
FROM Product JOIN PC
 ON PC.model = Product.model
WHERE speed >= 750
INTERSECT 
SELECT maker
FROM Product JOIN Laptop
 ON Laptop.model = Product.model
WHERE speed >= 750;*/

/*Задание 24
Перечислите номера моделей любых типов,
имеющих самую высокую цену по всей имеющейся в базе данных продукции.*/
WITH a (model,price) AS (
SELECT model, price
FROM PC
UNION ALL
SELECT model, price
FROM Laptop
UNION ALL
SELECT model, price
FROM Printer)
SELECT DISTINCT model
FROM a
WHERE price = (SELECT MAX(price) FROM a);

/*Задание 25
Найдите производителей принтеров, которые производят ПК с наименьшим объемом RAM
и с самым быстрым процессором среди всех ПК, имеющих наименьший объем RAM. Вывести: Maker*/
SELECT DISTINCT maker
FROM Product JOIN PC 
 ON PC.model = Product.model
WHERE ram = (SELECT MIN(ram)
FROM PC) AND
speed = (
SELECT MAX(speed)
FROM PC
WHERE ram = (SELECT MIN(ram)
FROM PC)
) AND maker IN (
SELECT maker
FROM Product
WHERE type = 'Printer');