package hello.servlet.domain.member;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class MemberRepositoryTest {
	MemberRepository memberRepository = MemberRepository.getInstance();
	
	@AfterEach 
	void afterEach() { // 테스트 종료 할때마다 store 날려주기
		memberRepository.clearStore();
	}
	
	@Test
	void save() {
		// given
		Member member = new Member("hello",20);
		
		// when
		Member savedMember = memberRepository.save(member);
		
		// then
		Member findMember = memberRepository.findById(savedMember.getId());
		Assertions.assertThat(findMember).isEqualTo(savedMember);
	}
	
	@Test
	void findAll() {
		// given
		Member member1 = new Member("mem1",20);
		Member member2 = new Member("mem2",25);
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		// when
		List<Member> result = memberRepository.findAll();
		
		// then
		Assertions.assertThat(result.size()).isEqualTo(2);
		Assertions.assertThat(result).contains(member1, member2);
	}
}
