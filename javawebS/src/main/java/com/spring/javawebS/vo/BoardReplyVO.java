package com.spring.javawebS.vo;

import lombok.Data;

@Data
public class BoardReplyVO {
	private int idx,boardIdx;
	private String mid,nickName,wDate,hostIp,content;
	
	// 대댓글 처리를 위한 변수
	private int groupId,level;
}
