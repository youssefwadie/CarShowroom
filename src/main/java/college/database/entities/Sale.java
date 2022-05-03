package college.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Columns;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sale")
public class Sale implements Serializable {
    @EmbeddedId
    private SaleId id;

    @Column(name = "date")
    private Date soldDate;

    @Column(name = "sale_price")
    private BigDecimal salePrice;


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
}
