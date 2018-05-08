package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;

public interface BoardDAO {
	public void create(BoardVO vo) throws Exception;
	
	public BoardVO read(int bno) throws Exception;
	
	public void update(BoardVO vo) throws Exception;
	
	public void delete(int bno) throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
	//페이징-테스트
	public List<BoardVO> listPage(int page) throws Exception;
	
	//페이징 Criteria class에 맞게 게시글 가져옴
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
	//전체 글 갯수 가져오기
	public int totalCount() throws Exception;
}
