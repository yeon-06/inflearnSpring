package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
private Logger log = LoggerFactory.getLogger(getClass());
	// URI가 같아도 http 메소드를 따로 지정하면 해당 메소드를 실행
	@PutMapping("/mapping-test")
	public String putTest() {
		log.info("putTest");
		return "ok";
	}
	
	@GetMapping("/mapping-test")
	public String getTest() {
		log.info("getTest");
		return "ok";
	}
	
	@PostMapping("/mapping-test")
	public String postTest() {
		log.info("postTest");
		return "ok";
	}
	
	@PatchMapping("/mapping-test")
	public String patchTest() {
		log.info("patchTest");
		return "ok";
	}
}
