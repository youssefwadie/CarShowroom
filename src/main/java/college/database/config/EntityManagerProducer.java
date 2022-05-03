package college.database.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProducer {
    private final EntityManagerFactory factory;

    private static final EntityManagerProducer instance = new EntityManagerProducer();

    private EntityManagerProducer() {
        this.factory = Persistence.createEntityManagerFactory("CarShowroomPU");
    }

    // mimic DI
    public static EntityManager createEntityManager() {
        return instance.factory.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (instance.factory.isOpen()) {
            instance.factory.close();
        }
    }
}
