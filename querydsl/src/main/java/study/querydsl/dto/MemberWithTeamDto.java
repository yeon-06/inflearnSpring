package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberWithTeamDto {

	private Long id;
	private String username;
	private String teamName;

	@QueryProjection
	public MemberWithTeamDto(Long id, String username, String teamName) {
		this.id = id;
		this.username = username;
		this.teamName = teamName;
	}
}
