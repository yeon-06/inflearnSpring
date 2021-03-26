package hello.servlet.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="helloservlet", urlPatterns="/hello")
public class HelloServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("HelloServlet.service");
		
		String username = req.getParameter("username");
		res.setContentType("text/plain");
		res.setCharacterEncoding("utf-8");
		res.getWriter().write("hello "+username);
		
	}
}
