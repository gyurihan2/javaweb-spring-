package com.spring.javawebS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javawebS.service.GuestService;
import com.spring.javawebS.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	GuestService guestService;
	
	@RequestMapping(value = "guestList", method=RequestMethod.GET)
	public String guestListGet(Model model,
			@RequestParam(name="pag",defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize",defaultValue = "5", required = false) int pageSize) {
		
		// page 설정
		int totRecCnt = guestService.totRecCnt();
		int totPage = totRecCnt%pageSize == 0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
		int startIndexNo = (pag-1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		// block 설정
		int blockSize = 5;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage -1) / blockSize;
		
		model.addAttribute("pag",pag);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("totRecCnt",totRecCnt);
		model.addAttribute("totPage",totPage);
		model.addAttribute("curScrStartNo",curScrStartNo);
		model.addAttribute("curBlock",curBlock);
		model.addAttribute("blockSize",blockSize);
		model.addAttribute("lastBlock",lastBlock);
		
		
		List<GuestVO> vos =  guestService.getGuestList(startIndexNo,pageSize);
		model.addAttribute("vos", vos);
		return "guest/guestList";
	}
	
	@RequestMapping(value = "/guestInput", method=RequestMethod.GET)
	public String guestInputGet(Model model) {
		return "guest/guestInput";
	}
	@RequestMapping(value = "/guestInput", method=RequestMethod.POST)
	public String guestInputPost(Model model, GuestVO vo) {
		int res = guestService.setGuestInput(vo);
		
		if(res == 1) {
				return "redirect:/message/guestInputOk";
		}
		else{
			return "redirect:/message/guestInputNo";
		}
	}
	
	@RequestMapping(value = "adminLogin", method=RequestMethod.GET)
	public String adminLoginGet(Model model) {
		return "guest/adminLogin";
	}
	@RequestMapping(value = "adminLogin", method=RequestMethod.POST)
	public String adminLoginPost(Model model, HttpServletRequest request,
			@RequestParam(name="mid",defaultValue = "", required = false) String mid, 
			@RequestParam(name="pwd",defaultValue = "", required = false) String pwd) {
		
		int res = guestService.getAdminChk(mid,pwd);
		
		if(res == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("sAdmin", "adminok");
			return "redirect:/message/guestAdminOk";			
		}
		else return "redirect:/message/guestAdminNo";

	}
	@RequestMapping(value = "adminLogout", method=RequestMethod.GET)
	public String adminLogoutGet(HttpSession session) {
		session.invalidate();
		return "redirect:/message/adminLogout";		
	}

	
}
