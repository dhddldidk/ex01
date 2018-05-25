package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	//세션에 로그인 정보만 넣기만 함

	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("pre Handle.................................................");
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("post Handle.................................................");
		
		//ModelAndView modelAndView 모델과 뷰 둘 다 있어야함 없으면 에러남 500
		//사용하지 않더라도 만들어 둬야 하는 경우가 있음
		
		
		Object loginDTO = modelAndView.getModel().get("loginDTO");
		
		if(loginDTO!=null){
			logger.info("new Login Success");
			
			//session을 가져올 수 있음
			HttpSession session = request.getSession();
			session.setAttribute("login", loginDTO);
			
			Object dest = session.getAttribute("dest");
			String path = "";
			
			if(dest != null){
				path = (String)dest;
			}else{
				path = request.getContextPath();
			}
			
			//request.getContextPath() 프로젝트 이름임
			response.sendRedirect(path);//home 화면으로 가도록
		}
	}

}
