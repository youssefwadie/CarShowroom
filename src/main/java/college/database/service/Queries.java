package college.database.service;

import college.database.config.EntityManagerProducer;
import college.database.entities.Car;
import college.database.entities.CarOption;
import college.database.entities.Sale;
import college.database.entities.Salesperson;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

public class Queries {

    private final static int windowWidth = 80;

    // For java 11+, use " ".repeat(X)
    private final static String separator = String.join("", Collections.nCopies(windowWidth, "="));


    public static void query1() {
        // @NamedQuery(name = Car.GET_CAR_BY_MANUFACTURER_AND_MODEL,
        // query = "SELECT c FROM Car c WHERE c.model = :model AND c.manufacturer = :manufacturer")
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            Car toyotaCorolla = manager.createNamedQuery(Car.GET_CAR_BY_MANUFACTURER_AND_MODEL, Car.class)
                    .setParameter("manufacturer", "Toyota")
                    .setParameter("model", "Corolla")
                    .getSingleResult();
            System.out.println(Queries.separator);
            System.out.println(centerString("\tQuery 1") + "\n");
            System.out.printf("\t%s %s's serial number is %s, and its price = %s%n",
                    toyotaCorolla.getManufacturer(), toyotaCorolla.getModel(),
                    toyotaCorolla.getSerialNumber(), toyotaCorolla.getPrice().toString());
            System.out.println("\n" + Queries.separator);

        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query2() {
        // @NamedQuery(name = Car.GET_CARS_IN_RANGE,
        // query = "SELECT c FROM Car c WHERE c.price BETWEEN :minPrice AND :maxPrice")
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Car> carsBetween = manager.createNamedQuery(Car.GET_CARS_IN_RANGE, Car.class)
                    .setParameter("minPrice", 150000)
                    .setParameter("maxPrice", 250000)
                    .getResultList();
            System.out.println(Queries.separator);
            System.out.println(centerString("\tQuery 2") + "\n");
            for (Car car : carsBetween) {

                System.out.printf("\tCar's model is %s and its price = %.2f%n", car.getModel(), car.getPrice());
            }
            System.out.println(Queries.separator);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query3() {
        // @NamedQuery(name = Car.GET_ALL_CARS_SORTED_BY_PRICE,
        // query = "SELECT c FROM Car c ORDER BY c.price DESC")
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            Double afterInterest = 1.07d;

            List<Car> cars = manager.createNamedQuery(Car.GET_ALL_CARS_SORTED_BY_PRICE, Car.class).getResultList();
            System.out.println(Queries.separator);
            System.out.println(centerString("\tQuery 3") + "\n");
            for (Car car : cars) {

                System.out.printf("\tCar's model = %s, price = %.2f, and price after adding interest = %.2f%n"
                        , car.getModel(), car.getPrice(), car.getPrice() * afterInterest);
            }
            System.out.println("\n" + Queries.separator);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query4() {
        EntityManager manager = EntityManagerProducer.createEntityManager();

        try {
            // @NamedQuery(name = Car.GET_ALL_CARS_WITH_THEIR_OPTIONS,
            // query = "SELECT c FROM Car c JOIN FETCH c.options")
            List<Car> cars = manager
                    .createNamedQuery(Car.GET_ALL_CARS_WITH_THEIR_OPTIONS, Car.class)
                    .getResultList();

            System.out.println(Queries.separator);
            System.out.println(centerString("Report 1"));
            System.out.println(Queries.separator);

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

        } finally {
            if (manager != null) {
                manager.close();
            }
        }

    }


    public static void query5() {
        // @NamedQuery(name = Car.GET_ALL_CARS_WITH_THEIR_OPTIONS,
        // query = "SELECT DISTINCT c FROM Car c LEFT JOIN FETCH c.options"),
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Car> cars = manager.createNamedQuery(Car.GET_ALL_CARS_WITH_THEIR_OPTIONS, Car.class)
                    .getResultList();
            System.out.println(Queries.separator);
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
            System.out.println(Queries.separator);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query6() {
        // @NamedQuery(name = Salesperson.GET_SALESPEOPLE_WITH_NAME_STARTING_WITH,
        // query = "SELECT s FROM Salesperson s WHERE s.name LIKE CONCAT(:prefix, '%')"),
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Salesperson> salespeopleWithPrefix = manager.createNamedQuery(Salesperson.GET_SALESPEOPLE_WITH_NAME_STARTING_WITH, Salesperson.class)
                    .setParameter("prefix", "s")
                    .getResultList();
            System.out.println(Queries.separator);
            System.out.println(centerString("\tQuery 6") + "\n");
            for (Salesperson salesPerson : salespeopleWithPrefix) {
                System.out.printf("\tSales Person: %s, phone: %s%n", salesPerson.getName(), salesPerson.getPhone());
            }
            System.out.println(Queries.separator);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query7() {
        // @NamedQuery(name = Salesperson.GET_SALESPEOPLE_WITH_NO_PHONE,
        // query = "SELECT s FROM Salesperson s WHERE s.phone = null ")

        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Salesperson> salesPeople = manager.createNamedQuery(Salesperson.GET_SALESPEOPLE_WITH_NO_PHONE, Salesperson.class)
                    .getResultList();
            System.out.println(Queries.separator);
            System.out.println(centerString("\tQuery 7") + "\n");
            for (Salesperson salesPerson : salesPeople) {
                System.out.printf("\t%s is salesperson with no number%n", salesPerson.getName());
            }
            System.out.println(Queries.separator);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public static void query8() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            // @NamedQuery(name = Sale.GET_ALL_SALES,
            // query = "SELECT s FROM Sale s")

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


    public static void query9() {
        // @NamedQuery(name = Salesperson.GET_SALESPEOPLE_WITH_ALL_THEIR_SALES,
        // query = "SELECT DISTINCT s FROM Salesperson s JOIN FETCH s.sales")

        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Salesperson> salesPeople =
                    manager.createNamedQuery(Salesperson.GET_SALESPEOPLE_WITH_ALL_THEIR_SALES, Salesperson.class)
                            .getResultList();
            System.out.println(Queries.separator);
            System.out.println(centerString("\tQuery 9") + "\n");
            for (Salesperson salesPerson : salesPeople) {
                Double totalPriceOfSales = salesPerson
                        .getSales()
                        .stream()
                        .map(Sale::getSalePrice)
                        .reduce(0.0d, Double::sum);

                String output = String.format("\t%s sold %d cars, with total price = %.2f",
                        salesPerson.getName(),
                        salesPerson.getSales().size(),
                        totalPriceOfSales);
                System.out.println(output);
            }
            System.out.println(Queries.separator);

        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }


    public static void query10() {

        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            List<Car> cars = manager
                    .createNamedQuery(Car.GET_ALL_CARS_WITH_THEIR_OPTIONS, Car.class).getResultList();

            Car cheapestCar = cars.get(0);
            for (int i = 1; i < cars.size(); i++) {
                if (cars.get(i).getPrice().compareTo(cheapestCar.getPrice()) < 0) {
                    cheapestCar = cars.get(i);
                }
            }

            System.out.println(Queries.separator);
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
            System.out.println(Queries.separator);
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
}
