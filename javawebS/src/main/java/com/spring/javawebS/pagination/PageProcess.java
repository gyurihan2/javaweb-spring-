package com.spring.javawebS.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawebS.dao.GuestDAO;
import com.spring.javawebS.vo.PageVO;

@Service
public class PageProcess {

	@Autowired
	GuestDAO guestDAO;
	
	public PageVO totRecCnt(int pag, int pageSize, String section, String part, String searchString) {
		PageVO pageVO = new PageVO();
		
		int totRecCnt = 0;
		
		if(section.equals("guest")) totRecCnt = guestDAO.totRecCnt();
		//else if(section.equals("member")) totRecCnt.memberDAO.totRecCnt();
		
		
		
		// page 설정
		int totPage = totRecCnt%pageSize == 0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
		int startIndexNo = (pag-1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		// block 설정
		int blockSize = 5;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage -1) / blockSize;
		
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setTotRecCnt(totRecCnt);
		pageVO.setTotPage(totPage);
		pageVO.setStartIndexNo(startIndexNo);
		pageVO.setCurScrStartNo(curScrStartNo);
		pageVO.setCurBlock(curBlock);
		pageVO.setBlockSize(blockSize);
		pageVO.setLastBlock(lastBlock);
		pageVO.setPart(part);
		
		return pageVO;
	}

}
