package com.spring.javawebS.service;

import java.util.List;

import com.spring.javawebS.vo.GuestVO;

public interface GuestService {

	public List<GuestVO> getGuestList(int startIndexNo, int pageSize);

	public int setGuestInput(GuestVO vo);

	public int getAdminChk(String mid, String pwd);

	public int totRecCnt();

	
}
