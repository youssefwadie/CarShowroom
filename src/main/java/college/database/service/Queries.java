package college.database.service;

import college.database.cli.Colors;
import college.database.config.EntityManagerProducer;
import college.database.entities.Car;
import college.database.entities.CarOption;
import college.database.entities.Sale;
import college.database.entities.Salesperson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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

    public static boolean createRecord2() {
        //        String dropOldView = "DROP VIEW IF EXISTS report2";
        String createViewQuery = "CREATE VIEW IF NOT EXISTS report2 AS " +
                "SELECT c.model, c.price, sp.name, s.date, s.sale_price " +
                "FROM car c JOIN sale s ON c.serial_no = s.car_serial_no " +
                "JOIN salesperson sp ON sp.id = s.salesperson_id";

        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            try {
                transaction.begin();
//                manager.createNativeQuery(dropOldView).executeUpdate();
                manager.createNativeQuery(createViewQuery).executeUpdate();
                transaction.commit();
                return true;

            } catch (RollbackException e) {
                System.out.printf("%sFailed to create the view: %s%s%n", Colors.ANSI_RED, e.getMessage(), Colors.ANSI_RESET);
                transaction.rollback();
                return false;
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }


    // TODO: print the rest of data
    public static void queryRecord2() {
        EntityManager manager = EntityManagerProducer.createEntityManager();

        try {
            List<Object[]> resultList = manager
                    .createNativeQuery("SELECT * FROM report2")
                    .getResultList();
            for (Object[] result : resultList) {
                String model = (String) result[0];
                BigDecimal price = BigDecimal.valueOf((Double) result[1]);
                String salespersonName = (String) result[2];
                LocalDate saleDate = ((Date) result[3]).toLocalDate();
                BigDecimal salePrice = BigDecimal.valueOf((Double) result[4]);

                System.out.printf("Model: %s, price = %.2f %n", model, price);
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
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
