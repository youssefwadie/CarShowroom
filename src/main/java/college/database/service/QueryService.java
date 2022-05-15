package college.database.service;

import college.database.config.EntityManagerProducer;
import college.database.entities.Car;
import college.database.entities.CarOption;
import college.database.entities.Sale;
import college.database.entities.Salesperson;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import java.util.List;


@NamedQuery(name = Sale.FIND_ALL, query = "SELECT s FROM Sale s")
public class QueryService {


    public List<Car> findAllCars() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            return manager.createNamedQuery(Car.FIND_ALL, Car.class).getResultList();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }
    }

    public List<CarOption> findAllOptions() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            return manager.createNamedQuery(CarOption.FIND_ALL, CarOption.class).getResultList();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }
    }

    public List<Salesperson> findAllSalespeople() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            return manager.createNamedQuery(Salesperson.FIND_ALL, Salesperson.class).getResultList();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }
    }

    public List<Sale> findAllSales() {
        EntityManager manager = EntityManagerProducer.createEntityManager();
        try {
            return manager
                    .createNamedQuery(Sale.FIND_ALL, Sale.class)
                    .getResultList();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }
    }
}
