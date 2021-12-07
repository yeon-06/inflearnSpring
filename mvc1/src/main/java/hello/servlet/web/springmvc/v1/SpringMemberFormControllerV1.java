package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller	// Spring이 자동으로 Spring bean으로 등록 + 스프링 MVC에서 어노테이션 기반 컨트롤러로 인식
public class SpringMemberFormControllerV1 {
	@RequestMapping("/springmvc/v1/members/new-form")	// 요청 정보 매핑. 해당 url로 호출 시 해당 메소드 호출
	private ModelAndView process() {
		return new ModelAndView("new-form");
	}
}
