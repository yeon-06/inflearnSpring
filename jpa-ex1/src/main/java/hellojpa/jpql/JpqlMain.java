package hellojpa.jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpqlMain {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EntityTransaction transaction = entityManager.getTransaction();

    public static void main(String[] args) {
        transaction.begin();

        try {
            updateMemberAge(26, 25);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    private static void updateMemberAge(int criteriaAge, int changeAge) {
        String query = "update JpqlMember m set m.age = :changeAge where m.age = :criteriaAge";
        int result = entityManager.createQuery(query)
                        .setParameter("criteriaAge", criteriaAge)
                        .setParameter("changeAge", changeAge)
                        .executeUpdate();
        System.out.println(result);
    }

    private static void selectByNamedQuery(String username) {
        List<JpqlMember> result1 = entityManager.createNamedQuery("JpqlMember.findByUsername", JpqlMember.class)
                .setParameter("name", username)
                .getResultList();
        printMemberList(result1);
    }

    private static void selectByFetchJoin() {
        String query1 = "select m from JpqlMember m join fetch m.team";
        List<JpqlMember> result1 = entityManager.createQuery(query1, JpqlMember.class).getResultList();
        printMemberList(result1);

        String query2 = "select t from JpqlTeam t join fetch t.members";
        List<JpqlTeam> result2 = entityManager.createQuery(query2, JpqlTeam.class).getResultList();
        result2.forEach(System.out::println);
    }

    private static void initialEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }

    // 컬렉션 값 연관 경로 - 묵시적 내부 조인
    private static void selectMembersFromTeam() {
        // Collection 타입으로 반환되기 때문에 t.members.name 같은 호출 불가
        String query = "select t.members from JpqlTeam t";
        Collection result = entityManager.createQuery(query, Collection.class).getResultList();

        System.out.println(">> selectMembersFromTeam() 결과 출력");
        for (Object o : result) {
            System.out.println(o.toString());
        }
    }

    // 단일 값 연관 경로 - 묵시적 내부 조인
    private static void selectTeamFromMember() {
        String query = "select m.team.name from JpqlMember m";
        List<String> result = entityManager.createQuery(query, String.class).getResultList();
        printStringList(result);
    }

    private static void useMyDialect() {
        String query = "select function('group_concat', m.name) from JpqlMember m";
        List<String> result = entityManager.createQuery(query, String.class).getResultList();
        printStringList(result);
    }

    private static void useCoalesce() {
        String query = "select coalesce(m.name, '이름 없는 회원') from JpqlMember m";
        List<String> result = entityManager.createQuery(query, String.class).getResultList();
        printStringList(result);
    }

    private static void useNullIf() {
        String query = "select NULLIF(m.name, '이름 없는 회원') from JpqlMember m";
        List<String> result = entityManager.createQuery(query, String.class).getResultList();
        printStringList(result);
    }

    private static void insert(String username, int age) {
        JpqlTeam team = new JpqlTeam();
        team.setName("team1");
        entityManager.persist(team);

        JpqlMember member = new JpqlMember();
        member.setName(username);
        member.setAge(age);
        member.setTeam(team);
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