package com.spring.javawebS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawebS.dao.StudyDAO;
import com.spring.javawebS.vo.MemberVO;

@Service
public class StudyServiceImple implements StudyService {

	@Autowired
	StudyDAO studyDAO;

	@Override
	public String[] getCityStringArray(String dodo) {
		String[] strArray = new String[100];
		
		if(dodo.equals("서울")) {
			strArray[0] = "강남구";
			strArray[1] = "서초구";
			strArray[2] = "마초구";
			strArray[3] = "영등포구";
			strArray[4] = "관악구";
			strArray[5] = "동작구";
			strArray[6] = "성북구";
		}
		else if(dodo.equals("경기")) {
			strArray[0] = "수원시";
			strArray[1] = "안양시";
			strArray[2] = "안성시";
			strArray[3] = "고양시";
			strArray[4] = "일산시";
			strArray[5] = "용인시";
		}
		else if(dodo.equals("충북")) {
			strArray[0] = "청주시";
			strArray[1] = "충주시";
			strArray[2] = "제천시";
			strArray[3] = "단양군";
			strArray[4] = "음성군";
			strArray[5] = "괴산군";
			strArray[6] = "보은군";
			strArray[7] = "진천시";
		}
		else if(dodo.equals("충남")) {
			strArray[1] = "천안시";
			strArray[2] = "아산시";
			strArray[3] = "홍성군";
			strArray[4] = "예산군";
			strArray[5] = "공주시";
			strArray[6] = "청양군";
			strArray[7] = "논산시";
		}
		else if(dodo.equals("경북")) {
			strArray[0] = "봉화군";
			strArray[1] = "구미시";
			strArray[2] = "영주시";
			strArray[3] = "칠곡군";
			strArray[4] = "안동시";
			strArray[5] = "포항시";
			strArray[6] = "의성군";
		}
		return strArray;
	}

	@Override
	public ArrayList<String> getCityArrayList(String dodo) {
		ArrayList<String> vos = new ArrayList<>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("서초구");
			vos.add("마초구");
			vos.add("영등포구");
			vos.add("관악구");
			vos.add("동작구");
			vos.add("성북구");
			
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("안양시");
			vos.add("안성시");
			vos.add("고양시");
			vos.add("일산시");
			vos.add("수원시");
			vos.add("용인시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("제천시");
			vos.add("단양군");
			vos.add("음성군");
			vos.add("괴산군");
			vos.add("보은군");
			vos.add("진천시");
			
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("홍성군");
			vos.add("예산군");
			vos.add("공주시");
			vos.add("청양군");
			vos.add("논산시");
		}
		else if(dodo.equals("경북")) {
			vos.add("봉화군");
			vos.add("구미시");
			vos.add("영주시");
			vos.add("칠곡군");
			vos.add("안동시");
			vos.add("포항시");
			vos.add("의성군");
			
		}
		
		return vos;
	}

	@Override
	public MemberVO getMemberMidSearch(String name) {
	
		return studyDAO.getMemberMidSearch(name);
	}

	@Override
	public ArrayList<MemberVO> getMemberMidSearch2(String name) {
		
		return studyDAO.getMemberMidSearch2(name);
	}
}
