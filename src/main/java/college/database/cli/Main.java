package college.database.cli;

import college.database.config.EntityManagerProducer;
import college.database.service.Queries;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.printf("%sSetup the database connection, please wait...%s%n", Colors.ANSI_GREEN, Colors.ANSI_RESET);
            Class.forName(EntityManagerProducer.class.getName());
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot load the EntityManagerFactory class");
            System.exit(1);
        }

        int choice = 0;
        Queries queries = new Queries();
        do {
            printMenu();
            choice = handleInput();
			System.out.print("\033[H\033[2J");
			System.out.flush();
			if (choice == -1) {
                System.out.printf("%sTerminating....%s%n", Colors.ANSI_GREEN, Colors.ANSI_RESET);
                EntityManagerProducer.closeEntityManagerFactory();
                continue;
            }
            switch (choice) {
                case 1:
                    queries.query1();
                    break;
                case 2:
                    queries.query2();
                    break;
                case 3:
                    queries.query3();
                    break;
                case 4:
                    queries.query4();
                    break;
                case 5:
                    queries.query5();
                    break;
                case 6:
                    queries.query6();
                    break;
                case 7:
                    queries.query7();
                    break;
                case 8:
                    queries.query8();
                    break;
                case 9:
                    queries.query9();
                    break;
                case 10:
                    queries.query10();
                    break;
                default:
                    System.out.printf("%sNot implemented yet!%s%n", Colors.ANSI_RED, Colors.ANSI_RESET);
            }

        } while (choice != -1);

        scanner.close();
    }

    private static void printMenu() {
        System.out.printf("%s1.  Display the serial number and the price of the Toyota corolla.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);

        System.out.printf("%s2.  Retrieve a model and price of each car whose has price between 150000 and 250000.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);

        System.out.printf("%s3.  Retrieve a model, price, and price after adding 7%% interest of all cars ordered from " +
                        "the most expensive car to the cheapest car.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);

        System.out.printf("%s4. Make a report named report1 (view) that contains car model, car manufacturer, car price, option\n" +
                        "name, option price and the total price of the car with each of its option.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);


        System.out.printf("%s5.  For each car model, display the total prices of its options.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);

        System.out.printf("%s6.  Retrieve the name and phone of salesperson whose name start with “s”.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);

        System.out.printf("%s7.  Retrieve the name of salesperson who has no phone.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);

        System.out.printf("%s8.  Make a report named report2 (view) that contains each car sales details " +
                        "(car model, car price, salesperson name, sales date, and sales price)%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);

        System.out.printf("%s9.  For each salesperson, display the number of cars that he sold and summation of their prices.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);
        System.out.printf("%s10. Display the details of the cheapest car(s).%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);
        System.out.printf("%sQ. Quit.%s%n",
                Colors.ANSI_PURPLE, Colors.ANSI_RESET);
    }

    private static int handleInput() {
        while (true) {
            System.out.printf("%s[%d-%d] or quit: %s", Colors.ANSI_CYAN, 1, 10, Colors.ANSI_RESET);
            String input = scanner.nextLine();
            if (input.matches("-?\\d+")) {

                try {
                    int number = Integer.parseInt(input);
                    if (number < 1 || number > 10) {
                        System.out.printf("%sOut of range: %d%s%n", Colors.ANSI_RED, number, Colors.ANSI_RESET);
                    } else {
                        return number;
                    }
                } catch (NumberFormatException ignored) {
                    System.out.printf("%sOut of range: %s%s%n", Colors.ANSI_RED, input, Colors.ANSI_RESET);
                }
            } else if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                return -1;
            } else {
                System.out.printf("%sInvalid input; expected number found, %s%s%n", Colors.ANSI_RED, input, Colors.ANSI_RESET);
            }
        }
    }
}
