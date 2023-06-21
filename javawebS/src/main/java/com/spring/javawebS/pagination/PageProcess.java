package com.spring.javawebS.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawebS.dao.BoardDAO;
import com.spring.javawebS.dao.GuestDAO;
import com.spring.javawebS.vo.PageVO;

@Service
public class PageProcess {

	@Autowired
	GuestDAO guestDAO;
	
	@Autowired
	BoardDAO boardDAO;
	
	public PageVO totRecCnt(int pag, int pageSize, String section, String part, String searchString) {
		PageVO pageVO = new PageVO();
		
		int totRecCnt = 0;
		
		if(section.equals("guest")) totRecCnt = guestDAO.totRecCnt();
		else if(section.equals("board")) {
			if(part.equals("")) totRecCnt = boardDAO.totRecCnt();
			else {
				String search =part;
				totRecCnt =boardDAO.totRecCntSearch(search,searchString);
			}
		}
		//else if(section.equals("board")) totRecCnt = memberDAO.totRecCnt();
		
		
		
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
		pageVO.setSearchString(searchString);
		pageVO.setSearch(part);
		
		return pageVO;
	}

}
