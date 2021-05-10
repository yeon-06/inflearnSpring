package hello.servlet.web.frontcontroller.v1.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v1.ControllerV1;

public class MemberSaveControllerV1 implements ControllerV1{
	MemberRepository memberRepository = MemberRepository.getInstance();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String username = req.getParameter("username");
		int age = Integer.parseInt(req.getParameter("age"));
		
		Member member = new Member(username, age);
		memberRepository.save(member);
		
		// Model에 데이터 보관
		req.setAttribute("member", member);
		
		String viewPath = "/WEB-INF/views/save-result.jsp";	
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath); 
		dispatcher.forward(req, res);
	}
}
