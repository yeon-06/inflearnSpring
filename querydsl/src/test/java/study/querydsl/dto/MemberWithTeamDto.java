package study.querydsl.dto;

import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

public class MemberWithTeamDto {

    private final Member member;
    private final Team team;

    public MemberWithTeamDto(Member member, Team team) {
        this.member = member;
        this.team = team;
    }
}