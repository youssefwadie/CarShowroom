package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "cars")
@NamedQueries({
        @NamedQuery(name = Car.GET_ALL_CARS_SORTED_BY_PRICE, query = "SELECT c FROM Car c ORDER BY c.price DESC"),
        @NamedQuery(name = Car.GET_CAR_BY_MANUFACTURER_AND_MODEL, query = "SELECT c FROM Car c WHERE c.model = :model AND c.manufacturer = :manufacturer"),
        @NamedQuery(name = Car.GET_CARS_IN_RANGE, query = "SELECT c FROM Car c WHERE c.price BETWEEN :minPrice AND :maxPrice"),
        @NamedQuery(name = Car.GET_ALL_CARS_WITH_THEIR_OPTIONS, query = "SELECT DISTINCT c FROM Car c LEFT JOIN FETCH c.options"),
})
public class Car implements Serializable {

    // All these queries can return multiple values in a different dataset so let's be as generic as we can
    // 1
    public static final String GET_CAR_BY_MANUFACTURER_AND_MODEL = "Car.findByManufacturerAndModel";

    // 2
    public static final String GET_CARS_IN_RANGE = "Car.findCarsWithPriceInRange";
    // 3
    public static final String GET_ALL_CARS_SORTED_BY_PRICE = "Car.findAllSorted";

    // 4...

    // 5
    public static final String GET_ALL_CARS_WITH_THEIR_OPTIONS = "Car.findAllWithOptions";

    @Id
    @Column(name = "serial_no")
    private String serialNumber;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Collection<CarOption> getOptions() {
        return options;
    }

    public void setOptions(Collection<CarOption> options) {
        this.options = options;
    }
}
