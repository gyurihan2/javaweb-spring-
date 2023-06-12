package com.spring.javawebS;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawebS.common.ARIAUtil;
import com.spring.javawebS.common.SecurityUtil;
import com.spring.javawebS.service.StudyService;

@Controller
@RequestMapping("/study")
public class StudyController {

	@Autowired
	StudyService studyService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = "/password/sha256", method = RequestMethod.GET)
	public String sha256Get() {
		
		return "study/password/sha256";
	}
	
	// SHA256 암호화 처리
	@RequestMapping(value = "/password/sha256", method = RequestMethod.POST,produces = "application/text; charset=utf8")
	@ResponseBody
	public String sha256Post(String pwd) {
		String encPwd =  SecurityUtil.encryptSHA256(pwd);
		
		pwd ="원본 비밀번호: "+pwd +"/ 암호화된 비밀번호: " +encPwd;
		return pwd;
	}
	
	// Aria 암호화 페이지
	@RequestMapping(value = "/password/aria", method = RequestMethod.GET)
	public String ariaGet() {
		return "study/password/aria";
	}
	
	//Aria 암호화 처리
	@RequestMapping(value = "/password/aria", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		String encPwd ="";
		String decPwd="";
		
		encPwd = ARIAUtil.ariaEncrypt(pwd);
		decPwd = ARIAUtil.ariaDecrypt(encPwd);
		
		pwd ="원본 비밀번호: "+pwd +"/ 암호화된 비밀번호: " +encPwd +" / 복호화된 비밀번호: " + decPwd;
		return pwd;
	}
	
	// bCryptPassword 암호화 페이지
	@RequestMapping(value = "/password/bCryptPassword", method = RequestMethod.GET)
	public String bCryptPasswordGet() {
		return "study/password/bCryptPassword";
	}
	
	//bCryptPassword 암호화 처리
	@RequestMapping(value = "/password/bCryptPassword", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String bCryptPasswordPost(String pwd) {
		String encPwd ="";
		encPwd = passwordEncoder.encode(pwd);

		pwd ="원본 비밀번호: "+pwd +"/ 암호화된 비밀번호: " +encPwd;
		return pwd;
	}
}
