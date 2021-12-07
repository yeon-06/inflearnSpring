package hello.servlet.web.frontcontroller.v2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.MyView;

public interface ControllerV2 {
	MyView process(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException;
}
