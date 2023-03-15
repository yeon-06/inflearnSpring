package study.querydsl.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.entity.Member;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void save() {
		// given
		Member member = new Member("yeonlog", 27);

		// when
		memberRepository.save(member);
		clear();

		// then
		List<Member> members = memberRepository.findAll();
		assertThat(members).hasSize(1);
	}

	@Test
	void findById() {
		// given
		Member member = new Member("yeonlog", 27);
		memberRepository.save(member);
		clear();

		// when
		List<Member> members = memberRepository.findByUsername(member.getUsername());

		// then
		assertThat(members).hasSize(1);
	}

	@Test
	void findAll() {
		// given
		memberRepository.save(new Member("yeonlog", 27));
		memberRepository.save(new Member("yeonlog2", 27));
		clear();

		// when
		List<Member> members = memberRepository.findAll();

		// then
		assertThat(members).hasSize(2);
	}

	private void clear() {
		entityManager.flush();
		entityManager.clear();
	}
}
