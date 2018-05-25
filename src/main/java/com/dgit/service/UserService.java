package com.dgit.service;

import com.dgit.domain.UserVO;

public interface UserService {
	//login
		public UserVO login(UserVO vo) throws Exception;
}
