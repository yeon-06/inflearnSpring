package study.querydsl;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QTeam.team;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.MemberWithTeamDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;


@SpringBootTest
@Transactional
class QuerydslApplicationTests {

	@Autowired
	private EntityManager entityManager;

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private final QMember qMember = QMember.member;

	private JPAQueryFactory queryFactory;

	private Team team1;
	private Team team2;
	private Member member;

	@BeforeEach
	void init() {
		queryFactory = new JPAQueryFactory(entityManager);

		team1 = new Team("team A");
		entityManager.persist(team1);
		team2 = new Team("team B");
		entityManager.persist(team2);

		member = new Member("yeonlog", 27, team1);
		Member member2 = new Member("logyeon", 25, team2);
		Member member3 = new Member("logyeon", 20, team2);
		entityManager.persist(member);
		entityManager.persist(member2);
		entityManager.persist(member3);
	}

	@Test
	void selectWithJpql() {
		// given
		String username = member.getUsername();

		// when
		Member findMember = entityManager.createQuery("select m from Member m where m.username = :username", Member.class)
			.setParameter("username", username)
			.getSingleResult();

		// then
		assertThat(findMember.getUsername()).isEqualTo(username);
	}

	// 단건 조회
	@Test
	void selectWithQuerydsl() {
		// given
		String username = member.getUsername();
		QMember qMember = QMember.member; // static으로 선언되어 있어서 바로 사용하면 된다.
//        QMember qMember = new QMember("m");   // 같은 테이블을 join하는 경우 선언해서 사용해야 한다.

		// when
		Member findMember = queryFactory.select(qMember)
			.from(qMember)
			.where(qMember.username.eq(username))
			.fetchOne();

		// then
		assertThat(findMember).isNotNull();
		assertThat(findMember.getUsername()).isEqualTo(username);
	}

	// 다건 조회
	@Test
	void selectAll() {
		// given & when=
		List<Member> members = queryFactory.select(qMember)
			.from(qMember)
			.fetch();

		// then
		assertThat(members).hasSize(3);
	}

	// 페이징 예제
	@Test
	void paging() {
		// given & when
		List<Member> members = queryFactory.selectFrom(qMember)
			.orderBy(qMember.age.desc())
			.offset(1)
			.limit(2)
			.fetch();

		// then
		assertThat(members).hasSize(2);
	}

	// 집함 함수 예제 - max, min, avg, ...
	@Test
	void max() {
		// given & when
		Integer result = queryFactory.select(qMember.age.max())
			.from(qMember)
			.fetchOne();

		// then
		assertThat(result).isEqualTo(27);
	}

	// 조인 예제 - team1에 소속된 멤버 조회
	@Test
	void join() {
		// given
		String teamName = team1.getName();

		// when
		List<Member> members = queryFactory.selectFrom(qMember)
			.join(qMember.team, team)
			.where(team.name.eq(teamName))
			.fetch();

		// then
		assertThat(members).contains(member);
	}

	// 세타 조인 예제 - Team 이름과 동일한 이름의 Member 조회
	@Test
	void theta_join() {
		// given
		String teamName = team1.getName();
		Member newMember = new Member(teamName);
		entityManager.persist(newMember);

		// when
		List<Member> members = queryFactory.select(qMember)
			.from(qMember, team)
			.where(qMember.username.eq(team.name))
			.fetch();

		// then
		assertThat(members).contains(newMember);
	}

	// on절 조인 예제 - 팀과 멤버를 조인. 단, 멤버는 전체를 조회하고 팀은 team1과 동일한 이름만 조회한다.
	@Test
	void on_join() {
		// given
		String teamName = team1.getName();

		// when
		List<MemberWithTeamDto> result = queryFactory.select(
				Projections.constructor(MemberWithTeamDto.class,
					qMember,
					team))
			.from(qMember)
			.join(qMember.team, team)
			.leftJoin(qMember.team, team).on(team.name.eq(teamName))
			.fetch();

		// then
		assertThat(result).extracting("team")
			.extracting("name")
			.contains(team1.getName())
			.doesNotContain(team2.getName());
	}

	// fetch 조인 예제 - fetch join 미적용
	@Test
	void no_fetch_join() {
		// given
		entityManager.flush();
		entityManager.clear();

		String username = member.getUsername();

		// when
		Member findMember = queryFactory.selectFrom(qMember)
			.join(qMember.team, team)
			.where(qMember.username.eq(username))
			.fetchOne();

		// then
		assertThat(findMember).isNotNull();
		boolean loaded = entityManagerFactory.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
		assertThat(loaded).isFalse();
	}

	// fetch 조인 예제 - fetch join 적용
	@Test
	void fetch_join() {
		// given
		entityManager.flush();
		entityManager.clear();

		String username = member.getUsername();

		// when
		Member findMember = queryFactory.selectFrom(qMember)
			.join(qMember.team, team).fetchJoin()
			.where(qMember.username.eq(username))
			.fetchOne();

		// then
		assert findMember != null;
		boolean loaded = entityManagerFactory.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
		assertThat(loaded).isTrue();
	}

