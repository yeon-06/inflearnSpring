package hello.servlet.web.frontcontroller.v5.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof ControllerV3);
	}
	
	@Override
	public ModelView handle(HttpServletRequest req, HttpServletResponse res, Object handler) throws ServletException, IOException {
		ControllerV3 controller = (ControllerV3) handler; 
        
		Map<String, String> paramMap = createParamMap(req);
		ModelView mv = controller.process(paramMap);
		return mv;
	}

	private Map<String, String> createParamMap(HttpServletRequest req) {
		Map<String, String> paramMap = new HashMap<>();
		req.getParameterNames().asIterator()
		 		.forEachRemaining(paramName -> paramMap.put(paramName, req.getParameter(paramName)));
		return paramMap;
	}
}