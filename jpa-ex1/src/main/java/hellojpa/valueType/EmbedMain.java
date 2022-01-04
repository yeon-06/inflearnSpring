package hellojpa.valueType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class EmbedMain {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EntityTransaction transaction = entityManager.getTransaction();

    public static void main(String[] args) {
        transaction.begin();

        try {
            EmbedMember embedMember = new EmbedMember();
            embedMember.setName("yeonLog");
            embedMember.setHomeAddress(new Address("성남시", "ㅇㅇ로", "XXXXX"));
            embedMember.setPeriod(new Period(LocalDateTime.now(), LocalDateTime.MAX));

            embedMember.addFavoriteFoods("치킨");
            embedMember.addFavoriteFoods("피자");

            entityManager.persist(embedMember);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();

        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
