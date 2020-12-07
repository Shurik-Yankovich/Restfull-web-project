@echo off
cd ./SQL
mysql -uroot -proot <create.sql
echo database created

@timeout /T 3

cd ../Web
mvn clean install
echo project compiled

@pause

