package college.database.entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SaleId implements Serializable {
    @Column(name = "salesperson_id", nullable = false)
    private String salespersonId;


    @Column(name = "car_serial_no", nullable = false)
    private Integer carSerial;

    public String getSalespersonId() {
        return salespersonId;
    }

    public void setSalespersonId(String salespersonId) {
        this.salespersonId = salespersonId;
    }

    public Integer getCarSerial() {
        return carSerial;
    }

    public void setCarSerial(Integer carSerial) {
        this.carSerial = carSerial;
    }
}
