package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "sales")
@NamedQuery(name = Sale.FIND_ALL, query = "SELECT s FROM Sale s")
public class Sale implements Serializable {

    public static final String FIND_ALL = "Sale.getAll";
    @EmbeddedId
    private SaleId id;
    @Column(name = "date", nullable = false)
    private Date soldDate;

    @Column(name = "sale_price", nullable = false)
    private Double salePrice;

    @ManyToOne
    @JoinColumn(name = "car_serial_no", referencedColumnName = "serial_no", updatable = false, insertable = false, nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "salesperson_id", referencedColumnName = "id", updatable = false, insertable = false, nullable = false)
    private Salesperson salesperson;

    public SaleId getId() {
        return id;
    }

    public void setId(SaleId id) {
        this.id = id;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Salesperson getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }
}
