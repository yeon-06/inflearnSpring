package hellojpa.shopping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ShoppingMain {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try {
            Team team = new Team("플랫폼팀");
            entityManager.persist(team);

            User user = new User("yeonlog", "연로그", team);
            entityManager.persist(user);

            entityManager.flush();
            entityManager.clear();

            User findUser = entityManager.find(User.class, user.getId());
            System.out.println(findUser);

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
