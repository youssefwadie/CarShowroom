package college.database.service;

import college.database.cli.Colors;
import college.database.entities.Car;
import college.database.entities.CarOption;
import college.database.entities.Sale;
import college.database.entities.Salesperson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Queries {

    private final static int windowWidth = 80;

    // For java 11+, use " ".repeat(X)
    private final static String separator = String.join("", Collections.nCopies(windowWidth, "="));

    private final QueryService service;

    public Queries() {
        service = new QueryService();
    }

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

        System.out.println(Queries.separator);
        System.out.println(centerString("\tQuery 1") + "\n");
        System.out.printf("\t%s %s's serial number is %s, and its price = %s%n",
                toyotaCorolla.getManufacturer(), toyotaCorolla.getModel(),
                toyotaCorolla.getSerialNumber(), toyotaCorolla.getPrice().toString());
        System.out.println("\n" + Queries.separator);
    }

    public void query2() {
        List<Car> cars = service.findAllCars();
        List<Car> filteredCars = cars
                .stream()
                .filter(car -> car.getPrice() >= 150_000.0 && car.getPrice() <= 250_000)
                .collect(Collectors.toList());

        System.out.println(Queries.separator);
        System.out.println(centerString("\tQuery 2") + "\n");
        for (Car car : filteredCars) {
            System.out.printf("\tCar's model is %s and its price = %.2f%n", car.getModel(), car.getPrice());
        }
        System.out.println(Queries.separator);
    }

    public void query3() {
        Double afterInterest = 1.07d;
        List<Car> cars = service.findAllCars();
        cars.sort(Comparator.comparing(Car::getPrice));
        System.out.println(Queries.separator);
        System.out.println(centerString("\tQuery 3") + "\n");
        for (Car car : cars) {
            System.out.printf("\tCar's model = %s, price = %.2f, and price after adding interest = %.2f%n"
                    , car.getModel(), car.getPrice(), car.getPrice() * afterInterest);
        }
        System.out.println("\n" + Queries.separator);
    }

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
    }


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
    }


    public void query6() {
        List<Salesperson> salespeople = service.findAllSalespeople();
        List<Salesperson> salespeopleWithPrefix = salespeople
                .stream().filter(s -> s.getName().startsWith("s") || s.getName().startsWith("S"))
                .collect(Collectors.toList());

        System.out.println(Queries.separator);
        System.out.println(centerString("\tQuery 6") + "\n");
        for (Salesperson salesPerson : salespeopleWithPrefix) {
            System.out.printf("\tSales Person: %s, phone: %s%n", salesPerson.getName(), salesPerson.getPhone());
        }
        System.out.println(Queries.separator);
    }

    public void query7() {
        List<Salesperson> salespeopleWithNoNumber = service.findAllSalespeople()
                .stream()
                .filter(s -> s.getPhone().isEmpty()).collect(Collectors.toList());
        System.out.println(Queries.separator);
        System.out.println(centerString("\tQuery 7") + "\n");
        for (Salesperson salesPerson : salespeopleWithNoNumber) {
            System.out.printf("\t%s is salesperson with no number%n", salesPerson.getName());
        }
        System.out.println(Queries.separator);
    }


    public void query8() {
        List<Sale> sales = service.findAllSales();

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

    }


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

        System.out.println(Queries.separator);
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
        System.out.println(Queries.separator);


    }

    public void query10() {
        List<Car> cars = service.findAllCars();
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
