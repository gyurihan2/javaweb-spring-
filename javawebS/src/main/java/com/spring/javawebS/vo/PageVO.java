package com.spring.javawebS.vo;

import lombok.Data;

@Data
public class PageVO {

	//page
	private int pag=1;
	private int pageSize=5;
	private int totRecCnt;
	private int totPage;
	private int startIndexNo;
	private int curScrStartNo;
	
	// block
	private int blockSize;
	private int curBlock;
	private int lastBlock;
	
	private String part;
}
