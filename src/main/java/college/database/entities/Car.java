package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "cars")
@NamedQuery(name = Car.FIND_ALL, query = "SELECT c FROM Car c")
public class Car implements Serializable {

    public static final String FIND_ALL = "car.findAll";

    @Id
    @Column(name = "serial_no", nullable = false)
    private Integer serialNumber;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "price", nullable = true)
    private Double price;

    @OneToMany(mappedBy = "car")
    Collection<CarOption> options = new ArrayList<>();

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
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
