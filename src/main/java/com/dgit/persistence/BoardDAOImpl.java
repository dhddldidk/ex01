package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {

	
	@Autowired
	SqlSession session;
	
	private static final String namespace = "com.dgit.mapper.BoardMapper";
	
	@Override
	public void create(BoardVO vo) throws Exception {
		session.insert(namespace+".create",vo);//.create이름은 Mapper의 id와 같아야 함

	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return session.selectOne(namespace+".read",bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		session.update(namespace+".update", vo);

	}

	@Override
	public void delete(int bno) throws Exception {
		session.delete(namespace+".delete",bno);

	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		
		return session.selectList(namespace+".listAll");
	}

	//페이징
	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		page = (page-1)*10;
		
		
		return session.selectList(namespace+".listPage", page);
	}

	//페이징 Criteria class에 맞게 게시글 가져옴
	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return session.selectList(namespace+".listCriteria", cri);
	}

	@Override
	public int totalCount() throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".totalCount");
	}

	//검색한 부분에 대한 결과
	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".listSearch",cri);
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".totalSearchCount",cri);
	}

	
	//댓글을 달거나 댓글을 삭제할 때 호출되도록
	@Override
	public void updateReplyCnt(int bno, int amount) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("amount", amount);
		session.update(namespace+".updateReplyCnt", map);
		
	}

	@Override
	public void updateViewCnt(int bno) throws Exception {
		session.update(namespace+".updateViewCnt", bno);
		
	}

	@Override
	public void addAttach(String fullName) throws Exception {
		session.insert(namespace+".addAttach", fullName);
		
	}

	@Override
	public List<String> getAttach(int bno) throws Exception {
		
		return session.selectList(namespace+".getAttach", bno);
	}

	@Override
	public void deleteAttach(int bno) throws Exception {
		session.selectOne(namespace+".deleteAttach", bno);
		
	}

	@Override
	public void replaceAttach(String fullName, int bno) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("bno", bno);
		map.put("fullName", fullName);
		session.insert(namespace+".replaceAttach", map);
		
	}

}
