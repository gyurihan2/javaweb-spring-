package com.spring.javawebS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javawebS.dao.BoardDAO;
import com.spring.javawebS.vo.BoardReplyVO;
import com.spring.javawebS.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardDAO boardDAO;

	@Override
	public List<BoardVO> getBoardList(int startIndexNo, int pageSize) {
		return boardDAO.getBoardList(startIndexNo,pageSize);
	}

	@Override
	public int setBoardInput(BoardVO vo) {
		
		return boardDAO.setBoardInput(vo);
	}

	@Override
	public void imgCheck(String content) {
		//     01234567890123456789012345678901234567890123456
		//<img src="/javawebS/data/ckeditor/23061616.jpg"
		
		// content안에 그림파일이 존재한다면 그림을 /data/board/ 폴더로 복사 처리한다. 없으면 돌려보낸다.
		if(content.indexOf("src=\"/") == -1) return;
		
		HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position=20;
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		
		boolean sw=true;
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			String origFilePath = realPath+"ckeditor/"+imgFile;
			String copyFilePath = realPath+"board/"+imgFile;
			System.out.println("origFilePath:" +origFilePath);
			System.out.println("copyFilePath:" +copyFilePath);
			//ckeditor 파일을 board폴더로 복사
			fileCopyCheck(origFilePath,copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw=false;
			
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
			
		}
			
		
	}

	// 원본 파일을 다른곳으로 복사 처리
	private void fileCopyCheck(String origFilePath, String copyFilePath) {
		try {
			FileInputStream fis = new FileInputStream(new File(origFilePath));
			
			FileOutputStream fos = new FileOutputStream(new File(copyFilePath));
			
			
			byte[] bytes = new byte[2048];
			int cnt=0;
			while((cnt = fis.read(bytes)) != -1) {
			
				fos.write(bytes,0,cnt);
			}
			
			fos.flush();
			fos.close();
			fis.close();
			
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		
		return boardDAO.getBoardContent(idx);
	}

	@Override
	public void setBoardReadNum(int idx) {
		boardDAO.setBoardReadNum(idx);
	}

	@Override
	public ArrayList<BoardVO> getPrevNext(int idx) {
		
		return boardDAO.getPrevNext(idx);
	}


	@Override
	public int setBoardGood(String mid, int idx) {
		// 해당 게시물의 좋아요 상태 확인
		BoardVO boardGood = boardDAO.getBoardGoodStatus(mid,idx);
		return 0;
	}

	@Override
	public List<BoardVO> getBoardListSearch(int startIndexNo, int pageSize, String search, String searchString) {
		return boardDAO.getBoardListSearch(startIndexNo,pageSize,search,searchString);
	}

	@Override
	public void imgDelete(String content) {
		if(content.indexOf("src=\"/") == -1) return;
		
		HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position=21;
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		System.out.println(nextImg);
		boolean sw=true;
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			
			String origFilePath = realPath+"board/"+imgFile;
			
			fileDelte(origFilePath);
		
			if(nextImg.indexOf("src=\"/") == -1) sw=false;
			
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
			
		}
	}

	// 서버에 저장된 사진 이미지 삭제
	public void fileDelte(String origFilePath) {
		File file = new File(origFilePath);
		if(file.exists()) file.delete();
	}

	@Override
	public int setBoardDelete(int idx) {
		
		return boardDAO.setBoardDelete(idx);
	}

	@Override
	public void imgCheckUpdate(String content) {
		if(content.indexOf("src=\"/") == -1) return;
		
		HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position=21;
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		
		boolean sw=true;
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			String origFilePath = realPath+"board/"+imgFile;
			String copyFilePath = realPath+"ckeditor/"+imgFile;
			System.out.println("origFilePath:" +origFilePath);
			System.out.println("copyFilePath:" +copyFilePath);
			//ckeditor 파일을 board폴더로 복사
			fileCopyCheck(origFilePath,copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw=false;
			
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
			
		}
		
	}

	@Override
	public int setBoardUpdate(BoardVO vo) {
		
		return  boardDAO.setBoardUpdate(vo);
	}

	@Override
	public String getMaxGroupId(int boardIdx) {
		
		return boardDAO.getMaxGroupId(boardIdx);
	}

	@Override
	public void setBoardReplyInput(BoardReplyVO replyVO) {
		boardDAO.setBoardReplyInput(replyVO);
	}

	@Override
	public List<BoardReplyVO> setBoardReply(int idx) {
		return boardDAO.setBoardReply(idx);
	}

	@Override
	public void setBoardReplyDelete(int replyIdx,int level,int groupId, int boardIdx) {
	boardDAO.setBoardReplyDelete(replyIdx,level,groupId,boardIdx);
		
	}

	@Override
	public BoardReplyVO getBoardReplyIdx(int replyIdx) {
	
		return boardDAO.getBoardReplyIdx(replyIdx);
	}

	@Override
	public void setBoardReplyUpdate(int idx, String content, String hostIp) {
		boardDAO.setBoardReplyUpdate(idx,content,hostIp);
		
	}

	
	
}
