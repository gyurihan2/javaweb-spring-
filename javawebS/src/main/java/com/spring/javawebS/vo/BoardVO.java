package com.spring.javawebS.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private String mid;
	private String nickName;
	private String title;
	private String email;
	private String homePage;
	private String content;
	private int readNum;
	private String hostIp;
	private String openSw;
	private String wDate;
	private int good;
	
	// 신규 레코드(게시판) New 이미지 표시
	private int day_diff; //날짜 차이 계산필드(1일차)
	private int hour_diff; // 24시간 차이
	
	// 이전글/다음글을 위한 변수 설정
	private String preTitle;
	private int preIdx;
	private String nextTitle;
	private int nextIdx;
	
	//댓글의 수
	private int replyCount;

}
