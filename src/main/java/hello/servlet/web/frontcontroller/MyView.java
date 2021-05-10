package hello.servlet.web.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyView {
	private String viewPath;
	
	public MyView(String viewPath) {
		this.viewPath = viewPath;
	}
	
	public void render(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
		dispatcher.forward(req, res);
	}
}
