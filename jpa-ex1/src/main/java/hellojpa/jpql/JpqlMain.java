package hellojpa.jpql;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EntityTransaction transaction = entityManager.getTransaction();

    public static void main(String[] args) {
        transaction.begin();

        try {
            insert(null,25);
            useCoalesce();
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    private static void useCoalesce() {
        String query = "select coalesce(m.name, '이름 없는 회원') from JpqlMember m";
        List<String> result = entityManager.createQuery(query, String.class).getResultList();
        printStringList(result);
    }

    private static void insert(String username, int age) {
        JpqlMember member = new JpqlMember();
        member.setName(username);
        member.setAge(age);
        entityManager.persist(member);
    }

    private static void findById(Long id) {
        TypedQuery<JpqlMember> query = entityManager.createQuery("select m from JpqlMember m where m.id = :id", JpqlMember.class);
        query.setParameter("id", id);
        JpqlMember member = query.getSingleResult();
        System.out.println("result = " + member.getName());
    }

    // 반환 타입이 명확
    private static void selectByTypedQuery() {
        TypedQuery<JpqlMember> query1 = entityManager.createQuery("select m from JpqlMember m", JpqlMember.class);
        List<JpqlMember> result1 = query1.getResultList();
        printMemberList(result1);

        TypedQuery<String> query2 = entityManager.createQuery("select m.name from JpqlMember m", String.class);
        List<String> result2 = query2.getResultList();
        printStringList(result2);
    }

    // 반환 타입이 명확하지 않음
    private static void selectByQuery() {
        List<MemberDTO> resultList = entityManager.createQuery("select new hellojpa.jpql.MemberDTO(m.name, m.age) from JpqlMember m", MemberDTO.class)
                .getResultList();

        resultList.forEach(m -> {
            System.out.println(m.getName());
            System.out.println(m.getAge());
        });
    }

    private static void selectWithPaging(int start, int cnt) {
        TypedQuery<JpqlMember> query = entityManager.createQuery("select m from JpqlMember m", JpqlMember.class)
                .setFirstResult(start)
                .setMaxResults(cnt);
        List<JpqlMember> result1 = query.getResultList();
        printMemberList(result1);
    }

    private static void printMemberList(List<JpqlMember> members) {
        System.out.println(">> start print member list");
        members.forEach(m -> {
            System.out.println(m.getName());
            System.out.println(m.getAge());
        });
    }

    private static void printStringList(List<String> list) {
        System.out.println(">> start print string list");
        list.forEach(System.out::println);
    }
}