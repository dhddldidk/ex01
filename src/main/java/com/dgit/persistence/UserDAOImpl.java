package com.dgit.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {
	
	//@Autowired 와 @Inject는 거의 같은 기능임
	//둘 중 아무거나 써도 상관없음
	//but 출처가 다름(@Autowired는 spring 에서 만들어진거  @Inject는 자바에서 만들어진거 )
	@Autowired
	SqlSession session;
	
	private static final String namespace = "com.dgit.mapper.UserMapper";

	@Override
	public UserVO login(UserVO vo) throws Exception {
		
		return session.selectOne(namespace+".login", vo);
	}

}
