package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;


@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    private Member member1;
    private Member member2;
    private Member member3;

    @BeforeEach
    void init() {
        queryFactory = new JPAQueryFactory(entityManager);

        Team team1 = new Team("team A");
        entityManager.persist(team1);
        Team team2 = new Team("team B");
        entityManager.persist(team2);

        member1 = new Member("yeonlog", 27, team1);
        member2 = new Member("logyeon", 25, team2);
        member3 = new Member("logyeon", 20, team2);
        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);
    }

    @Test
    void selectWithJpql() {
        // given
        String username = member1.getUsername();

        // when
        Member findMember = entityManager.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getSingleResult();

        // then
        assertThat(findMember.getUsername()).isEqualTo(username);
    }

    // 단건 조회
    @Test
    void selectWithQuerydsl() {
        // given
        String username = member1.getUsername();
        QMember qMember = member; // static으로 선언되어 있어서 바로 사용하면 된다.
//        QMember qMember = new QMember("m");   // 같은 테이블을 join하는 경우 선언해서 사용해야 한다.

        // when
        Member findMember = queryFactory.select(qMember)
                .from(qMember)
                .where(qMember.username.eq(username))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo(username);
    }

    // 다건 조회
    @Test
    void selectAll() {
        // given & when=
        List<Member> members = queryFactory.select(member)
                .from(member)
                .fetch();

        // then
        assertThat(members).hasSize(3);
    }

    // 페이징 예제
    @Test
    void paging() {
        // given & when
        List<Member> members = queryFactory.selectFrom(member)
                .orderBy(member.age.desc())
                .offset(1)
                .limit(2)
                .fetch();

        // then
        assertThat(members).hasSize(2);
    }

    // 집함 함수 예제 - max, min, avg, ...
    @Test
    void max() {
        // given & when
        Integer result = queryFactory.select(member.age.max())
                .from(member)
                .fetchOne();

        // then
        assertThat(result).isEqualTo(27);
    }
}
