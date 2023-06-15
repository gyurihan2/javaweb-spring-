package com.spring.javawebS.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawebS.common.JavawebProvide;
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
	public int setMemberJoinOk(MemberVO vo, MultipartFile fName ) {
		int res = 0;
		
		JavawebProvide jp = new JavawebProvide();
		// 업로드된 사진을 서버 파일 시스템에 저장 처리한다.
		String oFileName = fName.getOriginalFilename();
		
		try {
			if(oFileName.equals("")) vo.setPhoto("noimage.jpg");
			else {
				// 파일 이름 중복 방지
				UUID uid = UUID.randomUUID();
				String saveFileName = uid + "_" +oFileName;
					jp.writeFile(fName, saveFileName, "member");
					vo.setPhoto(saveFileName);
				} 
			res = memberdao.setMemberJoinOk(vo);
		}	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		
		return res;
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
		return  memberdao.getMemberNameCheck(name);
	}

	@Override
	public int setMemberUpdateOk(MultipartFile fName, MemberVO vo) {
		int res =0;
		System.out.println(vo.toString());
		try {
			String oFileName = fName.getOriginalFilename();
			// 이미지가 비어있는 경우(수정을 안할경우) 공백으로 값이 넘어옴
			if(!oFileName.equals("")) {

				UUID uid = UUID.randomUUID();
				String saveFileName = uid+"_"+oFileName;
				
				JavawebProvide jp = new JavawebProvide();
				jp.writeFile(fName, saveFileName, "member");
				
				// 기존에 존재하는 파일은 삭제 처리
				if(!vo.getPhoto().equals("noimage.jpg")) {
					HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
					String realPath = request.getSession().getServletContext().getRealPath("/resources/data/member/");
					File file = new File(realPath+vo.getPhoto());
					file.delete();
				}
				// 기존 이미지를 삭제하고 신규 이미지 파일명 입력
				vo.setPhoto(saveFileName);
			}
			memberdao.setMemberUpdateOk(vo);
			res =1;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public int setMemberDelUpdateOk(String mid) {
		
		return memberdao.setMemberDelUpdateOk(mid);
	}
}
