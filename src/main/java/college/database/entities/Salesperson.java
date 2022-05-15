package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "salespeople")
@NamedQuery(name = Salesperson.FIND_ALL, query = "SELECT s FROM Salesperson s")
public class Salesperson implements Serializable {

    public static final String FIND_ALL = "salesperson.findAll";

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "phone")
    private String phone;

    @OneToMany
    @JoinColumn(name = "salesperson_id", referencedColumnName = "id")
    private Collection<Sale> sales = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<Sale> getSales() {
        return sales;
    }

    public void setSales(Collection<Sale> sales) {
        this.sales = sales;
    }
}
