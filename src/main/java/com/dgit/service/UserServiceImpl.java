package com.dgit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.UserVO;
import com.dgit.persistence.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO dao;
	
	@Override
	public UserVO login(UserVO vo) throws Exception {
		
		return dao.login(vo);
	}

}
