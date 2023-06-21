package com.spring.javawebS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawebS.common.ARIAUtil;
import com.spring.javawebS.common.SecurityUtil;
import com.spring.javawebS.service.MemberService;
import com.spring.javawebS.service.StudyService;
import com.spring.javawebS.vo.MailVO;
import com.spring.javawebS.vo.MemberVO;
import com.spring.javawebS.vo.UserVO;

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
	
	@RequestMapping(value = "/ajax/ajaxForm", method = RequestMethod.GET)
	public String ajaxFormGet() {
		
		return "study/ajax/ajaxForm";
	}
	
	@RequestMapping(value = "/ajax/ajaxTest1", method = RequestMethod.POST,produces = "application/text; charset=utf-8")
	@ResponseBody
	public String ajaxTest1Post( int idx) {
		idx = (int)(Math.random()*idx)+1;
		return idx+"Have a good time(안녕하세요)";
	}
	
	
	@RequestMapping(value = "/ajax/ajaxTest2_1", method = RequestMethod.GET)
	public String ajaxTest2_1Get() {
		
		return "study/ajax/ajaxTest2_1Get";
	}
	@RequestMapping(value = "/ajax/ajaxTest2_1", method = RequestMethod.POST)
	@ResponseBody
	public String[] ajaxTest2_1Post(String dodo) {
		
		return studyService.getCityStringArray(dodo);
	}
	
	// 객체 배열(ArrayList) 전달
	@RequestMapping(value = "/ajax/ajaxTest2_2", method = RequestMethod.GET)
	public String ajaxTest2_2Get() {
		
		return "study/ajax/ajaxTest2_2Get";
	}
	@RequestMapping(value = "/ajax/ajaxTest2_2", method = RequestMethod.POST)
	@ResponseBody
	public  ArrayList<String> ajaxTest2_2Post(String dodo) {
		
		return studyService.getCityArrayList(dodo);
	}
	
	//객체 배열(HashMap) 전달
	@RequestMapping(value = "/ajax/ajaxTest2_3", method = RequestMethod.GET)
	public String ajaxTest2_3Get() {
		
		return "study/ajax/ajaxTest2_3Get";
	}
	@RequestMapping(value = "/ajax/ajaxTest2_3", method = RequestMethod.POST)
	@ResponseBody
	public  HashMap<Object, Object> ajaxTest2_3Post(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		vos = studyService.getCityArrayList(dodo);
		
		HashMap<Object, Object> map = new HashMap<>();
		map.put("city", vos);
		
		return map;
	}
	
	//DB를 활용한 값의 전달
	@RequestMapping(value = "/ajax/ajaxTest3", method = RequestMethod.GET)
	public String ajaxTest3Get() {
		
		return "study/ajax/ajaxTest3";
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.POST)
	@ResponseBody
	public MemberVO ajaxTest3_1Post(String name) {
		
		return studyService.getMemberMidSearch(name);
	}
	
	//DB(vos)를 활용한 값의 전달
	
	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<MemberVO> ajaxTest3_2Post(String name) {
		System.out.println(name);
		return studyService.getMemberMidSearch2(name);
	}
	
	// 파일 업로드폼
	@RequestMapping(value = "/fileUpload/fileUploadForm", method = RequestMethod.GET)
	public String fileUploadGet(Model model,HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study");
		
		
		
		String[] files = new File(realPath).list();
		
		model.addAttribute("files",files);
		
		return "study/fileUpload/fileUploadForm";
	}
	// 파일 업로드 처리
	@RequestMapping(value = "/fileUpload/fileUploadForm", method = RequestMethod.POST)
	public String fileUploadPost(MultipartFile fName, String mid) {
		//System.out.println("fName: " + fName.getOriginalFilename());
		//System.out.println("mid: " + mid);
		
		int res = studyService.fileUpload(fName, mid);
		
		if(res == 1) return "redirect:/message/filUploadOk";
		else return "redirect:/message/filUploadNo";
		
	}
	
	@RequestMapping(value = "/fileUpload/fileDelete", method = RequestMethod.POST)
	@ResponseBody
	public String fileDeletePost(HttpServletRequest request,
			@RequestParam(name="fName",defaultValue = "",required = false) String fName) {
		
		String res ="0";
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		
		File file = new File(realPath+fName);
		
		if(file.exists()) {
			if(file.delete()) res = "1";
		}
		
		return res;
	}
	
	
	// 파일 다운로드 메소드
	@RequestMapping(value = "/fileUpload/fileDownAction", method = RequestMethod.GET)
	public void fileDownActionGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fName = request.getParameter("fName");
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		
		File file = new File(realPath+fName);
		
		// 다운로드 파일을 윈도우 형식으로
		String downFileName =  new String(fName.getBytes("UTF-8"),"8859_1");
		// 헤더에 추가
		response.setHeader("Content-Disposition", "attachment:fileName="+downFileName);
		
		
		FileInputStream fis = new FileInputStream(file);
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] buffer = new byte[2048];
		int data = 0;
		
		while((data = fis.read(buffer,0,buffer.length)) != -1) {
			sos.write(buffer,0,data);
		}
		
		sos.flush();
		sos.close();
		fis.close();
		
		//return "study/fileUpload/fileUploadForm";
	}
	
	// validator를 이용한 Backend 유효성 검사
	@RequestMapping(value = "/validator/validatorForm", method = RequestMethod.GET)
	public String validatorFormGet() {
		return "study/validator/validatorForm";
	}
	
	// validator를 이용한 Backend 유효성 검사하기 - 자료 검사후 DB에 저장하기
	@RequestMapping(value = "/validator/validatorForm", method = RequestMethod.POST)
	public String validatorFormPost(
			@Validated UserVO vo, BindingResult bindingResult) {
			System.out.println("vo:" + vo.toString());
		
		if(bindingResult.hasFieldErrors()) { //hasFieldErrors() true : 오류 확인
			List<ObjectError> list = bindingResult.getAllErrors();
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println(list);
			for(ObjectError e : list) {
				System.out.println("메시지 : " + e.getDefaultMessage());
			}
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("error: " + bindingResult.hasErrors());
			return "redirect:/message/validatorNo";
		}
		
		int res = studyService.setUserInput(vo);
		
		if(res == 1 ) return "redirect:/message/userInputOk";
		else return "redirect:/message/userInputNo";
	}
	
	// validator를 이용한 Backend 유효성 검사
	@RequestMapping(value = "/validator/validatorList", method = RequestMethod.GET)
	public String validatorListGet(Model model) {
		ArrayList<UserVO> vos = studyService.getUserList();
		model.addAttribute("vos", vos);
		
		return "study/validator/validatorList";
	}
	// validator 유저 삭제
	@RequestMapping(value = "/validator/validatorDelete2", method = RequestMethod.GET)
	public String validatorDeleteGet(int idx) {
	
		studyService.setUserDelete(idx);
	
		return "redirect:/message/validatorDeleteOk";
	}
	
}
