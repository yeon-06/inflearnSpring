package study.querydsl.step1;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TeaTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void select() {
        // given
        Tea tea = new Tea("웨딩 임페리얼", "마리아쥬 프레르");
        entityManager.persist(tea);

        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        QTea qTea = new QTea("test");

        // when
        Tea result = query.selectFrom(qTea)
                .fetchOne();

        // then
        assertThat(result).isEqualTo(tea);
        assertThat(result.getId()).isEqualTo(tea.getId());
    }
}