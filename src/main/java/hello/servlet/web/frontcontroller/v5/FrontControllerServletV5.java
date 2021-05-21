package hello.servlet.web.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

@WebServlet(name = "frontControllerServletV5", urlPatterns="/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
	private final Map<String, Object> handlerMappingMap = new HashMap<>();
	private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
	
	public FrontControllerServletV5() {
		initHandlerMappingMap();
		initHandlerAdapters();
	}
	
	private void initHandlerMappingMap() {
		// v3
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
		
		// v4
		handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
	}
	
	private void initHandlerAdapters() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
		handlerAdapters.add(new ControllerV4HandlerAdapter());
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Object handler = getHandler(req);
		
		if(handler == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);	//404
			return;
		}
		
		MyHandlerAdapter adapter = getHandlerAdapter(handler);
		ModelView mv = adapter.handle(req, res, handler);
		
		String viewName = mv.getViewName();
		MyView view = viewResolver(viewName);
		view.render(mv.getModel(), req, res);
	}
	
	private Object getHandler(HttpServletRequest req) {
		String reqURI = req.getRequestURI();
		return handlerMappingMap.get(reqURI);
	}
	
	private MyHandlerAdapter getHandlerAdapter (Object handler) {
		for(MyHandlerAdapter adapter : handlerAdapters ) {
			if(adapter.supports(handler)) {
				return adapter;
			}
		}
		throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다.");
	}
	
	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}