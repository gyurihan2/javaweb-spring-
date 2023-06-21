package com.spring.javawebS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawebS.vo.BoardReplyVO;
import com.spring.javawebS.vo.BoardVO;

public interface BoardDAO {

	public int totRecCnt();

	public List<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public int setBoardInput(@Param("vo")BoardVO vo);

	public BoardVO getBoardContent(@Param("idx") int  idx);

	public void setBoardReadNum(@Param("idx") int idx);

	public ArrayList<BoardVO> getPrevNext(@Param("idx")int idx);

	public BoardVO getBoardGoodStatus(@Param("mid")String mid, @Param("idx")int idx);

	public int totRecCntSearch(@Param("search")String search, @Param("searchString")String searchString);

	public List<BoardVO> getBoardListSearch(@Param("startIndexNo")int startIndexNo, @Param("pageSize")int pageSize, @Param("search")String search, @Param("searchString")String searchString);

	public int setBoardDelete(@Param("idx")int idx);

	public int setBoardUpdate(@Param("vo")BoardVO vo);

	public String getMaxGroupId(@Param("boardIdx")int boardIdx);

	public void setBoardReplyInput(@Param("replyVO") BoardReplyVO replyVO);

	public List<BoardReplyVO> setBoardReply(@Param("idx") int idx);

	public void setBoardReplyDelete(@Param("replyIdx")int replyIdx, @Param("level")int level, @Param("groupId")int groupId, @Param("boardIdx")int boardIdx);

	public BoardReplyVO getBoardReplyIdx(int replyIdx);

	public void setBoardReplyUpdate(@Param("idx")int idx, @Param("content")String content, @Param("hostIp")String hostIp);

	

}
