package hellojpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello"); // 하나만 생성해 애플리케이션 전체에서 공유
        EntityManager entityManager = entityManagerFactory.createEntityManager(); // 고객의 요청이 올때마다 생성 (스레드 간 공유 X)
        EntityTransaction transaction = entityManager.getTransaction(); // 데이터의 모든 변경은 트랜잭션 내부에서 실행할 것

        transaction.begin();

        try {
            select(entityManager);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    private static void insert(EntityManager entityManager) {
        Member member = new Member(1L, "yeonLog");
        entityManager.persist(member);
    }

    private static Member selectOne(EntityManager entityManager) {
        return entityManager.find(Member.class, 1L);
    }

    private static void delete(EntityManager entityManager) {
        Member member = selectOne(entityManager);
        entityManager.remove(member);
    }

    private static void update(EntityManager entityManager) {
        Member member = selectOne(entityManager);
        member.setName("연로그");
    }

    private static List<Member> select(EntityManager entityManager) {
        return entityManager.createQuery("select m from Member as m", Member.class)
                .setFirstResult(1)
                .setMaxResults(5)
                .getResultList();
    }
}
