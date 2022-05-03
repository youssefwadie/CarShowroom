package college.database.service;

import college.database.config.EntityManagerProducer;
import college.database.entities.Car;
import college.database.entities.CarOption;
import college.database.entities.Sale;
import college.database.entities.SalesPerson;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class Queries {
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
            BigDecimal interest = new BigDecimal(1.07d);

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

    // Query 4 ==> native query (create record)


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
                System.out.printf("Car's model = %s%n", car.getModel());
                System.out.printf("Its options price = %.2f%n", optionsPrice);
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
            List<SalesPerson> salespeopleWithPrefix = manager.createNamedQuery(SalesPerson.GET_SALESPEOPLE_WITH_NAME_STARTING_WITH, SalesPerson.class)
                    .setParameter("prefix", "s")
                    .getResultList();
            for (SalesPerson salesPerson : salespeopleWithPrefix) {
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
            List<SalesPerson> salesPeople = manager.createNamedQuery(SalesPerson.GET_SALESPEOPLE_WITH_NO_PHONE, SalesPerson.class)
                    .getResultList();
            for (SalesPerson salesPerson : salesPeople) {
                System.out.printf("%s is salesperson with no number%n", salesPerson.getName());
            }

        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }


    // Query 8 ==> native query (create record)

    public static void query9() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<SalesPerson> salesPeople =
                    manager.createNamedQuery(SalesPerson.GET_SALESPEOPLE_WITH_ALL_THEIR_SALES, SalesPerson.class)
                            .getResultList();
            for (SalesPerson salesPerson : salesPeople) {
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
