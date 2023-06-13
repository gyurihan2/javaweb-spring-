package com.spring.javawebS;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawebS.common.ARIAUtil;
import com.spring.javawebS.common.SecurityUtil;
import com.spring.javawebS.service.MemberService;
import com.spring.javawebS.service.StudyService;
import com.spring.javawebS.vo.MailVO;
import com.spring.javawebS.vo.MemberVO;

@Controller
@RequestMapping("/study")
public class StudyController {

	@Autowired
	StudyService studyService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
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
	
	// 메일 연습 폼
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.GET)
	public String mailFormGet(Model model) {
		ArrayList<MemberVO> vos =  memberService.getMemberList();
		model.addAttribute("vos",vos);
		return "study/mail/mailForm";
	}
	
	// 메일 전송
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.POST)
	public String mailFormPost(MailVO mailVO, HttpServletRequest request) throws MessagingException {
		
		String toMail = mailVO.getToMail();
		String title = mailVO.getTitle();
		String content = mailVO.getContent();
		
		// 메일 전송을 위한 객체: MimeMessage(), MimeMessageHelper()
		MimeMessage message =  mailSender.createMimeMessage();
		
		//보관함
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true , "UTF-8");
		
		//메일 보관함에 회원이 보내온 메시지들의 정보를 모두 저장시킨 후 작업 
		messageHelper.setTo(toMail.split(";"));
		
		messageHelper.setSubject(title);
		
		//메시지 보관함의 내용(content)에 필요한 정보를 추가로 담아서 전송 시킬수 있도록 한다.
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>CJ Green에서 보냅니다.</h3><hr><br>";
		
		// cid 예약어
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 :<a href='http://49.142.157.251:9090/javaweb14J/MainHomepage.mem'>영화보는날</a> </p>";
		content += "<hr>";
		
		messageHelper.setText(content, true);
		
		// 이미지 경로를 별도로 표시시켜준다. 그다음 다시 보관함에 담아준다.
		FileSystemResource file = new FileSystemResource("C:\\Users\\green\\git\\javaweb-spring-\\javawebS\\src\\main\\webapp\\resources\\images\\main.jpg");
		messageHelper.addInline("main.jpg", file);
		
		// 첨부 파일 보내기(서버 파일 시스템에 존재하는 파일을 보내기)
		file = new FileSystemResource("C:\\Users\\green\\git\\javaweb-spring-\\javawebS\\src\\main\\webapp\\resources\\images\\chicago.jpg");
		messageHelper.addAttachment("test.jpg", file);
		
		// 첨부 파일 보내기(서버 파일 시스템에 존재하는 파일을 보내기)
		file = new FileSystemResource("C:\\Users\\green\\git\\javaweb-spring-\\javawebS\\src\\main\\webapp\\resources\\images\\main.zip");
		messageHelper.addAttachment("test.zip", file);
		
		// 파일 시스템에 설계한 파일이 저장된 실제 경로(realPath) 경로 이름이 중복이면 하나만
		file = new FileSystemResource(request.getRealPath("/resources/images/paris.jpg"));
		messageHelper.addAttachment("test1.jpg", file);
		
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/paris.jpg"));
		messageHelper.addAttachment("paris.jpg", file);
		
		
		//메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}
	
	@RequestMapping(value = "/uuid/uuidForm", method = RequestMethod.GET)
	public String uuidFormGet() {
		
		return "study/uuid/uuidForm";
	}
	
	@RequestMapping(value = "/uuid/uuidForm", method = RequestMethod.POST)
	@ResponseBody
	public String uuidFormPost() {
		UUID uid = UUID.randomUUID();
		return uid.toString();
	}
	
}
