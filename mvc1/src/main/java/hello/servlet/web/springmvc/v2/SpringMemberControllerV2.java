package hello.servlet.web.springmvc.v2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

@Controller	
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {
	private MemberRepository memberRepository = MemberRepository.getInstance();
	
	@RequestMapping("/new-form")
	private ModelAndView newForm() {
		return new ModelAndView("new-form");
	}
	
	@RequestMapping
	public ModelAndView members() {
		List<Member> members = memberRepository.findAll();
		
		ModelAndView mv = new ModelAndView("members");
		mv.getModel().put("members", members);
		
		return mv;
	}
	
	@RequestMapping("/save")
	private ModelAndView save(HttpServletRequest req, HttpServletResponse res) {
		String username = req.getParameter("username");
		int age = Integer.parseInt(req.getParameter("age"));
		
		Member member = new Member(username, age);
		memberRepository.save(member);
		
		ModelAndView mv = new ModelAndView("save-result");
		mv.addObject("member", member);
		return mv;
	}
}
