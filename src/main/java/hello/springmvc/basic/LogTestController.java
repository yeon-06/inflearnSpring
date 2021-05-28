package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //@Controller는 return viewResolver 해야하는데 RestController는 HTTP 메시지 바디에 바로 입력
public class LogTestController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/log-test")
	public String logTest() {
		String name = "Hello Log Test!";
		
		log.trace("trace log={}",name);
		log.debug("debug log={}",name);
		log.info("info log={}", name);
		log.warn("warn log={}", name);
		log.error("error log={}", name);
		
		return "ok";
	}
}
