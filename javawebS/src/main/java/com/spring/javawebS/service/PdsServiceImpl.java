package com.spring.javawebS.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.filefilter.CanWriteFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javawebS.dao.PdsDAO;
import com.spring.javawebS.vo.PdsVO;

@Service
public class PdsServiceImpl implements PdsService {

	@Autowired
	PdsDAO pdsDAO;

	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		
		return pdsDAO.getPdsList(startIndexNo, pageSize, part);
	}

	@Override
	public void setPdsInput(PdsVO vo, MultipartHttpServletRequest mFile) {
		try {
			
			// controller에 선어한 변수 이름으로 public String pdsInputPost(PdsVO vo, MultipartHttpServletRequest file) {
			List<MultipartFile> fileList =  mFile.getFiles("file");
			String oFileNames="";
			String sFileNames="";
			int fileSizes= 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				
				// 저장시 이름 중복 방지
				String sFileName = saveFileName(oFileName);
				
				// 파일을 서버에 저장 처리
				writeFile(file,sFileName);
				
				// 여려개 파일명을 구분자'/' 사용하여 관리
				oFileNames += oFileName+"/";
				sFileNames += sFileName+"/";
				fileSizes += file.getSize();
			}
		
			vo.setFName(oFileNames);			
			vo.setFSName(sFileNames);	
			vo.setFSize(fileSizes);
			
			pdsDAO.setPdsInput(vo);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	// 개별 파일 저장
	public void writeFile(MultipartFile file, String sFileName) throws IOException {
		byte[] data = file.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		System.out.println("realPath: "+realPath);
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		fos.write(data);
		fos.close();
	}

	// 파일명 중복 방지 처리(calendar 사용)
	public String saveFileName(String oFileName) {
		String fileName="";
		
		Calendar cal = Calendar.getInstance();
		fileName += cal.get(Calendar.YEAR);
		fileName += cal.get(Calendar.MONTH);
		fileName += cal.get(Calendar.DATE);
		fileName += cal.get(Calendar.HOUR);
		fileName += cal.get(Calendar.MINUTE);
		fileName += cal.get(Calendar.SECOND);
		fileName += cal.get(Calendar.MILLISECOND);
		
		return fileName+"_"+oFileName;
	}

	
	
	@Override
	public PdsVO getIdxSearch(int idx) {
		
		return pdsDAO.getIdxSearch(idx);
	}

	@Override
	public void setPdsDownNumCheck(int idx) {
		pdsDAO.setPdsDownNumCheck(idx);
		
	}
	
	
	
	
	
}
