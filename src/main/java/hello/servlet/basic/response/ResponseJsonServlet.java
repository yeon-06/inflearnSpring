package hello.servlet.basic.response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.servlet.basic.HelloData;


@WebServlet(name="responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet{
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		
		// HelloData 객체
		HelloData helloData = new HelloData();
		helloData.setUsername("yeonlog");
		helloData.setAge(25);
		
		// json 형태로 바꾸기
		String result = mapper.writeValueAsString(helloData);
		res.getWriter().write(result);
	}
}
