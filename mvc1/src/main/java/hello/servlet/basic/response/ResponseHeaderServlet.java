package hello.servlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// status-line
		res.setStatus(HttpServletResponse.SC_OK);
		
		// response header
		res.setContentType("text/plain");
		res.setCharacterEncoding("utf-8");
		//res.setHeader("Content-Type", "text/plain;charset=utf-8");
		res.setHeader("Cache-Contorl", "no-cache, no-store, must-revalidate");
		res.setHeader("Pragma", "no-cache");
		res.setHeader("temp-header", "hello world");
		
		cookie(res);
		redirect(res);
		
		PrintWriter writer = res.getWriter();
		writer.println("ok");
	}
	
	private void cookie (HttpServletResponse res) {
		Cookie cookie = new Cookie("myCookie","good");
		cookie.setMaxAge(600);
		res.addCookie(cookie);
	}
	private void redirect (HttpServletResponse res) {
		res.setStatus(HttpServletResponse.SC_FOUND);
		res.setHeader("Location", "/basic/hello-form.html");
//		res.sendRedirect("/basic/hello-form.thml");
	}
}
