package com.spring.javawebS.vo;

import lombok.Data;

@Data
public class GuestVO {
	private int idx;
	private String content, email, homePage,visitDate,hostIp,name;
}
