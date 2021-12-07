package hello.servlet.web.servletmvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "mvcMemberFormSerlvet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String viewPath = "/WEB-INF/views/new-form.jsp";	// WEB-INF 이하의 자원들은 외부의 직접 호출 X. 컨트롤러 통해서 호출 O. 
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath); // 다른 servlet이나 JSP로 이동 가능
		dispatcher.forward(req, res);	// redirect: 클라이언트가 다시 요청. forward: 서버에서 호출하고 끝. 클라이언트가 인지 x.
	}
}
