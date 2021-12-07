package hello.servlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		// Content-Type: text/html;charset=utf-8
		res.setContentType("text/html");
		res.setCharacterEncoding("utf-8");
		
		PrintWriter writer = res.getWriter();
		writer.println("<html>");
		writer.println("<body>");
		writer.println(" <div>안녕?</div>");
		writer.println("</body>");
		writer.println("</html>");
	}
}
