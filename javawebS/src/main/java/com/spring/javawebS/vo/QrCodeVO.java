package com.spring.javawebS.vo;

import lombok.Data;

@Data
public class QrCodeVO {
	private String mid, name, email;
	
	private String moveUrl;
	
	private String movieName, movieDate, movieTime, publishNow;
	private int movieAdult, movieChild;
	
	private String movieTemp;
	private String qrCodeName;
}
