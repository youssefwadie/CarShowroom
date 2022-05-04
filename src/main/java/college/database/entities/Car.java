package college.database.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "car")
@NamedQuery(name = Car.GET_ALL_CARS_SORTED_BY_PRICE, query = "SELECT c FROM Car c ORDER BY c.price DESC")
@NamedQuery(name = Car.GET_CAR_BY_MANUFACTURER_AND_MODEL, query = "SELECT c FROM Car c WHERE c.model = :model AND c.manufacturer = :manufacturer")
@NamedQuery(name = Car.GET_CARS_IN_RANGE, query = "SELECT c FROM Car c WHERE c.price BETWEEN :minPrice AND :maxPrice")
@NamedQuery(name = Car.GET_ALL_CARS_WITH_THEIR_OPTIONS, query = "SELECT c FROM Car c JOIN FETCH c.options")
@NamedQuery(name = Car.GET_CHEAPEST_CAR, query = "SELECT c FROM Car c JOIN FETCH c.options WHERE c.price = (SELECT MIN(c.price) FROM Car c)")
@NamedQuery(name = Car.GET_CARS_DETAILS, query = "SELECT new college.database.report.Report1(c) FROM Car c")
public class Car implements Serializable {

    // All these queries can return multiple values in a different dataset so let's be as generic as we can
    // 1
    public static final String GET_CAR_BY_MANUFACTURER_AND_MODEL = "Car.findByManufacturerAndModel";

    // 2
    public static final String GET_CARS_IN_RANGE = "Car.getCarsInRange";
    // 3
    public static final String GET_ALL_CARS_SORTED_BY_PRICE = "Car.getAllSorted";

    // 4
    public static final String GET_CARS_DETAILS = "Car.getCarsDetails";

    // 5
    public static final String GET_ALL_CARS_WITH_THEIR_OPTIONS = "Car.getAllWithOptions";

    // 10
    public static final String GET_CHEAPEST_CAR = "Car.getCheapest";

    @Id
    @Column(name = "serial_no")
    private String serialNumber;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "car")
    Collection<CarOption> options = new ArrayList<>();

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Collection<CarOption> getOptions() {
        return options;
    }

    public void setOptions(Collection<CarOption> options) {
        this.options = options;
    }
}
