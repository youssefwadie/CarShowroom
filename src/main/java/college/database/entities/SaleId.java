package college.database.entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SaleId implements Serializable {
    @Column(name = "salesperson_id")

    private String salespersonId;


    @Column(name = "car_serial_no")
    private String carSerial;

    public String getSalespersonId() {
        return salespersonId;
    }

    public void setSalespersonId(String salespersonId) {
        this.salespersonId = salespersonId;
    }

    public String getCarSerial() {
        return carSerial;
    }

    public void setCarSerial(String carSerial) {
        this.carSerial = carSerial;
    }
}
