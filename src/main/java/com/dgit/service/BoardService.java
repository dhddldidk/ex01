package com.dgit.service;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;

public interface BoardService {
	public void regist(BoardVO vo) throws Exception;
	
	public BoardVO read(int bno) throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
	public void modify(BoardVO vo) throws Exception;
	
	public void remove(int bno) throws Exception;
	
	//페이징
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
	public int totalCount() throws Exception;
}
