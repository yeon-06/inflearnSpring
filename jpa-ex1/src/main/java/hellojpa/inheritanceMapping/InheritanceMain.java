package hellojpa.inheritanceMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class InheritanceMain {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EntityTransaction transaction = entityManager.getTransaction();

    public static void main(String[] args) {
        transaction.begin();

        try {
            Movie movie = new Movie();
            movie.setDirector("A");
            movie.setActor("bb");
            movie.setName("000");
            movie.setPrice(1000);
            movie.setCreatedBy("yeonLog");
            movie.setModifiedBy("yeonLog");
            movie.setCreatedDate(LocalDateTime.now());
            movie.setModifiedDate(LocalDateTime.now());

            entityManager.persist(movie);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}