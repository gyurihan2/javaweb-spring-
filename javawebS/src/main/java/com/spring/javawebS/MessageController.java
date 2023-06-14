package com.spring.javawebS;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String messageGet(@PathVariable String msgFlag, Model model) {
		
		if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg","게시글이 등록되었습니다.");
			model.addAttribute("url","/guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("msg","게시글이 등록 실패.");
			model.addAttribute("url","/guest/guestInput");
		}
		
		else if(msgFlag.equals("guestAdminOk")) {
			model.addAttribute("msg","관리자 인증 성공.");
			model.addAttribute("url","/guest/guestList");
		}
		else if(msgFlag.equals("guestAdminNo")) {
			model.addAttribute("msg","관리자 인증 실패.");
			model.addAttribute("url","/guest/adminLogin");
		}
		else if(msgFlag.equals("adminLogout")) {
			model.addAttribute("msg","관리자 로그아웃.");
			model.addAttribute("url","/");
		}
		else if(msgFlag.equals("guestDelteOk")) {
			model.addAttribute("msg","방명혹 글이 삭제 되었습니다.");
			model.addAttribute("url","/guest/guestList");
		}
		else if(msgFlag.equals("guestDelteNo")) {
			model.addAttribute("msg","방명혹 글이 삭제 실패!!");
			model.addAttribute("url","/guest/guestList");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg","메일 전송 완료!!!");
			model.addAttribute("url","/study/mail/mailForm");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("msg","아이디가 중복 되었습니다.");
			model.addAttribute("url","/member/memberJoin");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("msg","닉네임이 중복 되었습니다.");
			model.addAttribute("url","/member/memberJoin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("msg","회원 가입 완료되었습니다.");
			model.addAttribute("url","/member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("msg","회원가입 실패.");
			model.addAttribute("url","/member/memberJoin");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("msg","로그인 되었습니다.");
			model.addAttribute("url","/member/memberMain");
		}
		else if(msgFlag.equals("memberLogin")) {
			model.addAttribute("msg","로그인 실패.");
			model.addAttribute("url","/member/memberLogin");
		}
		else if(msgFlag.equals("memberLogout")) {
			model.addAttribute("msg","로그아웃 되었습니다.");
			model.addAttribute("url","/member/memberLogin");
		}
		else if(msgFlag.equals("admainNo")) {
			model.addAttribute("msg","관리자 전용 페이지 입니다.");
			model.addAttribute("url","/");
		}
		else if(msgFlag.equals("memberNo")) {
			model.addAttribute("msg","로그인 하셔야 사용 가능합니다.");
			model.addAttribute("url","/member/memberLogin");
		}
		else if(msgFlag.equals("levelChkNo")) {
			model.addAttribute("msg","회원 등급을 확인하세요.");
			model.addAttribute("url","/member/memberLogin");
		}
		else if(msgFlag.equals("memberIdChkNo")) {
			model.addAttribute("msg","회원 아이디를 확인하세요.");
			model.addAttribute("url","/member/memberPwdFind");
		}
		else if(msgFlag.equals("memberEmailChkNo")) {
			model.addAttribute("msg","회원 이메일을 확인하세요.");
			model.addAttribute("url","/member/memberPwdFind");
		}
		else if(msgFlag.equals("memberImsiPwdOk")) {
			model.addAttribute("msg","임시 비밀번호가 발급 되었습니다.\\n가입된 메일을 확인하세요.");
			model.addAttribute("url","/member/memberLogin");
		}
		else if(msgFlag.equals("memberImsiPwdNo")) {
			model.addAttribute("msg","임시비밀번호 발급 실패.");
			model.addAttribute("url","/member/memberPwdFind");
		}
		else if(msgFlag.equals("memberPwdUpdateOk")) {
			model.addAttribute("msg","비밀번호 변경 완료.");
			model.addAttribute("url","/member/memberMain");
		}
		
		
		return "include/message";
	}
	
}
