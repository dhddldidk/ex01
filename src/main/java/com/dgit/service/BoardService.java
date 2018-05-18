package com.dgit.service;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardService {
	public void regist(BoardVO vo) throws Exception;
	
	public BoardVO read(int bno) throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
	public void modify(BoardVO vo) throws Exception;
	
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
