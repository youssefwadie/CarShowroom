package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "option_car")
public class CarOption implements Serializable {

    @EmbeddedId
    private CarOptionId id;

    @ManyToOne
    @JoinColumn(name = "car_serial_no", referencedColumnName = "Serial_no", insertable = false, updatable = false)
    private Car car;
    
    @Column(name = "Price")
    private BigDecimal price;

    public CarOptionId getId() {
        return id;
    }

    public void setId(CarOptionId id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