	// 서브 쿼리 예제 - 나이가 가장 많은 회원 조회
	@Test
	void subQuery() {
		// given
		QMember memberSub = new QMember("memberSub");

		// when
		List<Member> maxAgeMembers = queryFactory.selectFrom(qMember)
			.where(qMember.age.eq(
				JPAExpressions.select(memberSub.age.max())
					.from(memberSub)
			)).fetch();

		// then
		assertThat(maxAgeMembers).extracting("age")
			.containsOnly(member.getAge());
	}

	// case문 예제
	@Test
	void case_ex() {
		// given & when
		List<String> memberNames = queryFactory.select(qMember.username
				.when("yeonlog").then("연로그")
				.when("logyeon").then("로그연")
				.otherwise("기타"))
			.from(qMember)
			.fetch();

		// then
		assertThat(memberNames).containsOnly("연로그", "로그연");
	}

	// DTO 조
	@Test
	void useProperty() {
		// 필수인 생성자/메서드 없음.
		MemberDto memberDto = queryFactory.select(
				Projections.fields(MemberDto.class,
					qMember.username,
					qMember.age
					// 필드명이 동일하지 않는 경우 아래 2가지 방법이 있음
//					member.age.as("memberAge")
//					ExpressionUtils.as(member.age, "memberAge")
				)
			).from(qMember)
			.where(qMember.id.eq(member.getId()))
			.fetchOne();

		assertThat(memberDto).isNotNull();
		assertThat(memberDto.getUsername()).isEqualTo(member.getUsername());
		assertThat(memberDto.getAge()).isEqualTo(member.getAge());
	}

	@Test
	void useConstructor() {
		// AllArgsConstructor 필수
		MemberDto memberDto = queryFactory.select(
				Projections.constructor(MemberDto.class,
					qMember.username,
					qMember.age
				)
			).from(qMember)
			.where(qMember.id.eq(member.getId()))
			.fetchOne();

		assertThat(memberDto).isNotNull();
		assertThat(memberDto.getUsername()).isEqualTo(member.getUsername());
		assertThat(memberDto.getAge()).isEqualTo(member.getAge());
	}

	@Test
	void useSetter() {
		/*
		case 1: Setter -> 성공
		case 2: Setter + AllArgsConstructor -> 실패
		case 3: Setter + NoArgsConstructor + AllArgsConstructor -> 성공

		setter와 빈 생성자가 존재해야한다.
		setter만 존재하는 경우가 통과한건 아무 생성자도 존재하지 않으면 빈 생성자를 자동으로 생성해줘서라고 예측된다.
		 */
		MemberDto memberDto = queryFactory.select(
				Projections.bean(MemberDto.class,
					qMember.username,
					qMember.age
				)
			).from(qMember)
			.where(qMember.id.eq(member.getId()))
			.fetchOne();

		assertThat(memberDto).isNotNull();
		assertThat(memberDto.getUsername()).isEqualTo(member.getUsername());
		assertThat(memberDto.getAge()).isEqualTo(member.getAge());
	}

	// 1. DTO 주생성자에 @QueryProjection 추가
	// 2. compileQuerydsl
	@Test
	void useQueryProjection() {
		// b: 컴파일 시점에 타입 체크 가능 (생성자 방식 같은건 런타임 시점에야 확인 가능)
		// q: Q 클래스 생성 필수. DTO에 QueryDSL에 대한 의존성 추가됨.
		MemberDto memberDto = queryFactory.select(new QMemberDto(qMember.username, qMember.age))
			.from(qMember)
			.where(qMember.id.eq(member.getId()))
			.fetchOne();

		assertThat(memberDto).isNotNull();
		assertThat(memberDto.getUsername()).isEqualTo(member.getUsername());
		assertThat(memberDto.getAge()).isEqualTo(member.getAge());
	}


	// BooleanBuilder를 이용해 동적 쿼리 생성
	@Test
	void dynamicQuery_BooleanBuilder() {
		List<Member> members = searchMember_BooleanBuilder(null);
		List<Member> membersWithName = searchMember_BooleanBuilder(member.getUsername());

		assertThat(members.size()).isNotEqualTo(membersWithName.size());
	}

	private List<Member> searchMember_BooleanBuilder(String username) {
		BooleanBuilder builder = new BooleanBuilder();
		if (username != null) {
			builder.and(qMember.username.eq(username));
		}
		return queryFactory.selectFrom(qMember)
			.where(builder)
			.fetch();
	}

	// 다중조건을 이용해 동적 쿼리 생성
	@Test
	void dynamicQuery_WhereParam() {
		List<Member> members = searchMember_where(null);
		List<Member> membersWithName = searchMember_where(member.getUsername());

		assertThat(members.size()).isNotEqualTo(membersWithName.size());
	}

	private List<Member> searchMember_where(String username) {
		return queryFactory.selectFrom(qMember)
			.where(usernameEq(username))
			.fetch();
	}

	private Predicate usernameEq(String username) {
		return username == null ? null : qMember.username.eq(username);
	}

	@Test
	void bulk() {
		int updateAge = 25;

		queryFactory.update(qMember)
			.set(qMember.age, updateAge)
			.execute();

		entityManager.flush();
		entityManager.clear();

		List<Integer> memberAges = queryFactory.selectFrom(qMember)
			.fetch()
			.stream()
			.map(Member::getAge)
			.collect(Collectors.toList());

		assertThat(memberAges).containsOnly(updateAge);
	}
}
