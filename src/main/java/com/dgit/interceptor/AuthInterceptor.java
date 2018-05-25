package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dgit.domain.LoginDTO;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("pre Handle.................................................");
		
		//로그인 한 사용자가 제한구역에 접근시 로그인 화면으로 가도록 처리
		
		//로그인 상태인지 아닌지 판단
		HttpSession session = request.getSession();
		LoginDTO dto = (LoginDTO)session.getAttribute("login");
		if(dto != null){
			logger.info("로그인이 된 상태입니다.");
		}else{
			logger.info("로그인이 안 된 상태입니다.");
			saveDest(request);
			response.sendRedirect(request.getContextPath()+"/user/login");
			
			return false;//Controller로 가는거 막음
		}
		
		return super.preHandle(request, response, handler);
	}
	
	//로그인 했을 때 고객이 갈려고 했던 listPage로 가도록
	private void saveDest(HttpServletRequest req){
		String uri = req.getRequestURI();//갈려고 하는 주소를 뽑아옴
		String query = req.getQueryString();//매개변수 정보를 줌 ex)몇번째 페이지 등등
		
		if(query == null || query.equals("null")){
			query = "";
		}else{
			query = "?" +query;
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")){
			logger.info("destination : "+uri+query);
			
			//세션에 uri주소와 갈려고 했던 목적지??매개변수를 session에 담아둠
			req.getSession().setAttribute("dest", uri+query);
		}
	}
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("post Handle.................................................");
		super.postHandle(request, response, handler, modelAndView);
	}

}
