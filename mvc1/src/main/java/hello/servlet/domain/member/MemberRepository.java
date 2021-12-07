package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemberRepository {
	private static Map<Long, Member> store = new ConcurrentHashMap<> ();
	private static long sequence = 0L;
	
	// 싱글톤으로 생성
	private static final MemberRepository instance = new MemberRepository();
	
	public static MemberRepository getInstance() {
		return instance;
	}
	
	private MemberRepository() {
	}
	
	public Member save(Member member) {
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}
	
	public Member findById(Long id) {
		return store.get(id);
	}
	
	public List<Member> findAll() {
		return new ArrayList<>(store.values()); //store룰 보호하기 위해 arraylist를 새로 생성
	}
	
	public void clearStore() {
		store.clear();
	}
}
