package com.spring.javawebS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawebS.pagination.PageProcess;
import com.spring.javawebS.service.BoardService;
import com.spring.javawebS.vo.BoardVO;
import com.spring.javawebS.vo.PageVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	PageProcess pageProcess;
	
	//Board 게시판 홈
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "1", required = false) int pageSize) {
		
		PageVO pageVO =pageProcess.totRecCnt(pag, pageSize, "board", "", "");
		
		List<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(),pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		
		return "board/boardList";
	}
	
	// Board 글쓰기
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	// Board 글쓰기
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVO vo) {
		
		//content에 이미지가 저장되어 있다면 저장된 이미지만 골라서 /resources/data/board/ 폴더에 저장 시켜준다.
		boardService.imgCheck(vo.getContent());
		
		// 이미지들의 모든 복사 작업을 마치면 ckeditor폴더 경로를 board폴더 경로로 변경한다.
		vo.setContent(vo.getContent().replace("/data/ckeditor", "/data/board"));
		
		//content안의 내용정리가 끝나면 변경된 VO를 db에 저장시켜준다.
		
		int res = boardService.setBoardInput(vo);
		
		if(res ==1) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
	}
	
	// 글내용 상세 보기
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(Model model,HttpSession session,
			@RequestParam(name="idx",defaultValue = "0", required = false)  int idx,
			@RequestParam(name="pag",defaultValue = "0", required = false)  int pag,
			@RequestParam(name="pageSize",defaultValue = "0", required = false)  int pageSize) {
		
		//글 조회수 1씩 증가시키기(조회수 중복 방지 - 세션 처리('board + 고유번호'를 추가)
		ArrayList<String> contentIdx = (ArrayList)session.getAttribute("sContentIdx");
		
		if(contentIdx == null) contentIdx = new ArrayList<String>();
		
		String imsiContentIdx = "board"+ idx;
		
		// 첫 방문일경우
		if(!contentIdx.contains(imsiContentIdx)) {
			contentIdx.add(imsiContentIdx);
			boardService.setBoardReadNum(idx);
		}
		
		session.setAttribute("sContentIdx", contentIdx);
		
		
		BoardVO vo = boardService.getBoardContent(idx);
		
		//이전글 다음글 가져오기
		ArrayList<BoardVO> pnVos = boardService.getPrevNext(idx);
		model.addAttribute("pnVos", pnVos);
		System.out.println(pnVos.toString());
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize",pageSize);
		
		
		
		return "board/boardContent";
	}

	@RequestMapping(value = "boardGoodCheckAjax",method = RequestMethod.POST)
	@ResponseBody
	public int boardGoodCheckPost(HttpSession session,
			@RequestParam(name ="idx", defaultValue = "-1", required = false) int idx) {
		
		String mid = (String) session.getAttribute("sMid");
		
		int res = boardService.setBoardGood(mid, idx);
		
		
		return res;
	}
	
}
