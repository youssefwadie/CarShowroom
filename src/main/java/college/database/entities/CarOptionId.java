package college.database.entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CarOptionId implements Serializable {
    @Column(name = "car_serial_no", nullable = false)
    private Integer carSerialNumber;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    public Integer getCarSerialNumber() {
        return carSerialNumber;
    }

    public void setCarSerialNumber(Integer carSerialNumber) {
        this.carSerialNumber = carSerialNumber;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
