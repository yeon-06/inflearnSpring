package hello.springmvc.basic.request;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestParamController {
	// step1. getParameter 이용하기
	@RequestMapping("/request-param-v1")
	public void requestParamV1(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String name = req.getParameter("username");
		Integer age = Integer.parseInt(req.getParameter("age"));
		
		log.info("username={}, age={}",name, age);

		res.getWriter().write("ok");
	}
	
	// step2. @RequestParam 이용하기
	@ResponseBody	// "ok"라는 view를 찾는 것이 아니라 문자열 반환하기 위함
	@RequestMapping("/request-param-v2")
	public String requestParamV2(@RequestParam("username") String name,
									@RequestParam("age") int age) {
		log.info("username={}, age={}",name, age);
		return "ok";
	}
	
	// step3. @RequestParam 간소화
	@ResponseBody
	@RequestMapping("/request-param-v3")
	public String requestParamV3(
							@RequestParam String username,
//							@RequestParam(required = true, defaultVale="guest") String username,	// 필수 파라미터 설정. 해당 파라미터 없으면 페이지 접근 시 오류
							@RequestParam int age) {
		log.info("username={}, age={}",username, age);
		return "ok";
	}
	
	// step4. @RequestParam 생략. -> 파라미터를 받아온 것임을 보기 편하게 하기 위해 step3을 더 권장하는 편.
	@ResponseBody
	@RequestMapping("/request-param-v4")
	public String requestParamV4(String username, int age) {
		log.info("username={}, age={}",username, age);
		return "ok";
	}
	
	// 모든 파라미터 받아오기
	@ResponseBody
	@RequestMapping("/request-param-map")
	public String requestParamMap(@RequestParam Map<String, Object> map) {
		log.info("username={}, age={}",map.get("username"), map.get("age"));
		return "ok";
	}
	
	// 모든 파라미터 받아오기 - 중복되는 key
	@ResponseBody
	@RequestMapping("/request-param-MultiValueMap")
	public String requestParamMultiValueMap(@RequestParam MultiValueMap<String, Object> map) {
		log.info("username={}, age={}",map.get("username"), map.get("age"));
		return "ok";
	}
	
	// 파라미터 객체에 담기 - @RequestParam
	@ResponseBody
	@RequestMapping("/model-attribute-v1")
	public String modelAttributeV1(@RequestParam String username, @RequestParam int age) {
		HelloData helloData = new HelloData();
		helloData.setUsername(username);
		helloData.setAge(age);
		log.info("username={}, age={}",helloData.getUsername(), helloData.getAge());
		return "ok";
	}
	
	// 파라미터 객체에 담기 - @ModelAttribute
	@ResponseBody
	@RequestMapping("/model-attribute-v2")
	public String modelAttributeV2(@ModelAttribute HelloData helloData) {
		log.info("username={}, age={}",helloData.getUsername(), helloData.getAge());
		return "ok";
	}
	
}
