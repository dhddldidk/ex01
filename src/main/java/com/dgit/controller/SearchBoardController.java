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
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/sboard/")
public class SearchBoardController { // page+search

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private BoardService service;
	
	//페이징  url ex01/board/listPage
		@RequestMapping(value="/listPage", method=RequestMethod.GET)
		public void listPage(Model model, @ModelAttribute("cri")SearchCriteria cri) throws Exception{//Criteria 클래스에서 생성자에서 page1 을 불러줌
			logger.info("board listPage .........................");
			logger.info(cri.toString());
			
			List<BoardVO> list = service.listSearchCriteria(cri);//검색 안한 리스트
			model.addAttribute("list", list);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.totalSearchCount(cri));
			model.addAttribute("pageMaker",pageMaker);
		}
		
		@RequestMapping(value="/readPage", method=RequestMethod.GET)
		public void readPage(Model model, int bno, @ModelAttribute("cri")SearchCriteria cri, boolean flag) throws Exception {//게시글 번호를 int로 받음, page번호도
			logger.info("board readPage ............................");
			logger.info("bno : "+bno);
			logger.info(cri.toString());
			
			if(flag==true){
				service.plusViewcnt(bno);
			}
			
			
			BoardVO vo = service.read(bno);
			model.addAttribute("boardVO", vo);
		}
		
		@RequestMapping(value="/removePage", method=RequestMethod.GET)
		public String removePage(int bno, SearchCriteria cri, Model model) throws Exception{
			logger.info("board removePage ...........................");
			logger.info("bno : "+ bno);
			logger.info(cri.toString());
			
			service.remove(bno);
			
			model.addAttribute("page", cri.getPage());
			model.addAttribute("searchType", cri.getSearchType());
			model.addAttribute("keyword", cri.getKeyword());
			return "redirect:/sboard/listPage";
		}
		
		@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
		public String modifyPageGet(int bno, Model model, @ModelAttribute("cri")SearchCriteria cri) throws Exception{
			logger.info("board modifyPageGet ........................... ");
			logger.info("bno : "+bno);
			logger.info(cri.toString());
		
			BoardVO boardVO = service.read(bno);
			model.addAttribute("boardVO", boardVO);
			return "/sboard/modifyPage";
		}
		
		@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
		public String modifyPagePost(BoardVO vo, Model model, SearchCriteria cri) throws Exception{
			logger.info("board modifyPagePost ........................... ");
			logger.info("bno : "+vo);
			service.modify(vo);
			
			model.addAttribute("bno", vo.getBno());
			model.addAttribute("page", cri.getPage());
			model.addAttribute("searchType", cri.getSearchType());
			model.addAttribute("keyword", cri.getKeyword());
			model.addAttribute("flag", false);
			return "redirect:/sboard/readPage";
		}
}
