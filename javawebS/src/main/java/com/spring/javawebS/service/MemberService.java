package com.spring.javawebS.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javawebS.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickCheck(String nickName);

	public int setMemberJoinOk(MemberVO vo, MultipartFile fName);

	public void setMemberVisitProcess(MemberVO vo);

	public ArrayList<MemberVO> getMemberList();

	public void setMemberPwdUpdate(String mid, String pwd);

	public MemberVO getMemberNameCheck(String name);

	public int setMemberUpdateOk(MultipartFile fName, MemberVO vo);

	public int setMemberDelUpdateOk(String mid);
	
}
