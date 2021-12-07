package hello.springmvc.basic.request;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RequestHeaderController {
	@RequestMapping("/headers")
	public String headers(HttpServletRequest req, 
							HttpServletResponse res, 
							HttpMethod httpMethod, 		// POST, GET, ...
							Locale locale, 				// 가장 우선순위 높은 Locale
							@RequestHeader MultiValueMap<String, String> headerMap,	// HTTP 헤더 조회 - MultiValueMap은 Map과 다르게 하나의 키에 여러개의 값 가질 수 있음
							@RequestHeader("host") String host,						// host 주소 ( ex: localhost:8080 )
							@CookieValue(value="myCookie", required=false) String cookie) {
		
		log.info("request={}", req);
		log.info("response={}", res);
		log.info("httpMethod={}", httpMethod);
		log.info("locale={}", locale);
		log.info("headerMap={}", headerMap);
		log.info("header host={}", host);
		log.info("myCookie={}", cookie);
				 
		return "ok";
	}
	
}
