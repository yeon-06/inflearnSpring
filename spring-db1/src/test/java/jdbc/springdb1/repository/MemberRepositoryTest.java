package jdbc.springdb1.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import jdbc.springdb1.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class MemberRepositoryTest {

    private final MemberRepository memberRepository = new MemberRepository();

    @DisplayName("등록 확인")
    @Order(1)
    @Test
    void insert() {
        Member member = new Member("yeonLog", 10000);
        assertDoesNotThrow(() -> memberRepository.save(member));
    }

    @DisplayName("조회")
    @Order(2)
    @Test
    void select() {
        Member member = new Member("yeonLog", 10000);
        Member findMember = memberRepository.findById(member.getMemberId());

        assertThat(findMember).isEqualTo(member);
    }

    @DisplayName("수정")
    @Order(3)
    @Test
    void update() {
        String id = "yeonLog";
        int money = 20000;

        memberRepository.update(id, money);

        Member findMember = memberRepository.findById(id);
        assertThat(findMember.getMoney()).isEqualTo(money);
    }

    @DisplayName("삭제")
    @Order(4)
    @Test
    void delete() {
        String id = "yeonLog";
        memberRepository.delete(id);

        assertThatThrownBy(() -> memberRepository.findById(id))
                .isInstanceOf(IllegalStateException.class);
    }
}