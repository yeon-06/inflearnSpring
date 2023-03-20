package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberInfoDto {

	private final Long id;
	private final String username;
	private final String teamName;

	@QueryProjection
	public MemberInfoDto(Long id, String username, String teamName) {
		this.id = id;
		this.username = username;
		this.teamName = teamName;
	}
}
