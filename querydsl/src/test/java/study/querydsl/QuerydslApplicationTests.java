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

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @Autowired
    private EntityManager entityManager;

    private Member member1;

    @BeforeEach
    void init() {
        Team team1 = new Team("team A");
        entityManager.persist(team1);

        member1 = new Member("yeonlog", 27, team1);
        entityManager.persist(member1);
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

    @Test
    void selectWithQuerydsl() {
        // given
        String username = member1.getUsername();
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QMember qMember = QMember.member; // static으로 선언되어 있어서 바로 사용하면 된다.
//        QMember qMember = new QMember("m");   // 같은 테이블을 join하는 경우 선언해서 사용해야 한다.

        // when
        Member findMember = queryFactory.select(qMember)
                .from(qMember)
                .where(qMember.username.eq(username))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo(username);
    }
}
