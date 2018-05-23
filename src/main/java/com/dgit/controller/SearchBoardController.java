package com.dgit.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/")
public class SearchBoardController { // page+search

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private BoardService service;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	
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
		public String removePage(int bno, SearchCriteria cri, Model model, String[] files) throws Exception{
			logger.info("board removePage ...........................");
			logger.info("bno : "+ bno);
			logger.info(cri.toString());
			
			
			for(String file : files){
				logger.info("file : "+file);
				UploadFileUtils.deleteFile(uploadPath, file);
			}
			
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
		
		@RequestMapping(value="/register", method=RequestMethod.GET)
		public void registerGet(){
			logger.info("Board register Get ......");
			
			//return "/board/register";
			
			//board파일 안에 있는 register.jsp파일
			//view 안의 폴더 이름이 url주소와 같을 때에는 리턴타입을 void로 하고 return을 생략할 수 있음
		}
		
		
		//jsp에서 from 을 누르면 파일들은 객체로 넘어오기 때문에 List<MultipartFile> imageFiles
		//BoardVO에 files에 넣을 수 없음(여기에는 String임) private String[] files;
		//그래서 BoardVO에 변수명을 바꾸던지 input에 name을 
		//바꾸던지해서 이름을 다르게 해줘야 함
		@RequestMapping(value="/register", method=RequestMethod.POST)
		public String registerPost(BoardVO vo, List<MultipartFile> imageFiles) throws Exception{ //title, content
			logger.info("board register Post............");
			logger.info(vo.toString());
			
			//BoardVO에 getFiles에 넣어주기
			ArrayList<String> list = new ArrayList<>();
			for(MultipartFile file : imageFiles){
				logger.info("filename : "+ file.getOriginalFilename());
				
				String thumb = UploadFileUtils.uploadFile(uploadPath, 
											file.getOriginalFilename(), 
											file.getBytes());
				list.add(thumb);//리스트객체
			}
			
			vo.setFiles(list.toArray(new String[list.size()]));//스트링배열
			
			service.regist(vo);
			
			//	return "/board/success";
			//글을 등록하고 나면 listAll command로 가게 하기 위해 
			return "redirect:/sboard/listPage";
		}
		
		
		
		//명령어 /displayFile에 filename넣어줘야 함
		@ResponseBody
		@RequestMapping("/displayFile")
		public ResponseEntity<byte[]> displayFile(String filename) throws Exception{
			ResponseEntity<byte[]> entity = null;
			InputStream in = null;//파일을 읽기위해
			
			logger.info("[displayFile] filename : "+ filename);
			
			try {
				
				//MediaType.IMAGE_JPEG, MediaType.png...... 확장자가 다름 util pagkage 만들어서 넣음
				//확장자 뽑아내기
				String format = filename.substring(filename.lastIndexOf(".")+1);
				//.jpg등등 뽑아냄
				
				MediaType mType = MediaUtils.getMediaType(format);
				
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(mType);
				
				in = new FileInputStream(uploadPath+"/"+filename);
				
				//IOUtils통해서 in객체들을 뽑아줌 headers를 통해서 새로 생성하라고 함
				entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
															headers,
															HttpStatus.CREATED);
				//외부 저장소에 저장된 이미지 파일의 정보를 받아서 바이트 배열로 뽑아내서 바이트 배열을 실어서 보냄
				
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			}finally {
				in.close();
			}
			return entity;
		}
}
