package college.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sale")
public class Sale implements Serializable {
    @EmbeddedId
    private SaleId id;

    @Column(name = "date")
    private LocalDate soldDate;

    @Column(name = "sale_price")
    private BigDecimal salePrice;


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
}
