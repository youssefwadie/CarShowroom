package college.database.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public class EntityManagerProducer {
    private EntityManagerFactory factory;

    private static final EntityManagerProducer instance = new EntityManagerProducer();


    private EntityManagerProducer() {
        try {
            this.factory = Persistence.createEntityManagerFactory("CarShowroomPU");
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
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
