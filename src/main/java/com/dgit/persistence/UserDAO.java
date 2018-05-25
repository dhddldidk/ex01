package com.dgit.persistence;

import com.dgit.domain.UserVO;

public interface UserDAO {
	
	//login
	public UserVO login(UserVO vo) throws Exception;
}
