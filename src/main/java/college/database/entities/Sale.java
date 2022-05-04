package college.database.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sale")
@NamedQuery(name = Sale.GET_ALL_SALES, query = "SELECT s FROM Sale s")
public class Sale implements Serializable {

    public static final String GET_ALL_SALES = "Sale.getAll";
    @EmbeddedId
    private SaleId id;
    @Column(name = "date")
    private LocalDate soldDate;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

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

    public LocalDate getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(LocalDate soldDate) {
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
