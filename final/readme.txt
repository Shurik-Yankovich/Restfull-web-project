Предварительные действия для подготовки проекта к сборке (необходимо наличие субд MySQL):
1) В папке final\Web\rest\src\main\resources найти файл application.properties.
2) В файле application.properties изменить логин и пароль для доступа к базе данных, изменив значение в строках "spring.datasource.username" и "spring.datasource.password".
3) В папке final\Web\rest\src\main\resources в файле log4j.properties настроить путь для создания логов, изменив значение в строке "log4j.appender.file.File".
4) В папке final в файле create.bat изменить 3 строку (mysql -uroot -proot <create.sql), указав логин и пароль для вашей субд (mysql -u*логин -p*пароль <create.sql, заменив "логин" и "пароль", вашими данными, * не печатается).

Для упаковки и запуска проекта (необходимо наличе Tomcat версии 9.0 и выше):
1) В папке final запустить файл create.bat
2) После успешного выполнения задеплоить файл experience_exchange_service.war, находящийся в папке final\project, на сервер Tomcat.
