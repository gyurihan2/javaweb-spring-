package com.spring.javawebS.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Level2Interceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		int level = session.getAttribute("sLevel") == null ? 99: (int)session.getAttribute("sLevel");
		RequestDispatcher dispatcher;
		
		// 준회원이상(비회원=로그인하지 않은 회원) 아니면 초기화면 창으로 
		if(level > 2) {
			if(level == 99) {
				dispatcher = request.getRequestDispatcher("/message/memberNo");
				
			}
			else {
				dispatcher = request.getRequestDispatcher("/message/levelChkNo");
			}
			dispatcher.forward(request, response);
			return false;
		}
		
		return true;
	}
}
