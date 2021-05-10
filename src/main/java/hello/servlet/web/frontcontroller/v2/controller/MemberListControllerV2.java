package hello.servlet.web.frontcontroller.v2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

public class MemberListControllerV2 implements ControllerV2{
	MemberRepository memberRepository = MemberRepository.getInstance();
	
	@Override
	public MyView process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Member> members = memberRepository.findAll();
		req.setAttribute("members", members);
		return new MyView("/WEB-INF/views/members.jsp");
	}
}
