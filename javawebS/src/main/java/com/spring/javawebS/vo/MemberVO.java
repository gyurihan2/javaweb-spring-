package com.spring.javawebS.vo;

import lombok.Data;

@Data
public class MemberVO {
	private int idx,point,level,visitCnt,todayCnt;
	private String mid,pwd,nickName,name,gender,birthday,tel,address,email,homePage,job,
			hobby,photo,content,userInfor,userDel,startDate,lastDate;
	
	private String hobbys;
}
