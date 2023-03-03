package study.querydsl.repository;

import static study.querydsl.entity.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import study.querydsl.entity.Member;

@Repository
public class MemberRepository {

	private final EntityManager entityManager;
	private final JPAQueryFactory jpaQueryFactory;

	public MemberRepository(EntityManager entityManager) {
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
}
