package hello.springmvc.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyJsonController {
	/* 주의!
	 * HTTP 요청 시 content-type: application/json인지 확인해야함
	 */
	private ObjectMapper mapper = new ObjectMapper();
	
	// 1. HttpServletRequest, HttpServletResponse
	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1 (HttpServletRequest req, HttpServletResponse res) throws IOException {
		ServletInputStream inputStream = req.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		log.info("msgBody = {}", messageBody);
		
		HelloData helloData = mapper.readValue(messageBody, HelloData.class);
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		
		res.getWriter().write("ok");
	}
	
	// 2. @RequestBody 이용
	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public String requestBodyJsonV2 (@RequestBody String messageBody) throws IOException {
		log.info("msgBody = {}", messageBody);
		
		HelloData helloData = mapper.readValue(messageBody, HelloData.class);
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		
		return "ok";
	}
	
	// 3. Http 메시지 컨버터가 자동으로 매핑
	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public String requestBodyJsonV3 (@RequestBody HelloData helloData) {
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		return "ok";
	}
	
	// 4. HttpEntity 사용하기
	@ResponseBody
	@PostMapping("/request-body-json-v4")
	public String requestBodyJsonV4 (HttpEntity<HelloData> httpEntity) {
		HelloData helloData = httpEntity.getBody();
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		return "ok";
	}
	
	// 5. 객체를 반환하기
	@ResponseBody
	@PostMapping("/request-body-json-v5")
	public HelloData requestBodyJsonV5 (@RequestBody HelloData helloData) {
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		return helloData;	// 자동으로 json으로 바뀌어서 return
	}
}
