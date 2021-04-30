package hello.servlet.basic.request;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=yeonlog&age=25
 *
 */
@WebServlet(name="requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("[전체 파라미터 조회] - start ");
		req.getParameterNames().asIterator()
			.forEachRemaining(paramName -> System.out.println(paramName + "=" + req.getParameter(paramName)));
		System.out.println("[전체 파라미터 조회] - end ");
		System.out.println();
		
		System.out.println("[단일 파라미터 조회] - start ");
		String username = req.getParameter("username");
		String age = req.getParameter("age");
		System.out.println(username + age);
		System.out.println("[단일 파라미터 조회] - end ");
		
		System.out.println("[복수 파라미터 조회] - start "); // http://localhost:8080/request-param?username=yeonlog&username=yeonlog2
		String[] usernames = req.getParameterValues("username");
		for(String name:usernames)  {
			System.out.println(name);
		}
		System.out.println("[복수 파라미터 조회] - end ");
		
		res.getWriter().write("ok");
	}
}
