package com.dgit.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;
import com.dgit.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void loginGet() throws Exception {
		logger.info("login GET............................");
		
		
	}
	
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)
	public void loginPost(UserVO vo, Model model) throws Exception {
		logger.info("login POST............................");
		logger.info(vo.toString());
		
		
		vo = service.login(vo);
		
		if(vo==null){
			return;
		}
		
		LoginDTO loginDto = new LoginDTO();
		loginDto.setUid(vo.getUid());
		loginDto.setUname(vo.getUname());
		
		model.addAttribute("loginDTO", loginDto);
	}
	
	@RequestMapping(value="/logOut", method=RequestMethod.GET)
	public String logOut(HttpSession session) throws Exception {
		logger.info("logOut............................");
		session.invalidate();// session이 날아감 = 로그아웃 됨
		
		return "redirect:/";
	}
}
