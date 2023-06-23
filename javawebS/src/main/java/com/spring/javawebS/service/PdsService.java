package com.spring.javawebS.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javawebS.vo.PdsVO;

public interface PdsService {

	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part);

	public void setPdsInput(PdsVO vo, MultipartHttpServletRequest file);

	public void setPdsDownNumCheck(int idx);

	public PdsVO getIdxSearch(int idx);

}
