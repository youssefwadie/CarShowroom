package college.database.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class CarOptionId implements Serializable {
    @Column(name = "car_serial_no")
    private String carSerialNumber;

    @Column(name = "option_name", insertable = false, updatable = false)
    private String optionName;

    public String getCarSerialNumber() {
        return carSerialNumber;
    }

    public void setCarSerialNumber(String carSerialNumber) {
        this.carSerialNumber = carSerialNumber;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
