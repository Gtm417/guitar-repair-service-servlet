# Guitar repair service

Option №3  
```
Repair agency system. The user can create a request for product repair. 
The manager can accept the request indicating the price, or reject the request, indicating the reason. 
The master can complete the order accepted by the Manager. 
The user can leave feedback on completed order.
```

Вариант №3  
```
Система Ремонтное Агенство. Пользователь может создать заявку на ремонт изделия. 
Менеджер может принять заявку указав цену, либо отклонить заявку, указав причину. 
Мастер может выполнить принятую Менеджером заявку. 
Пользователь может оставить Отзыв о выполненных работах. 
```
## Installation and running
**Requirements**
```
JDK 1.8
Apache Tomcat
Apache Maven
MySQL
```

**Running the project**
```
Clone project to your local repository

Run scripts from /resources/db/ folder to create database and tables (dbCreation.sql) 
and to insert data (dbInsertion.sql)

Update DB login and password in /resources/database.properties

From project root folder run - mvn tomcat7:run

Use http://localhost:8080/ to view website
```
**Default users**
```
ADMIN otec@gmail.com - 12345678
MASTER top@gmail.com - 12345678
CLIENT neil@gmail.com - 12345678
```

## Tests coverage
![image](http://i.piccy.info/i9/804c7347da947df1c40be7e73a997861/1582053743/64851/1360567/test.jpg)

#
![image](http://i.piccy.info/i9/94945f3b7a6579486aad3bbc2a7ee99b/1580829639/53445/1360567/guitar.jpg)
