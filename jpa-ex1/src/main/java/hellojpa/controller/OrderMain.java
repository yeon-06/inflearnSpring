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
            // team 생성
            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            // member 생성
            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            entityManager.persist(member);

            // 영속성 컨텍스트 초기화
            entityManager.flush();
            entityManager.clear();

            // Member 검색
            Member findMember = entityManager.find(Member.class, member.getId());
            System.out.println("팀 id: " + findMember.getTeam().getId());
            System.out.println("팀 name: " + findMember.getTeam().getName());
            System.out.println("사용자 name: " + findMember.getUsername());

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
