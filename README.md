**Team Work**
- [Youssef Wadie](https://github.com/youssefwadie)
- [Abdullah Said](https://github.com/AbdullahSaidAbdeaaziz)

**Table of content**
1. [Schema](#Schema)
2. [Queries](#Queries)
3. [Installation](#installation)
# Cars Showroom
Simple DataBase for Car Showroom interest in sales cars show another model of the another car and their Manufacturer.

---

Installation
===
| Install               | Info                                                                                                                                                                                                                                                                                             |
|-----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Database              | go to File `Excuation.sql (/db/relational-model)`double click  on it;  it will open on `workbench` or  `mysql server` or copy that in the file and anywhere mysql online browser ...etc if you have and then Run queries and then refresh  to see your database ![database](/pics/workbench.png) |
| Dependency            | we use [Maven](https://maven.apache.org/) to install [mysql Connecter](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.29) ![MavenDependency](/pics/maven.png)                                                                                                                 |
| User (user of server) | go to File `persistence.xml (/target/classes/META-INF)` and change your sql server `user name` and `password` ![userPassword](/pics/userPassword.png)                                                                                                                                            |

Relational model
===
![relational_model](/db/relational-model/relational_model.png)

---
Schema
===
![schema](/db/schema.png)

Queries
===
> **Query 1**
> Display the serial number and the price of the Toyota corolla
---
![Query 1](/pics/q1.png)
> **Query 2**
> Retrieve a model and price of each car whose has price between 150000 and 250000.
---
![Query 2](/pics/q2.png)
> **Query 3**
> Retrieve a model, price, and price after adding 7% interest of all cars ordered from the most expensive car to the cheapest car.
---
![Query 3](/pics/q3.png)
> **Query 4**
> Make a report named report1 (view) that contains car model, car manufacturer, car price, option name, option price and the total price of the car with each of its option.
---
![Query 4](/pics/q4.png)
> **Query 5**
> For each car model, display the total prices of its options.
---
![Query 5](/pics/q5.png)
> **Query 6**
> Retrieve the name and phone of salesperson whose name start with “s”.
---
![Query 6](/pics/q6.png)
> **Query 7**
> Retrieve the name of salesperson who has no phone.
---
![Query 7](/pics/q7.png)
> **Query 8**
> Make a report named report2 (view) that contains each car sales details (car model, car price, salesperson name, sales date, and sales price)
---
![Query 8](/pics/q8.png)
> **Query 9**
> For each salesperson, display the number of cars that he sold and summation of their prices.
---
![Query 9](/pics/q9.png)
> **Query 10**
> Display the details of the cheapest car(s).
---
![Query 10](/pics/q10.png)









