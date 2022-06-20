package hellojpa;

import hellojpa.domain.Visitor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        // 하나만 생성해 애플리케이션 전체에서 공유
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        // 고객의 요청이 올때마다 생성 (스레드 간 공유 X)
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 데이터의 모든 변경은 트랜잭션 내부에서 실행할 것
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try {
            insert(entityManager);
            select(entityManager);

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    private static void insert(EntityManager entityManager) {
        Visitor visitor = new Visitor("yeonLog", "연로그");
        entityManager.persist(visitor);
    }

    private static Visitor selectOne(EntityManager entityManager) {
        return entityManager.find(Visitor.class, 1L);
    }

    private static void delete(EntityManager entityManager) {
        Visitor visitor = selectOne(entityManager);
        entityManager.remove(visitor);
    }

    private static void update(EntityManager entityManager) {
        Visitor visitor = selectOne(entityManager);
        visitor.setName("연로그");
    }

    private static List<Visitor> select(EntityManager entityManager) {
        return entityManager.createQuery("select v from Visitor as v", Visitor.class)
                .setFirstResult(1)
                .setMaxResults(5)
                .getResultList();
    }
}
