package com.spring.javawebS;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawebS.service.MemberService;
import com.spring.javawebS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;

	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) { 
		Cookie[] cookies = request.getCookies();
		
		for(Cookie tmp:cookies){		
			if(tmp.getName().equals("cMid")) {
				request.setAttribute("mid", tmp.getValue());
				break;
			}
		}
		return "member/memberLogin";
	}
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public String memberLoginPost(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="pwd", defaultValue = "", required = false) String pwd,
			@RequestParam(name="idSave", defaultValue = "", required = false) String idSave) { 
		
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) {
			// 회원 인증 처리된 경우는 ? strLevel, session에 저장 쿠키저장, 방문자수, 방문포인트 증가
			String strLevel="";
			
			if(vo.getLevel() == 0) strLevel="관리자";
			else if(vo.getLevel() == 1) strLevel="우수회원";
			else if(vo.getLevel() == 2) strLevel="정회원";
			else if(vo.getLevel() == 3) strLevel="준회원";
			
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("strLevel", strLevel);
			session.setAttribute("sMid", vo.getMid());
			session.setAttribute("sNickName", vo.getNickName());
			
			if(idSave.equals("on")) {
				Cookie cookie = new Cookie("cMid", vo.getMid());
				cookie.setMaxAge(60*60*24*7);
				response.addCookie(cookie);
			}
			else {
				Cookie[] cookies = request.getCookies();
				
				for(Cookie tmp:cookies){		
					if(tmp.getName().equals("cMid")) {
						tmp.setMaxAge(0);
						response.addCookie(tmp);
						break;
					}
				}
			}
			// 로그인한 사용자의 오늘 방문수와 방문 포인트를 누적한다.
			memberService.setMemberVisitProcess(vo);
			return "redirect:/message/memberLoginOk?mid="+mid;
		}
		
		else {
			
			return "redirect:/message/memberLoginNo";
		}
	}
	
	@RequestMapping(value = "/memberLogout", method = RequestMethod.GET)
	public String memberLogoutGet(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		session.invalidate();
		return "redirect:/message/memberLogout?mid="+mid;
	}
	
	@RequestMapping(value = "/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet() { 
		return "member/memberJoin";
	}
	@RequestMapping(value = "/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost(MemberVO vo) { 
		// 아이디 중복 체크
		if(memberService.getMemberIdCheck(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		// 닉네임 중복 체크
		else if(memberService.getMemberNickCheck(vo.getNickName()) != null ) return "redirect:/message/nickCheckNo";
		
		//비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		// 사진 파일 업로드가 되었다면 사진 파일을 서버 파일시스템에 저장 시켜준다.
		
		// 체크가 완료되면 VO에 담긴 자룔를 DB에 저장시겨준다
		int res = memberService.setMemberJoinOk(vo);
		
		return "redirect:/message/memberJoinOk";
	}
	
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdCheckPost(String mid) { 
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null) return "1";
		else return "0";
	}
	
	@RequestMapping(value = "/memberNickCheck", method = RequestMethod.POST)
	@ResponseBody
	public String memberNickCheckPost(String nickName) { 
		MemberVO vo = memberService.getMemberNickCheck(nickName);
		
		if(vo != null) return "1";
		else return "0";
	}
	
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String memberMain() {
		return "member/memberMain";
	}
	
	// 아이디 찾기
	@RequestMapping(value = "/memberIdFind", method = RequestMethod.GET)
	public String memberIdFindGet() {
		return "member/memberIdFind";
	}
	// 아이디 찾기
	@RequestMapping(value = "/memberIdFind", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdFindPost(String name, String toMail) {
		String res="";
		MemberVO vo =memberService.getMemberNameCheck(name);
		
		if(vo != null && vo.getEmail().equals(toMail)) {
			String tmpMid="";
			for(int i=0;i<vo.getMid().length();i++) {
				if(i%2==0) tmpMid = tmpMid.substring(0, i) + "*" + vo.getMid().substring(i + 1);
			}
			res=tmpMid;
		}
		return res;
	}
	// 비밀번호 찾기
	@RequestMapping(value = "/memberPwdFind", method = RequestMethod.GET)
	public String memberPwdFindGet() {
		return "member/memberPwdFind";
	}
	@RequestMapping(value = "/memberPwdFind", method = RequestMethod.POST)
	public String memberPwdFindPost(String mid, String toMail, HttpServletRequest request) throws MessagingException {
		MemberVO vo =  memberService.getMemberIdCheck(mid);
		
		if(vo ==null) {
			return "redirect:/message/memberIdChkNo";
		}
		else if(!vo.getEmail().equals(toMail)) {
			return "redirect:/message/memberEmailChkNo";
		}
		else {
			UUID uid = UUID.randomUUID();
			String pwd = uid.toString().substring(0,8);
			
			// 발급 받은 임시 비밀번호를 암호화 처리 시켜서 DB에 저장 한다.
			memberService.setMemberPwdUpdate(mid,passwordEncoder.encode(pwd));
			
			// 저장된 임시 비밀번호를 메일 전송 처리한다.
			String content = pwd;
			int res = mailSend(toMail,content);
			
			// 회원이 임시 비밀번호를 변경 처리할 수있도록 유도하기위해 임시세션 생성
			HttpSession session = request.getSession();
			session.setAttribute("sImsiPwd", "ok");
			
			if(res == 1) return "redirect:/message/memberImsiPwdOk";
			else return "redirect:/message/memberImsiPwdNo";
		}
		
	}
	
	// 임시 비밀번호를 메일로 전송 처리한다.
	private int mailSend(String toMail, String content) throws MessagingException {
		String title = "임시 비밀번호를 발급하였습니다.";
		
		// 메일 전송을 위한 객체: MimeMessage(), MimeMessageHelper()
		MimeMessage message =  mailSender.createMimeMessage();
		
		//보관함
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true , "UTF-8");
		
		//메일 보관함에 회원이 보내온 메시지들의 정보를 모두 저장시킨 후 작업 
		messageHelper.setTo(toMail.split(";"));
		
		messageHelper.setSubject(title);
		
		//메시지 보관함의 내용(content)에 필요한 정보를 추가로 담아서 전송 시킬수 있도록 한다.
		
		content += "<br><hr><h3>임시 비밀번호는 <font color='red'>"+content+"<font/></h3><hr><br>";
		
		// cid 예약어
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 :<a href='http://49.142.157.251:9090/javaweb14J/MainHomepage.mem'>영화보는날</a> </p>";
		content += "<hr>";
		
		messageHelper.setText(content, true);
		
		// 이미지 경로를 별도로 표시시켜준다. 그다음 다시 보관함에 담아준다.
		FileSystemResource file = new FileSystemResource("C:\\Users\\green\\git\\javaweb-spring-\\javawebS\\src\\main\\webapp\\resources\\images\\main.jpg");
		messageHelper.addInline("main.jpg", file);

		
		//메일 전송하기
		mailSender.send(message);
		
		return 1;
	}
	
	@RequestMapping(value = "/memberPwdUpdate", method = RequestMethod.GET)
	public String memberPwdUpdateGet() {
		return "member/memberPwdUpdte";
	}
	@RequestMapping(value = "/memberPwdUpdate", method = RequestMethod.POST)
	public String memberPwdUpdatePost(String mid, String pwd,HttpSession session) {
		memberService.setMemberPwdUpdate(mid,passwordEncoder.encode(pwd));
		session.removeAttribute("sImsiPwd");
		return "redirect:/message/memberPwdUpdateOk";
	}

}
