package com.spring.javawebS;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) { 
		Cookie[] cookies = request.getCookies();
		
		for(Cookie tmp:cookies){		
			if(tmp.getName().equals("cMid")) {
				System.out.println("실행");
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
}
