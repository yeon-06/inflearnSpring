package hello.springmvc.basic.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// @RestController 		// == @Controller + @ResponseBody
public class ResponseBodyController {
	
	@GetMapping("/response-body-string-v1")
	public void responseBodyV1(HttpServletResponse res) throws IOException {
		res.getWriter().write("ok");
	}
	
	@GetMapping("/response-body-string-v2")
	public ResponseEntity<String> responseBodyV2() throws IOException {
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
	
	// 문자로 처리
	@ResponseBody	// 클래스 위에 붙일수도 있음
	@GetMapping("/response-body-string-v3")
	public String responseBodyV3(HttpServletResponse res) {
		return "ok";
	}

	// json 처리
	@ResponseBody
	@GetMapping("/response-body-json-v1")
	public ResponseEntity<HelloData> responseBodyJsonV1 () {
		HelloData helloData = new HelloData();
		helloData.setAge(25);
		helloData.setUsername("yeonLog");
		
		return new ResponseEntity<>(helloData, HttpStatus.OK);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)	// 어노테이션을 사용해 HttpStatus 변경
	@GetMapping("/response-body-json-v2")
	public HelloData responseBodyJsonV2 () {
		HelloData helloData = new HelloData();
		helloData.setAge(25);
		helloData.setUsername("yeonLog");
		
		return helloData;
	}
}
