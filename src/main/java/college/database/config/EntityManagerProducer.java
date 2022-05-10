package college.database.config;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

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
