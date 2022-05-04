package college.database.report;

import college.database.entities.Car;
import college.database.entities.CarOption;

import java.math.BigDecimal;
import java.util.Collection;

public class Report1 {
    private final String carModel;

    private final String carManufacturer;

    private final BigDecimal carPrice;

    private Collection<CarOption> carOptions;

    // JPQL Doesn't allow collections in constructor expressions
//    public Report1(String carModel, String carManufacturer, BigDecimal carPrice, Collection<Object> carOptions) {
//        this.carModel = carModel;
//        this.carManufacturer = carManufacturer;
//        this.carPrice = carPrice;
//        System.out.println("====================== type of car options ===============" + carOptions.getClass().getName());
//        this.carOptions = new ArrayList<>();
//    }

    public Report1(Car car) {
        this.carModel = car.getModel();
        this.carManufacturer = car.getManufacturer();
        this.carPrice = car.getPrice();
        this.carOptions = car.getOptions();

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();


        sb.append("Car model is ").append(carModel).append('\n')
                .append("Car Manufacturer: ").append(carManufacturer).append('\n')
                .append("Car price: ").append(carPrice).append('\n');

        if (carOptions.size() != 0) {
            sb.append("====================== Options ======================\n");
            for (CarOption option : carOptions) {
                sb.append(String.format("%s costs %.2f%n", option.getId().getOptionName(), option.getPrice()));
            }
        }
        BigDecimal totalPrice = carOptions
                .stream()
                .map(CarOption::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        sb.append(String.format("Total price = %.2f%n", totalPrice));
        sb.append("=====================================================");
        return sb.toString();
    }
}
