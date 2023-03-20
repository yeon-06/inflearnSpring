package study.querydsl.repository;

import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.MemberInfoDto;
import study.querydsl.dto.QMemberWithTeamDto;
import study.querydsl.entity.Member;

@Repository
public class MemberJpaRepository {

	private final EntityManager entityManager;
	private final JPAQueryFactory jpaQueryFactory;

	public MemberJpaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	public void save(Member member) {
		entityManager.persist(member);
	}

	public List<Member> findByName(String username) {
		return jpaQueryFactory.selectFrom(member)
			.where(member.username.eq(username))
			.fetch();
	}

	public List<Member> findAll() {
		return jpaQueryFactory.selectFrom(member)
			.fetch();
	}

	public List<MemberInfoDto> search(String username, String teamName) {
		return jpaQueryFactory.select(
				new QMemberWithTeamDto(member.id, member.username, member.team.name)
			).from(member)
			.leftJoin(member.team, team)
			.where(
				usernameEq(username),
				teamNameEq(teamName)
			)
			.fetch();
	}

	private BooleanExpression usernameEq(String username) {
		return username == null ? null : member.username.eq(username);
	}

	private BooleanExpression teamNameEq(String teamName) {
		return teamName == null ? null : team.name.eq(teamName);
	}
}
