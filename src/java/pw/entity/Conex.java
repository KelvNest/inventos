package pw.entity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author seront
 */
public class Conex {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEmf() {
        if (emf != null) {
            return emf;
        } else {
            emf = Persistence.createEntityManagerFactory("PeliculaWebPU");
            return emf;
        }
    }

}
