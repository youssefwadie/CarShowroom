**Team Work**

- [Youssef Wadie](https://github.com/youssefwadie)
- [Abdullah Said](https://github.com/AbdullahSaidAbdeaaziz)

# Cars Showroom
Simple DataBase for Car Showroom interest in sales cars show another model of the another car and their Manufacturer.



**Table of content**

1. [Schema](#Schema)
2. [Relational model](#relational-model)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)
5. [Queries](#Queries)

---
Schema
===
![schema](/images/db/schema.png)

---
Relational model
===
![relational_model](/images/db/relational_model.png)

---
Prerequisites
===

1. JDK 1.8+
2. [Maven build system](https://maven.apache.org/)
3. DBMS; you can use any SQL implementation, but you'll have to change
   the [JDBC url](/src/main/resources/META-INF/persistence.xml#L11), and
   the [driver](/src/main/resources/META-INF/persistence.xml#L10) and install the appropriate `JDBC` driver in
   the [pom](pom.xml#L11)

---
Installation
===

## After installing the [Prerequisites](#prerequisites)

| Install            | Info                                                                                                                                                                                                                                                                                                            |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Database           | go to [create.sql](/db/create.sql) double click  on it; it will open on `workbench` or  `mysql server` or copy that in the file and anywhere mysql online browser ...etc if you have and then Run queries and then refresh  to see your database ![database](/images/installation/workbench.png)                |
| Dependency         | `maven` is used to install the JDBC driver and the JPA implementation. Here we're using [MariaDB Connector](https://mariadb.com/kb/en/installing-mariadb-connectorj/) and [eclipselink](https://www.eclipse.org/eclipselink/) as a JPA implementation ![maven dependencies](/images/installation/maven-dep.png) |
| DBMS configuration | go to [persistence.xml](/src/main/resources/META-INF/persistence.xml) and change your sql server `user name` and `password` according to your DBMS configuration ![pu-config](/images/installation/pu-config.png)                                                                                               |

Queries
===
#### Only three named queries are being used, in conjunction with three helper methods in the Query Service:
1. `name = Car.FIND_ALL, query = "SELECT c FROM Car c"`
   ```java
    public List<Car> findAllCars() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            return manager.createNamedQuery(Car.FIND_ALL, Car.class).getResultList();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }
    }
   ```
2. `name = Salesperson.FIND_ALL, query = "SELECT s FROM Salesperson s"`
   ```java
        public List<Salesperson> findAllSalespeople() {
            EntityManager manager = EntityManagerProducer.createEntityManager();
            try {
                return manager.createNamedQuery(Salesperson.FIND_ALL, Salesperson.class).getResultList();
            } finally {
                if (manager != null && manager.isOpen()) {
                    manager.close();
                }
            }
        }

   ```
3. `name = Sale.FIND_ALL, query = "SELECT s FROM Sale s"`
   ```java
        public List<Sale> findAllSales() {
            EntityManager manager = EntityManagerProducer.createEntityManager();
            try {
                return manager
                        .createNamedQuery(Sale.FIND_ALL, Sale.class)
                        .getResultList();
            } finally {
                if (manager != null && manager.isOpen()) {
                    manager.close();
                }
            }
        }

   ```

> **Query 1**
> Display the serial number and the price of the Toyota corolla
---

```java
    public void query1() {
        List<Car> cars = service.findAllCars();
        Car toyotaCorolla = null;

        for (Car car : cars) {
            if (car.getModel().equals("Corolla") && car.getManufacturer().equals("Toyota")) {
                toyotaCorolla = car;
                break;
            }
        }

        if (toyotaCorolla == null) {
            System.err.printf("%sToyota Corolla is not found!%s%n", Colors.ANSI_RED, Colors.ANSI_RESET);
            return;
        }

        System.out.println(PerformQueries.separator);
        System.out.println(centerString("\tQuery 1") + "\n");
        System.out.printf("\t%s %s's serial number is %s, and its price = %s%n",
                toyotaCorolla.getManufacturer(), toyotaCorolla.getModel(),
                toyotaCorolla.getSerialNumber(), toyotaCorolla.getPrice().toString());
        System.out.println("\n" + PerformQueries.separator);
    }
```

![Query 1](/images/quries/1.PNG)
> **Query 2**
> Retrieve a model and price of each car whose has price between 150000 and 250000.
---

```java
    public void query2() {
        List<Car> cars = service.findAllCars();
        List<Car> filteredCars = cars
                .stream()
                .filter(car -> car.getPrice() >= 150_000.0 && car.getPrice() <= 250_000)
                .collect(Collectors.toList());

        System.out.println(PerformQueries.separator);
        System.out.println(centerString("\tQuery 2") + "\n");
        for (Car car : filteredCars) {
            System.out.printf("\tCar's model is %s and its price = %.2f%n", car.getModel(), car.getPrice());
        }
        System.out.println(PerformQueries.separator);
    }

```

![Query 2](/images/quries/2.PNG)
> **Query 3**
> Retrieve a model, price, and price after adding 7% interest of all cars ordered from the most expensive car to the
> cheapest car.
---

```java
    public void query3() {
        Double afterInterest = 1.07d;
        List<Car> cars = service.findAllCars();
        cars.sort(Comparator.comparing(Car::getPrice));
        System.out.println(PerformQueries.separator);
        System.out.println(centerString("\tQuery 3") + "\n");
        for (Car car : cars) {
            System.out.printf("\tCar's model = %s, price = %.2f, and price after adding interest = %.2f%n"
                    , car.getModel(), car.getPrice(), car.getPrice() * afterInterest);
        }
        System.out.println("\n" + PerformQueries.separator);
    }
```

![Query 3](/images/quries/3.PNG)

> **Query 4**
> Make a report named report1 (view) that contains car model, car manufacturer, car price, option name, option price and
> the total price of the car with each of its option.
---

```java
    public void query4() {
        List<Car> cars = service.findAllCars();
        List<CarOption> options = service.findAllOptions();

        cars.forEach(car -> {
            // may be not fetched by the persistence provider
            if (car.getOptions().size() == 0) {
                car.setOptions(options.stream()
                        .filter(o -> o.getId().getCarSerialNumber().equals(car.getSerialNumber()))
                        .collect(Collectors.toList()));
            }
        });

        System.out.println(PerformQueries.separator);
        System.out.println(centerString("Report 1"));
        System.out.println(PerformQueries.separator);

        for (Car car : cars) {
            Double totalPrice = car.getPrice();
            String carModel = String.format("Car's model: %s", car.getModel());
            String carManufacturer = String.format("Car's manufacturer: %s", car.getManufacturer());
            String carPrice = String.format("Car's price: %.2f", car.getPrice());
            System.out.println(centerString(carModel));
            System.out.println(centerString(carManufacturer));
            System.out.println(centerString(carPrice));
            if (car.getOptions().size() != 0) {
                System.out.println(centerString("Options"));
                for (CarOption option : car.getOptions()) {
                    String optionName = String.format("Option name: %s", option.getId().getOptionName());
                    String optionPrice = String.format("Option price: %.2f", option.getPrice());
                    totalPrice += option.getPrice();
                    System.out.println(centerString(optionName));
                    System.out.println(centerString(optionPrice));
                }
            }
            System.out.println(centerString(String.format("Total price: %.2f", totalPrice)));

            System.out.println(separator);
        }
    }

```

![Query 4](/images/quries/4.PNG)
> **Query 5**
> For each car model, display the total prices of its options.
---

```java
    public void query5() {
        List<CarOption> options = service.findAllOptions();
        List<Car> cars = service.findAllCars();

        cars.forEach(car -> {
            // may be not fetched by the persistence provider
            if (car.getOptions().size() == 0) {
                car.setOptions(options.stream()
                        .filter(o -> o.getId().getCarSerialNumber().equals(car.getSerialNumber()))
                        .collect(Collectors.toList()));
            }
        });

        System.out.println(PerformQueries.separator);
        System.out.println(centerString("\tQuery 5") + "\n");
        for (Car car : cars) {
            Double optionsPrice = 0.0d;
            for (CarOption option : car.getOptions()) {
                optionsPrice += option.getPrice();
            }
            if (optionsPrice == 0.0d) {
                System.out.printf("\tCar's model = %s has no options%n", car.getModel());
            } else {
                System.out.printf("\tCar's model = %s, its options price = %.2f%n", car.getModel(), optionsPrice);
            }
        }
        System.out.println(PerformQueries.separator);
    }

```

![Query 5](/images/quries/5.PNG)
> **Query 6**
> Retrieve the name and phone of salesperson whose name start with “s”.
---

```java
    public void query6() {
        List<Salesperson> salespeople = service.findAllSalespeople();
        List<Salesperson> salespeopleWithPrefix = salespeople
                .stream().filter(s -> s.getName().startsWith("s") || s.getName().startsWith("S"))
                .collect(Collectors.toList());

        System.out.println(PerformQueries.separator);
        System.out.println(centerString("\tQuery 6") + "\n");
        for (Salesperson salesPerson : salespeopleWithPrefix) {
            System.out.printf("\tSales Person: %s, phone: %s%n", salesPerson.getName(), salesPerson.getPhone());
        }
        System.out.println(PerformQueries.separator);
    }
```

![Query 6](/images/quries/6.PNG)

> **Query 7**
> Retrieve the name of salesperson who has no phone.
---

```java
    public void query7() {
        List<Salesperson> salespeopleWithNoNumber = service.findAllSalespeople()
                .stream()
                .filter(s -> s.getPhone().isEmpty()).collect(Collectors.toList());
        System.out.println(PerformQueries.separator);
        System.out.println(centerString("\tQuery 7") + "\n");
        for (Salesperson salesPerson : salespeopleWithNoNumber) {
            System.out.printf("\t%s is salesperson with no number%n", salesPerson.getName());
        }
        System.out.println(PerformQueries.separator);
    }
```

![Query 7](/images/quries/7.PNG)
> **Query 8**
> Make a report named report2 (view) that contains each car sales details (car model, car price, salesperson name, sales
> date, and sales price)
---

```java
public void query8() {
        List<Sale> sales = service.findAllSales();

        System.out.println(PerformQueries.separator);
        System.out.println(centerString("Report 2"));
        System.out.println(PerformQueries.separator);
        for (Sale sale : sales) {
            String[] lines = new String[]{
                    String.format("Car model: %s", sale.getCar().getModel()),
                    String.format("Car price: %.2f", sale.getCar().getPrice()),
                    String.format("Salesperson's name: %s", sale.getSalesperson().getName()),
                    String.format("Sales date: %s", sale.getSoldDate().toString()),
                    String.format("Sale price: %.2f", sale.getSalePrice()),
                    PerformQueries.separator
            };
            for (String line : lines) {
                System.out.println(centerString(line));
            }
        }

    }
```

![Query 8](/images/quries/8.PNG)
> **Query 9**
> For each salesperson, display the number of cars that he sold and summation of their prices.
---

```java
    public void query9() {
        List<Sale> sales = service.findAllSales();
        List<Salesperson> salespeople = service.findAllSalespeople();

        salespeople.forEach(salesperson -> {
            if (salesperson.getSales().isEmpty()) {
                salesperson.setSales(sales.
                        stream()
                        .filter(sale -> sale.getId().getSalespersonId().equals(salesperson.getId()))
                        .collect(Collectors.toList()));
            }
        });

        System.out.println(PerformQueries.separator);
        System.out.println(centerString("\tQuery 9") + "\n");
        for (Salesperson salesperson : salespeople) {
            Double totalPriceOfSales = salesperson
                    .getSales()
                    .stream()
                    .map(Sale::getSalePrice)
                    .reduce(0.0d, Double::sum);

            String output = String.format("\t%s sold %d cars, with total price = %.2f",
                    salesperson.getName(),
                    salesperson.getSales().size(),
                    totalPriceOfSales);
            System.out.println(output);
        }
        System.out.println(PerformQueries.separator);


    }


```

![Query 9](/images/quries/9.PNG)
> **Query 10**
> Display the details of the cheapest car(s).
---

```java
   public void query10() {
        List<Car> cars = service.findAllCars();
        Car cheapestCar = cars.get(0);
        for (int i = 1; i < cars.size(); i++) {
            if (cars.get(i).getPrice().compareTo(cheapestCar.getPrice()) < 0) {
                cheapestCar = cars.get(i);
            }
        }

        System.out.println(PerformQueries.separator);
        System.out.println(centerString("\tQuery 10") + "\n");
        System.out.println("Cheapest car:");
        System.out.printf("\tSerial Number: %s%n", cheapestCar.getSerialNumber());
        System.out.printf("\tManufacturer: %s%n", cheapestCar.getManufacturer());
        System.out.printf("\tModel: %s%n", cheapestCar.getModel());
        System.out.printf("\tPrice: %.2f%n", cheapestCar.getPrice());
        System.out.println("Its options:");
        for (CarOption option : cheapestCar.getOptions()) {
            System.out.printf("\tOption name: %10s%n", option.getId().getOptionName());
            System.out.printf("\tPrice = %10s%n", option.getPrice());
        }
        System.out.println(PerformQueries.separator);

    }
```

![Query 10](/images/quries/10.PNG)









