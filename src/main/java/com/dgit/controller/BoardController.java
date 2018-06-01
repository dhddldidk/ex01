package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.service.BoardService;

@RequestMapping("/board/")
@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;
	
	//ex01/board/register command할 때 주소가
	//ex01/board/listAll
	//ex01/board/modify
	
	/*@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register(){
		logger.info("Board register Get ......");
		
		return "/board/register";
		//board파일 안에 있는 register.jsp파일
	}*/
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void registerGet(){
		logger.info("Board register Get ......");
		
		//return "/board/register";
		
		//board파일 안에 있는 register.jsp파일
		//view 안의 폴더 이름이 url주소와 같을 때에는 리턴타입을 void로 하고 return을 생략할 수 있음
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception{ //title, content
		logger.info("board register Post............");
		logger.info(vo.toString());
		
		service.regist(vo);
		
		//	return "/board/success";
		//글을 등록하고 나면 listAll command로 가게 하기 위해 
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("board listAll .........................");
		
		List<BoardVO> list = service.listAll();
		model.addAttribute("list", list);
	}
	
	@RequestMapping(value="/read", method=RequestMethod.GET)
	public void read(Model model, int bno, boolean flag) throws Exception {//게시글 번호를 int로 받음
		logger.info("board read ............................");
		logger.info("bno : "+bno);
		
		BoardVO vo = service.read(bno, flag);
		model.addAttribute("boardVO", vo);
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.GET)
	public String remove(int bno) throws Exception{
		logger.info("board remove ...........................");
		logger.info("bno : "+ bno);
		
		service.remove(bno);
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modifyGet(int bno, Model model) throws Exception{
		logger.info("board modify ........................... ");
		logger.info("bno : "+bno);
		
		BoardVO oldVo = service.read(bno, false);
		
		
		model.addAttribute("oldVo",oldVo);
		return "/board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyPost(BoardVO vo, Model model) throws Exception{
		logger.info("board modify ........................... ");
		logger.info("bno : "+vo);
		service.modify(vo, null);
		
		//model.addAttribute("bno", vo.getBno());
		return "redirect:/board/read?bno="+vo.getBno();
	}
	
	/* ----------------------------------------------------------------------- */
	//페이징  url ex01/board/listPage
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listPage(Model model, Criteria cri) throws Exception{//Criteria 클래스에서 생성자에서 page1 을 불러줌
		logger.info("board listPage .........................");
		
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalCount());
		model.addAttribute("pageMaker",pageMaker);
	}
	
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public void readPage(Model model, int bno, @ModelAttribute("cri")Criteria cri, boolean flag) throws Exception {//게시글 번호를 int로 받음, page번호도
		logger.info("board readPage ............................");
		logger.info("bno : "+bno);
		logger.info(cri.toString());
		
		BoardVO vo = service.read(bno, flag);
		model.addAttribute("boardVO", vo);
	}
	
	@RequestMapping(value="/removePage", method=RequestMethod.GET)
	public String removePage(int bno, Criteria cri) throws Exception{
		logger.info("board removePage ...........................");
		logger.info("bno : "+ bno);
		logger.info(cri.toString());
		
		service.remove(bno);
		return "redirect:/board/listPage?page="+cri.getPage();
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public String modifyPageGet(int bno, Model model, @ModelAttribute("cri")Criteria cri) throws Exception{
		logger.info("board modifyPageGet ........................... ");
		logger.info("bno : "+bno);
		logger.info(cri.toString());
		BoardVO oldVo = service.read(bno, false);
		
		
		model.addAttribute("oldVo",oldVo);
		return "/board/modifyPage";
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String modifyPagePost(BoardVO vo, Model model,Criteria cri) throws Exception{
		logger.info("board modifyPagePost ........................... ");
		logger.info("bno : "+vo);
		service.modify(vo, null);
		
		
		//model.addAttribute("bno", vo.getBno());
		return "redirect:/board/readPage?flag=false&bno="+vo.getBno()+"&page="+cri.getPage();
	}
}
