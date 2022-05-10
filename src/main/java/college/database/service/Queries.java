package college.database.service;

import college.database.config.EntityManagerProducer;
import college.database.entities.Car;
import college.database.entities.CarOption;
import college.database.entities.Sale;
import college.database.entities.Salesperson;
import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class Queries {

    private final static int windowWidth = 50;

    // For java 11+, use " ".repeat(X)
    private final static String separator = String.join("", Collections.nCopies(windowWidth, "="));


    public static void query1() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            Car toyotaCorolla = manager.createNamedQuery(Car.GET_CAR_BY_MANUFACTURER_AND_MODEL, Car.class)
                    .setParameter("manufacturer", "Toyota")
                    .setParameter("model", "Corolla")
                    .getSingleResult();

            System.out.printf("%s %s's serial number is %s, and its price = %s%n",
                    toyotaCorolla.getManufacturer(), toyotaCorolla.getModel(),
                    toyotaCorolla.getSerialNumber(), toyotaCorolla.getPrice().toString());

        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query2() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Car> carsBetween = manager.createNamedQuery(Car.GET_CARS_IN_RANGE, Car.class)
                    .setParameter("minPrice", 150000)
                    .setParameter("maxPrice", 250000)
                    .getResultList();

            for (Car car : carsBetween) {
                System.out.printf("Car's model is %s and its price = %.2f%n", car.getModel(), car.getPrice());
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query3() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            BigDecimal interest = BigDecimal.valueOf(1.07d);

            List<Car> cars = manager.createNamedQuery(Car.GET_ALL_CARS_SORTED_BY_PRICE, Car.class).getResultList();
            for (Car car : cars) {
                System.out.printf("Car's model = %s, price = %.2f, and price after adding interest = %.2f%n"
                        , car.getModel(), car.getPrice(), car.getPrice().multiply(interest));
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query4() {
        EntityManager manager = EntityManagerProducer.createEntityManager();

        try {
            List<Car> cars = manager
                    .createNamedQuery(Car.GET_ALL_CARS_WITH_THEIR_OPTIONS, Car.class)
                    .getResultList();

            System.out.println(Queries.separator);
            System.out.println(centerString("Report 1"));
            System.out.println(Queries.separator);

            for (Car car : cars) {
                BigDecimal totalPrice = car.getPrice();
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
                        totalPrice = totalPrice.add(option.getPrice());
                        System.out.println(centerString(optionName));
                        System.out.println(centerString(optionPrice));
                    }
                }
                System.out.println(centerString(String.format("Total price: %.2f", totalPrice)));

                System.out.println(separator);
            }

        } finally {
            if (manager != null) {
                manager.close();
            }
        }

    }


    public static void query5() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Car> cars = manager.createNamedQuery(Car.GET_ALL_CARS_WITH_THEIR_OPTIONS, Car.class)
                    .getResultList();
            for (Car car : cars) {
                BigDecimal optionsPrice = BigDecimal.ZERO;
                for (CarOption option : car.getOptions()) {
                    optionsPrice = optionsPrice.add(option.getPrice());
                }
                System.out.printf("Car's model = %s, its options price = %.2f%n", car.getModel(), optionsPrice);
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query6() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Salesperson> salespeopleWithPrefix = manager.createNamedQuery(Salesperson.GET_SALESPEOPLE_WITH_NAME_STARTING_WITH, Salesperson.class)
                    .setParameter("prefix", "s")
                    .getResultList();
            for (Salesperson salesPerson : salespeopleWithPrefix) {
                System.out.printf("Sales Person: %s, phone: %s%n", salesPerson.getName(), salesPerson.getPhone());
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query7() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Salesperson> salesPeople = manager.createNamedQuery(Salesperson.GET_SALESPEOPLE_WITH_NO_PHONE, Salesperson.class)
                    .getResultList();
            for (Salesperson salesPerson : salesPeople) {
                System.out.printf("%s is salesperson with no number%n", salesPerson.getName());
            }

        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query8() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Sale> sales = manager.createNamedQuery(Sale.GET_ALL_SALES, Sale.class).getResultList();
            System.out.println(Queries.separator);
            System.out.println(centerString("Report 2"));
            System.out.println(Queries.separator);
            for (Sale sale : sales) {
                String[] lines = new String[]{
                        String.format("Car model: %s", sale.getCar().getModel()),
                        String.format("Car price: %.2f", sale.getCar().getPrice()),
                        String.format("Salesperson's name: %s", sale.getSalesperson().getName()),
                        String.format("Sales date: %s", sale.getSoldDate().toString()),
                        String.format("Sale price: %.2f", sale.getSalePrice()),
                        Queries.separator
                };
                for (String line : lines) {
                    System.out.println(centerString(line));
                }
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    private static String centerString(String stringToBeCentered) {
        if (windowWidth > stringToBeCentered.length()) {
            String padding = String.join("",
                    Collections.nCopies((windowWidth - stringToBeCentered.length()) / 2, " "));
            return padding + stringToBeCentered;
        }

        return stringToBeCentered;
    }

    public static void query9() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Salesperson> salesPeople =
                    manager.createNamedQuery(Salesperson.GET_SALESPEOPLE_WITH_ALL_THEIR_SALES, Salesperson.class)
                            .getResultList();
            for (Salesperson salesPerson : salesPeople) {
                BigDecimal totalPriceOfSales = salesPerson
                        .getSales()
                        .stream()
                        .map(Sale::getSalePrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                System.out.printf("%s sold %d cars, with total price = %.2f%n",
                        salesPerson.getName(), salesPerson.getSales().size()
                        , totalPriceOfSales);
            }

        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }


    public static void query10() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            Car cheapestCar = manager.createNamedQuery(Car.GET_CHEAPEST_CAR, Car.class)
                    .getSingleResult();

            System.out.println("Cheapest car");
            System.out.printf("Serial Number: %s%n", cheapestCar.getSerialNumber());
            System.out.printf("Manufacturer: %s%n", cheapestCar.getManufacturer());
            System.out.printf("Model: %s%n", cheapestCar.getModel());
            System.out.println("Its options:");
            for (CarOption option : cheapestCar.getOptions()) {
                System.out.printf("\tOption name: %10s%n", option.getId().getOptionName());
                System.out.printf("\tPrice = %10s%n", option.getPrice());
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

//    public static void queryX() {
//        EntityManager manager = Producer.createEntityManager();
//        try {
//
//        } finally {
//            if (manager != null) {
//                manager.close();
//            }
//        }
//    }
}
