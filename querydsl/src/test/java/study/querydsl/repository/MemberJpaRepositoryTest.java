package study.querydsl.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.dto.MemberInfoDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MemberJpaRepository memberRepository;

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
		List<Member> members = memberRepository.findByName(member.getUsername());

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

	@Test
	void search_with_teamName() {
		// given
		Team team = new Team("team");
		entityManager.persist(team);
		memberRepository.save(new Member("yeonlog", 27, team));
		memberRepository.save(new Member("yeonlog2", 27, team));
		clear();

		// when
		List<MemberInfoDto> result = memberRepository.search(null, team.getName());

		// then
		assertThat(result).hasSize(2);
	}

	@Test
	void search_with_username() {
		// given
		Team team = new Team("team");
		entityManager.persist(team);
		String username = "yeonlog";
		memberRepository.save(new Member(username, 27, team));
		memberRepository.save(new Member(username, 25, team));
		clear();

		// when
		List<MemberInfoDto> result = memberRepository.search(username, null);

		// then
		assertThat(result).hasSize(2);
	}

	@Test
	void search_with_username_and_teamName() {
		// given
		Team team = new Team("team");
		entityManager.persist(team);
		String username = "yeonlog";
		memberRepository.save(new Member(username, 27, team));
		memberRepository.save(new Member(username + "2", 25, team));
		clear();

		// when
		List<MemberInfoDto> result = memberRepository.search(username, team.getName());

		// then
		assertThat(result).hasSize(1);
	}

	private void clear() {
		entityManager.flush();
		entityManager.clear();
	}
}
