package study.querydsl.repository;

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
		return entityManager.createQuery("select m from Member m where m.username = :username", Member.class)
			.setParameter("username", username)
			.getResultList();
	}

	public List<Member> findAll() {
		return entityManager.createQuery("select m from Member m", Member.class)
			.getResultList();
	}
}
