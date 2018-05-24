package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDAO;
import com.dgit.persistence.ReplyDAO;

@Service
public class BoardServiceImpl implements BoardService {

	
	@Autowired
	BoardDAO dao;
	
	@Autowired
	ReplyDAO replyDao;
	
	//게시물 추가하는 함수
	//게시물을 추가할 때 attch 테이블에도 사진을 넣어줌
	@Transactional
	@Override
	public void regist(BoardVO vo) throws Exception {
		dao.create(vo);

		//예외처리- 파일 선택없이 게시물 등록할 때를 대비함
		if(vo.getFiles()==null){
			return;
		}
		
		//파일도 배열로 받아서 넣어줌
		for(String filename : vo.getFiles()){
			dao.addAttach(filename);
		}
		
	}

	
	//1. 읽을 때 viewCnt를 올림 but 수정할 때에는 조회수가 변동없도록 처리
	//2. readPage Controller에서 boolean flag를 받아서 
	//3. flag가 true인 경우에만 조회수를 올려줌
	@Transactional
	@Override
	public BoardVO read(int bno, boolean flag) throws Exception {	
		
		if(flag==true){
			dao.updateViewCnt(bno);
		}
		
		BoardVO vo = dao.read(bno);
		List<String> files = dao.getAttach(bno);
		vo.setFiles(files.toArray(new String[files.size()]));
		
		return vo;
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		
		return dao.listAll();
	}

	@Transactional
	@Override
	public void modify(BoardVO vo, String[] oldFiles) throws Exception {
		dao.update(vo);
		
		//첨부파일 수정할 때 선택된 파일 지우기
		int bno = vo.getBno();
		if(oldFiles!=null){
			for(String deletedFile : oldFiles){
				dao.deleteAttach(bno, deletedFile);
			}
			
		}
		
		
		
		
		//첨부파일 새로 선택한거 업로드
		String[] files = vo.getFiles();
		
		if(files == null ){
			return;
		}
		
		for(String fileName : files){
			dao.replaceAttach(fileName, bno);
		}

	}

	@Transactional
	@Override
	public void remove(int bno) throws Exception {
		
		
		dao.deleteAttach(bno, null);
		
		//만약 게시글 삭제 버튼을 눌렀을 때 
		//모든 댓글도 삭제해야 게시글도 지울 수 있음
		//bno 외래키 때문에
		//replyDao.deleteAllReply(bno);
		
		dao.delete(bno);
		
		
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return dao.listCriteria(cri);
	}

	@Override
	public int totalCount() throws Exception {
		
		return dao.totalCount();
	}

	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		
		return dao.listSearch(cri);
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		
		return dao.totalSearchCount(cri);
	}


	@Override
	public void plusViewcnt(int bno) throws Exception {
		
		dao.updateViewCnt(bno);
	}

}
