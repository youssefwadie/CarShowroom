package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cars_options")
@NamedQuery(name = CarOption.FIND_ALL, query = "SELECT o FROM CarOption o")
public class CarOption implements Serializable {

    public static final String FIND_ALL = "carOption.findAll";
    @EmbeddedId
    private CarOptionId id;

    @ManyToOne
    @JoinColumn(name = "car_serial_no", referencedColumnName = "serial_no", insertable = false, updatable = false)
    private Car car;
    
    @Column(name = "price", nullable = true)
    private Double price;

    public CarOptionId getId() {
        return id;
    }

    public void setId(CarOptionId id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
