package hello.servlet.web.springmvc.old;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("/springmvc/request-handler")
public class MyHttpRequestHandler implements HttpRequestHandler{
	@Override
	public void handleRequest (HttpServletRequest req, HttpServletResponse res) throws ServletException {
		System.out.println("MyHttpRequestHandler.handleRequest");
	}
}
