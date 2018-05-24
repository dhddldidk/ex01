package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

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
	
	//sboard검색부분
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	
	public int totalSearchCount(SearchCriteria cri) throws Exception;
	
	public void updateReplyCnt(int bno, int amount) throws Exception;
	
	//조회수 증가
	public void updateViewCnt(int bno) throws Exception;
	
	
	
	//파일 업로드
	public void addAttach(String fullName) throws Exception;
	
	public List<String> getAttach(int bno) throws Exception;
	
	public void deleteAttach(int bno, String fullName) throws Exception;
	
	public void replaceAttach(String fullName, int bno) throws Exception;
}
