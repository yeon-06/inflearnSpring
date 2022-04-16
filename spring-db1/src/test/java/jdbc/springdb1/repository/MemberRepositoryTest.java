package jdbc.springdb1.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import jdbc.springdb1.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberRepositoryTest {

    private final MemberRepository memberRepository = new MemberRepository();

    @DisplayName("등록 확인")
    @Test
    void insert() {
        Member member = new Member("yeonlog", 10000);
        assertDoesNotThrow(() -> memberRepository.save(member));
    }

    @DisplayName("조회")
    @Test
    void select() {
        Member member = new Member("yeonlog", 10000);
        Member findMember = memberRepository.findById(member.getMemberId());

        assertThat(findMember).isEqualTo(member);
    }
}