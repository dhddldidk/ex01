package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	
	@Autowired
	BoardDAO dao;
	
	
	@Override
	public void regist(BoardVO vo) throws Exception {
		dao.create(vo);

	}

	
	//읽을 때 viewCnt를 올림 but 수정할 때에는 조회수가 변동없도록 처리
	@Override
	public BoardVO read(int bno) throws Exception {	
		return dao.read(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		
		return dao.listAll();
	}

	@Override
	public void modify(BoardVO vo) throws Exception {
		dao.update(vo);

	}

	@Override
	public void remove(int bno) throws Exception {
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
