package com.dgit.service;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardService {
	public void regist(BoardVO vo) throws Exception;
	
	public BoardVO read(int bno, boolean flag) throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
	//게시글 수정할 때 업로드된 파일도 파일 이름 받아서 수정하게
	public void modify(BoardVO vo, String[] filename) throws Exception;
	
	public void remove(int bno) throws Exception;
	
	//페이징
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
	public int totalCount() throws Exception;
	
	//검색한 후
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception;
	
	public int totalSearchCount(SearchCriteria cri) throws Exception;
	
	//조회수
	public void plusViewcnt(int bno) throws Exception;
}
