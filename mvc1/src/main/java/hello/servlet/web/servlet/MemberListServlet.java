package hello.servlet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

@WebServlet(name="memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {
	private MemberRepository memberRepository = MemberRepository.getInstance();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Member> members = memberRepository.findAll();
		
		res.setContentType("text/html");
		res.setCharacterEncoding("utf-8");
		
		PrintWriter w = res.getWriter();
		w.write("<html>");
		 w.write("<head>");
		 w.write(" <meta charset=\"UTF-8\">");
		 w.write(" <title>Title</title>");
		 w.write("</head>");
		 w.write("<body>");
		 w.write("<a href=\"/index.html\">메인</a>");
		 w.write("<table>");
		 w.write(" <thead>");
		 w.write(" <th>id</th>");
		 w.write(" <th>username</th>");
		 w.write(" <th>age</th>");
		 w.write(" </thead>");
		 w.write(" <tbody>");

		 for (Member member : members) {
		 w.write(" <tr>");
		 w.write(" <td>" + member.getId() + "</td>");
		 w.write(" <td>" + member.getUsername() + "</td>");
		 w.write(" <td>" + member.getAge() + "</td>");
		 w.write(" </tr>");
		 }
		 
		 w.write(" </tbody>");
		 w.write("</table>");
		 w.write("</body>");
		 w.write("</html>");
	}
}