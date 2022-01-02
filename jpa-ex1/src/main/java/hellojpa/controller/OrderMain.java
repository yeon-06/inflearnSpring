package hellojpa.controller;

import hellojpa.domain.Member;
import hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OrderMain {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EntityTransaction transaction = entityManager.getTransaction();

    public static void main(String[] args) {
        transaction.begin();

        try {
            Team team = createTeam("teamA");
            persistMember(team, "memberA");
            persistMember(team, "memberB");

            printTeamsMemberList(team.getId());

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    private static Team createTeam(String name) {
        Team team = new Team();
        team.setName(name);
        entityManager.persist(team);
        return team;
    }

    private static void persistMember(Team team, String name) {
        Member member = new Member();
        member.setUsername(name);
        member.setTeam(team);
        entityManager.persist(member);
    }

    private static void printMemberInfo(Long memberId) {
        Member findMember = entityManager.find(Member.class, memberId);
        System.out.println("팀 id: " + findMember.getTeam().getId());
        System.out.println("팀 name: " + findMember.getTeam().getName());
        System.out.println("사용자 name: " + findMember.getUsername());
    }

    private static void initialEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }

    private static void printTeamsMemberList(Long teamId) {
        Team findTeam = entityManager.find(Team.class, teamId);
        for (Member member : findTeam.getMembers()) {
            System.out.println("id: " + member.getId());
            System.out.println("name: " + member.getUsername());
        }
    }
}
