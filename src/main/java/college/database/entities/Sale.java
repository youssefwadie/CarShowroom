package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "sale")
@NamedQuery(name = Sale.GET_ALL_SALES, query = "SELECT s FROM Sale s")
public class Sale implements Serializable {

    public static final String GET_ALL_SALES = "Sale.getAll";
    @EmbeddedId
    private SaleId id;
    @Column(name = "date")
    private Date soldDate;

    @Column(name = "SalePrice")
    private BigDecimal salePrice;

    @ManyToOne
    @JoinColumn(name = "car_serial_no", referencedColumnName = "Serial_no", updatable = false, insertable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "sale_person_id", referencedColumnName = "id", updatable = false, insertable = false)
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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
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
