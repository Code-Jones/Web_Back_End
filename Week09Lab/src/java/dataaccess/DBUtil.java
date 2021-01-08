package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * Class that helps us close PreparedStatements and ResultSets
 */

public class DBUtil {
    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("UserPU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
