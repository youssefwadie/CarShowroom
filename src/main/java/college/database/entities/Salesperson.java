package college.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "salespeople")
@NamedQueries({
        @NamedQuery(name = Salesperson.GET_SALESPEOPLE_WITH_NAME_STARTING_WITH, query = "SELECT s FROM Salesperson s WHERE s.name LIKE CONCAT(:prefix, '%')"),
        @NamedQuery(name = Salesperson.GET_SALESPEOPLE_WITH_NO_PHONE, query = "SELECT s FROM Salesperson s WHERE s.phone = ''"),
        @NamedQuery(name = Salesperson.GET_SALESPEOPLE_WITH_ALL_THEIR_SALES, query = "SELECT DISTINCT s FROM Salesperson s JOIN FETCH s.sales")
})
public class Salesperson implements Serializable {

    // All these queries can return multiple values in a different dataset so let's be as generic as we can

    // 6
    public static final String GET_SALESPEOPLE_WITH_NAME_STARTING_WITH = "Salesperson.getAllByNamePrefix";

    // 7
    public static final String GET_SALESPEOPLE_WITH_NO_PHONE = "Salesperson.getSalespeopleWithNoPhone";

    // 9
    public static final String GET_SALESPEOPLE_WITH_ALL_THEIR_SALES = "Salesperson.getAllWithSales";

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
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
