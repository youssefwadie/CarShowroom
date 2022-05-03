package college.database.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Producer {
    private final EntityManagerFactory factory;

    private static final Producer instance = new Producer();

    private Producer() {
        this.factory = Persistence.createEntityManagerFactory("CarShowroomPU");
    }

    // mimic DI
    public static EntityManager createEntityManager() {
        return instance.factory.createEntityManager();
    }

}
