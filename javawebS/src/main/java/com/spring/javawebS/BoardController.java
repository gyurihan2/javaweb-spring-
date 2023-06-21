package com.spring.javawebS;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawebS.pagination.PageProcess;
import com.spring.javawebS.service.BoardService;
import com.spring.javawebS.vo.BoardReplyVO;
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
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		
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
		vo.setContent(vo.getContent().replace("/data", "/board"));
		
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
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize",pageSize);
		
		// 댓글 가져 오기(replyVOS) 출력 정렬: 1차 groupId 오름 차순 2차 idx 오름 차순
		List<BoardReplyVO> replyVOS = boardService.setBoardReply(idx);
		model.addAttribute("replyVOS", replyVOS);

		
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
	
	//게시글 검색 처리
	@RequestMapping(value = "boardSearch",method = RequestMethod.POST)
	public String boardSearchPost(String search, String searchString, Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		 
	
		PageVO pageVO =pageProcess.totRecCnt(pag, pageSize, "board", "", "");
		List<BoardVO> vos = boardService.getBoardListSearch(pageVO.getStartIndexNo(),pageSize,search,searchString);
		
		String searchTitle ="";
		if(search.equals("title"))searchTitle="글제목";
		else if(search.equals("name"))searchTitle="작성자";
		else if(search.equals("content"))searchTitle="글내용";
		
		
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("searchTitle", searchTitle);
		model.addAttribute("searchString", searchString);
		model.addAttribute("searchCount", vos.size());
		
		return "board/boardSearch";
	}
	
	@RequestMapping(value = "boardDelete",method = RequestMethod.GET)
	public String boardboardDeleteGet(int idx,int pag, int pageSize,HttpSession session) {
		// 게시글에 사진이 존재한다면 서버에 있는 사진 파일을 먼저 삭제처리한다.
		BoardVO vo = boardService.getBoardContent(idx);
		
		String mid = (String) session.getAttribute("sMid");
		if(!vo.getMid().equals(mid)) return "redirect:/";
		
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		//DB에 게시물 삭제
		int res = boardService.setBoardDelete(idx);
		
		if(res == 1)return "redirect:/message/boardDelteOk";
		else return "redirect:/message/boardDelteNo?idx="+idx+"&pag="+pag+"&pageSize="+pageSize;

	}
	// 게시물 수정 폼
	@RequestMapping(value = "boardUpdate",method = RequestMethod.GET)
	public String boardUpdateGet(Model model,
			@RequestParam(name="idx", defaultValue = "1", required = false) int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		
		// 수정 창으로 이동시에는 먼저 원본파일에 그림 파일이 있다면, 현재 폴더(board)의 그림 파일들을 ckeditor폴더로 복사 시켜둔다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheckUpdate(vo.getContent());
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/boardUpdate";
	}
	
	// 게시글 변경 처리
	@RequestMapping(value = "boardUpdate",method = RequestMethod.POST)
	public String boardUpdatePost(BoardVO vo, Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		
		BoardVO origVO = boardService.getBoardContent(vo.getIdx());
		
		//수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요 X
		//content의 내용이 조금이라도 변경된것이 있다면 내용을 수정 처리한다.
		if(!origVO.getContent().equals(vo.getContent())) {
			// 수정하기 버튼을 클릭하게되면, 기존의 board폴더에 저장된, 현재 content의 그림파일 모드 삭제 시킨다.
			if(origVO.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(origVO.getContent());
			
			// board폴더에는 이미 그림파일이 삭제 되어 있으므로(ckeditor폴더로 복사해 놓았음), vo.getContent()에 있는 그림 파일경로로 board를 ckeditor 경로로 변경
			vo.setContent(vo.getContent().replace("/board/", "/data/"));
			
			//앞의 작업이 끝나면  파일을 처음 업로드한것과 같은 작업을 처리 시켜준다.
			boardService.imgCheck(vo.getContent());
			
			
			
			// 이미지들의 모든 복사 작업을 마치면 ckeditor폴더 경로를 board폴더 경로로 변경한다.
			vo.setContent(vo.getContent().replace("/data", "/board"));
		}
		
		// content의 내용과 그림파일까지 잘 정비된 VO를 DB에 업데이트
		int res = boardService.setBoardUpdate(vo);
		
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("idx", vo.getIdx());
		
		if(res == 1) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo";
	}
	
	// 댓글 달기 
	@RequestMapping(value = "boardReplyInput", method = RequestMethod.POST)
	@ResponseBody
	public String boardReplyInputPost(BoardReplyVO replyVO) {
		String res="1";
		
		// 해당 게시물에 댓글 확인(그룹 아이디의 최대값 확인)
		String strGroupId = boardService.getMaxGroupId(replyVO.getBoardIdx());
		
		// 원본글의 댓글 처리
		if(strGroupId != null) replyVO.setGroupId(Integer.parseInt(strGroupId)+1);
		else replyVO.setGroupId(0);
		
		replyVO.setLevel(0);
		
		boardService.setBoardReplyInput(replyVO); 
		
		return res;
	}
	
	// 대댓글 입력 처리 boardReplyInput
	@RequestMapping(value = "boardReplyInput2", method = RequestMethod.POST)
	@ResponseBody
	public String boardReplyInput2Post(BoardReplyVO replyVO) {
		replyVO.setLevel(replyVO.getLevel()+1);
		
		boardService.setBoardReplyInput(replyVO);
		
		return "";
	}
	
	// 댓글 삭제 boardReplyDelete
	@RequestMapping(value = "boardReplyDelete", method = RequestMethod.POST)
	@ResponseBody
	public String boardReplyDeletePost(
			@RequestParam(name="replyIdx",defaultValue = "0", required = false) int replyIdx,
			@RequestParam(name="level",defaultValue = "0", required = false) int level) {
		
		BoardReplyVO replyVO = boardService.getBoardReplyIdx(replyIdx);
		
		boardService.setBoardReplyDelete(replyVO.getIdx(),replyVO.getLevel(),replyVO.getGroupId(),replyVO.getBoardIdx());
		return "1";
	}
	
	// 댓글 수정 boardReplyDelete
	@RequestMapping(value = "boardReplyUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String boardReplyUpdatePost(
			@RequestParam(name="idx",defaultValue = "0", required = false) int idx,
			@RequestParam(name="content",defaultValue = "", required = false) String content,
			@RequestParam(name="hostIp",defaultValue = "", required = false) String hostIp) {
		
		
		
		boardService.setBoardReplyUpdate(idx,content,hostIp);
		return "1";
	}
	
}
