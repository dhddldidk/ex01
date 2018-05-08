package com.dgit.ex01;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDAOTest {

	@Autowired
	BoardDAO dao;
	
	//@Test
	public void testCreate() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("title test");
		vo.setContent("content text");
		vo.setWriter("userid11");
		
		dao.create(vo);
	}
	
	//@Test
	public void testRead() throws Exception{
		BoardVO vo = dao.read(1);
		System.out.println("게시판 번호로 찾은 게시글 : "+vo);
	}
	//@Test
	public void testDelete() throws Exception{
		dao.delete(3);
		
	}
	
	//@Test
	public void testUpdate() throws Exception{
		BoardVO voUpdate = new BoardVO();
		voUpdate.setTitle("Tome to go home");
		voUpdate.setContent("It's Friday");
		voUpdate.setBno(2);
				
		dao.update(voUpdate);
		System.out.println(voUpdate);
	}
	//@Test
	public void testListAll() throws Exception{
		List<BoardVO> list = dao.listAll();
		
		for(BoardVO vo : list){
			System.out.println(vo);
		}
		
	}
	//페이징
	//@Test
	public void testListPage() throws Exception{
		//페이지에 맞게 게시글을 불러오는 지 확인해보기
		dao.listPage(2);
	}
	
	@Test
	public void testListCriteria() throws Exception{
		Criteria cri = new Criteria();
		cri.setPage(3);
		dao.listCriteria(cri);
	}
}
