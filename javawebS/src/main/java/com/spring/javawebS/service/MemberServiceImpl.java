package com.spring.javawebS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawebS.dao.MemberDAO;
import com.spring.javawebS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberdao;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
		return memberdao.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNickCheck(String nickName) {
		return memberdao.getMemberNickCheck(nickName);
	}

	@Override
	public int setMemberJoinOk(MemberVO vo) {
		// 업로드된 사진을 서버 파일 시스템에 저장 처리한다.
		
		return memberdao.setMemberJoinOk(vo);
	}

	@Override
	public void setMemberVisitProcess(MemberVO vo) {
		memberdao.setMemberVisitProcess(vo);
	}

	@Override
	public ArrayList<MemberVO> getMemberList() {
		
		return memberdao.getMemberList();
	}

	@Override
	public void setMemberPwdUpdate(String mid, String pwd) {
		memberdao.setMemberPwdUpdate(mid,pwd);
	}

	@Override
	public MemberVO getMemberNameCheck(String name) {
		// TODO Auto-generated method stub
		return  memberdao.getMemberNameCheck(name);
	}
}
