package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;
import com.dgit.persistence.BoardDAO;
import com.dgit.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO dao;
	
	@Autowired
	private BoardDAO boardDao;
	
	//게시판에 댓글갯수 나타내기 위해 
	@Transactional
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		dao.create(vo);
		
		boardDao.updateReplyCnt(vo.getBno(), 1);
	}

	@Override
	public List<ReplyVO> listReply(int bno) throws Exception {
		
		return dao.list(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		dao.update(vo);

	}

	//게시판에서 답글 삭제하면 갯수도 -되도록
	@Transactional
	@Override
	public void removeReply(int rno) throws Exception {
		
		//getBno-게시판 번호를 알 수 있음
		int bno = dao.getBno(rno);
		
		//게시판 번호를 알고 난 후 답글을 삭제하도록 함!!!!!!!!!!
		dao.delete(rno);
		
		boardDao.updateReplyCnt(bno, -1);
	}

	@Override
	public List<ReplyVO> listPageReply(int bno, Criteria cri) throws Exception {
		
		return dao.listPage(bno, cri);
	}

	@Override
	public int count(int bno) throws Exception {
		
		return dao.count(bno);
	}

}
