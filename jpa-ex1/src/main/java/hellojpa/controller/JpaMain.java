package hellojpa.controller;

import hellojpa.domain.MemberTemp;

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
        MemberTemp member = new MemberTemp();
        member.setId(1L);
        member.setName("helloJPA");
        entityManager.persist(member);
    }

    private static void inserts() {
        MemberTemp member = new MemberTemp();
        member.setId(1L);
        member.setName("helloJPA");

        MemberTemp member2 = new MemberTemp();
        member.setId(2L);
        member.setName("helloJPA2");

        entityManager.persist(member);
        entityManager.persist(member2);
    }

    private static void findById() {
        MemberTemp member = entityManager.find(MemberTemp.class, 1L);
        System.out.println("id: " + member.getId());
        System.out.println("name: " + member.getName());
    }

    private static void isEquals() {
        MemberTemp member1 = entityManager.find(MemberTemp.class, 1L);
        MemberTemp member2 = entityManager.find(MemberTemp.class, 1L);

        System.out.println(member1 == member2);
    }

    private static void delete() {
        MemberTemp member = entityManager.find(MemberTemp.class, 1L);
        entityManager.remove(member);
    }

    private static void update() {
        MemberTemp member = entityManager.find(MemberTemp.class, 1L);
        member.setName("byeJPA");
    }

    private static void selectAll() {
        List<MemberTemp> members = entityManager.createQuery("select m from Member as m", MemberTemp.class)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();

        for (MemberTemp member : members) {
            System.out.println("id: " + member.getId());
            System.out.println("name: " + member.getName());
        }
    }
}