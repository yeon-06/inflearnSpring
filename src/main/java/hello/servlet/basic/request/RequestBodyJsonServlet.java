package hello.servlet.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.servlet.basic.HelloData;


@WebServlet(name="requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet{

	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		ServletInputStream inputStream = req.getInputStream();
		
		String msgBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		System.out.println("msg Body = " + msgBody);
		
		HelloData helloData = mapper.readValue(msgBody, HelloData.class);
		System.out.println(helloData.getUsername());
		System.out.println(helloData.getAge());
		
		res.getWriter().write("ok");
	}
}
