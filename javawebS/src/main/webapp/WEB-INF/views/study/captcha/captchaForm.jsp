<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>chatchaForm</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <style>
  	#captchaImage{
  		width:230px;
  		height:50px;
  		border:3px dotted #A3C552;
  		text-align:center;
  		padding:5px;
  	}
  	
  </style>
  <script>
  	'use strict';
  	
  	jQuery(function(){
  		// captcha이미지를 새로 만듥;
  		$("#refreshBtn").click(function(e){
  			$.ajax({
  				type:"post",
  				url:"${ctp}/study/captcha/captchaImage",
  				async:false,
  			});
  		});
  		
  		//captcha
  		$("#configBtn").click(function(e){
  			e.prventDefault();
  			
  			let strCaptcha = $("#strCaptcha").val();
  			
  			$.ajax({
  				type:"post",
  				url:"${ctp}/study/captcha/captcha",
  				data:{strCaptcha:strCaptcha}
  			}).done(function(result){
  				if(result == "1") alert("로봇이 아니시군요. 계속 진행하세요");
  				else alert("로봇같은데??? 다시 해보세요")
  			});
  		});
  			
  	});
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></P>
<div class="container">
  <h2>Chatcha 연습</h2>
  <hr/>
  <pre>
  	Chatcha는 기계는 인식할 수 없으나 사람은 쉽게 인식할 수 있는 텍스트, 이미지를 통해 사람과 기계를 구별하는 프로그램
  	- preventDefault란 ?
  	 a태그나 submit 태그를 누르게되면 href를 통해 이동하거나, 창이 새로고침하여 실행되게 되는데,
  	 이때 preventDefault를 통해서 이러한 동작을 막아준다.
  	 주로 사용되는 경우는?
  	 (1) a태그를 눌렀을때도 href 링크로 이동하지 않게 할 경우
  	 (2) form안에 submit 역활을 하는 버튼을 눌렀을때도 새로 실행하지 않게 하고 싶을 경우...(단 submit은 작동됨)
  </pre>
  <form name="myform">
  	<p>다음 코드를 입력해주세요 <img src="${ctp}/images/captcha.png" id="captchaImage">
  	<input type="text" name="strCaptcha" id="strCaptcha"/>
	  	<button id="configBtn">확인</button>
	  	<button id="refreshBtn">새로고침</button>
  	</p>
  </form>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>