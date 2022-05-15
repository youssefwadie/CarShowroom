package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "sales")
@NamedQuery(name = Sale.GET_ALL_SALES, query = "SELECT s FROM Sale s")
public class Sale implements Serializable {

    public static final String GET_ALL_SALES = "Sale.getAll";
    @EmbeddedId
    private SaleId id;
    @Column(name = "date")
    private Date soldDate;

    @Column(name = "sale_price")
    private Double salePrice;

    @ManyToOne
    @JoinColumn(name = "car_serial_no", referencedColumnName = "serial_no", updatable = false, insertable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "salesperson_id", referencedColumnName = "id", updatable = false, insertable = false)
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
