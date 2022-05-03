package college.database;

import college.database.service.Queries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: query 4 and 8 (views)
public class Test {
    public static void main(String[] args) {

        // change all logging levels to error level
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.SEVERE);


        try {
            Class<?> queriesClass = Class.forName(Queries.class.getName());
            Method[] methods = queriesClass.getDeclaredMethods();

            for (Method method : methods) {
                String methodName = method.getName();

                // title == capitalize first character
                methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

                System.out.println(methodName);
                System.out.println("========================================================================");
                method.invoke(null);
                System.out.println("========================================================================\n");
            }
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }
}
