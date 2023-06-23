package com.spring.javawebS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawebS.vo.PdsVO;

public interface PdsDAO {

	public List<PdsVO> getPdsList(@Param("startIndexNo") int startIndexNo, @Param("pageSize")int pageSize, @Param("part")String part);

	public int totRecCnt(@Param("part") String part);

	public void setPdsInput(@Param("vo")PdsVO vo);

	public void setPdsDownNumCheck(@Param("idx") int idx);

	public PdsVO getIdxSearch(@Param("idx") int idx);

}
