package hellojpa.shopping;

import java.util.List;
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
            Team team = createTeam(entityManager);
            createUser(entityManager, team);
            createUser(entityManager, team);

            clear(entityManager);

            Team findTeam = entityManager.find(Team.class, team.getId());
            printList(findTeam.getUsers());

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    private static Team createTeam(EntityManager entityManager) {
        Team team = new Team("플랫폼팀");
        entityManager.persist(team);
        return team;
    }

    private static User createUser(EntityManager entityManager, Team team) {
        User user = new User("yeonlog", "연로그", team);
        entityManager.persist(user);
        return user;
    }

    private static void clear(EntityManager entityManager) {
        entityManager.flush();
        entityManager.clear();
    }

    private static <T> void printList(List<T> list) {
        list.forEach(System.out::println);
    }
}
