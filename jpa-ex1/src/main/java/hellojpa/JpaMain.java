package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EntityTransaction transaction = entityManager.getTransaction();

    public static void main(String[] args) {
        transaction.begin();

        try {
            isEquals();
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    private static void insert() {
        Member member = new Member();
        member.setId(1L);
        member.setName("helloJPA");
        entityManager.persist(member);
    }

    private static void inserts() {
        Member member = new Member();
        member.setId(1L);
        member.setName("helloJPA");

        Member member2 = new Member();
        member.setId(2L);
        member.setName("helloJPA2");

        entityManager.persist(member);
        entityManager.persist(member2);
    }

    private static void findById() {
        Member member = entityManager.find(Member.class, 1L);
        System.out.println("id: " + member.getId());
        System.out.println("name: " + member.getName());
    }

    private static void isEquals() {
        Member member1 = entityManager.find(Member.class, 1L);
        Member member2 = entityManager.find(Member.class, 1L);

        System.out.println(member1 == member2);
    }

    private static void delete() {
        Member member = entityManager.find(Member.class, 1L);
        entityManager.remove(member);
    }

    private static void update() {
        Member member = entityManager.find(Member.class, 1L);
        member.setName("byeJPA");
    }

    private static void selectAll() {
        List<Member> members = entityManager.createQuery("select m from Member as m", Member.class)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();

        for (Member member : members) {
            System.out.println("id: " + member.getId());
            System.out.println("name: " + member.getName());
        }
    }
}