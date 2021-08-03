package hello.springmvc.basic.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyStringController {
	// 1. HttpServletRequest와 Response
	@PostMapping("/request-body-string-v1")
	public void requestBodyStringV1 (HttpServletRequest req, HttpServletResponse res) throws IOException {
		ServletInputStream inputStream = req.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		log.info("msgBody = {}", messageBody);
		res.getWriter().write("ok");
	}
	
	// 2. InputStream 직접 받기
	@PostMapping("/request-body-string-v2")
	public void requestBodyStringV2 (InputStream inputStream, Writer resWriter) throws IOException {
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		log.info("msgBody = {}", messageBody);
		resWriter.write("ok");
	}
	
	// 3. HttpEntity: HTTP header, body 정보 편리하게 조회
	@PostMapping("/request-body-string-v3")
	public HttpEntity<String> requestBodyStringV3 (HttpEntity<String> httpEntity) throws IOException {
		String messageBody = httpEntity.getBody();
		log.info("msgBody = {}", messageBody);
		
		return new HttpEntity<>("ok");
		//return new ResponseEntity<>("ok", HttpStatus.CREATED);	// 상태코드 지정 가능
	}
	
	// 4. @RequestBody 사용하기
	@ResponseBody
	@PostMapping("/request-body-string-v4")
	public String requestBodyStringV4 (@RequestBody String messageBody) throws IOException {
		log.info("msgBody = {}", messageBody);
		return "ok";	// @ResponseBody 활용
	}
	
	/* point 
	 * 요청 파라미터 조회: @RequestParam, @ModelAttribute
	 * HTTP 메시지 바디 조회: @RequestBody
	 */
}
