package com.dgit.domain;

public class LoginDTO {
	//회원이 로그인 했나 안했나 확인하기 위해 
	private String uid;
	private String uname;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Override
	public String toString() {
		return "LoginDTO [uid=" + uid + ", uname=" + uname + "]";
	}

}
