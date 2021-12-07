package hello.servlet.web.frontcontroller.v1.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.v1.ControllerV1;

public class MemberFormControllerV1 implements ControllerV1{
	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
		String viewPath = "/WEB-INF/views/new-form.jsp";	 
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath); 
		dispatcher.forward(req, res);

	}

}
