<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>bCryptPassword.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	let str="";
  	let cnt = 0;
  	
  	function bCryptPasswordCheck(){
  		let pwd = $("#pwd").val();
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/password/bCryptPassword",
  			data:{
  				pwd:pwd
  			},
  			success:function(res){
  				str += (++cnt)+"번째: "+ res +"<br/>";
  				$("#demo").html(str);
  			},
  			error:function(){
  				alert("전송 실패");
  			}
  		});
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></P>
<div class="container">
  <h2>bCryptPassword</h2>
  <pre>
  	- 스프링 Security 프레임워크에서 제공하는 클래주중 하나로 비밀번호를 암호화 하는데 사용한다.
  	이것은 주로 자바 서버프로그램 개발을 위해 필요한 '인증'/'권한부여' 및 '보안기능'을 제공해주는 프레임워크에 속한다.
  	- bCryptPasswordEncoder는 bCrypt해싱함수를 사용하여 비밀번호를 인코딩해주는 메소드와 사용자에 의해 제출된 비밀번호를 
  	저장소에 저장된 비밀번호화의 일치 여부를 확인해주는 메소드로 제공된다.
  	- bCryptPasswordEncoder는 PasswordEncoder 인터페이스를 구현한 클래스이다.
  	- 사용되는 메소드?
  	 1) encode(java.lang.CharSequence) : 패스워드를 암호화 해주는 메소드이다.
  	  반환 타입은 String
  	  encode()메소드는 SHA-1에 의하여 8Byte로 결합된 해시키를 랜덤하게 생성된 Salt 지원
  	 2) machers(java.lang.CharSequence)
  		제출된 인코딩 되지 않는 패스워드의 일치 여부를 판단하기 위해 인코딩된 패스워드와 비교 판단한다.
  		반환 타입 boolean이다
  </pre>
  <hr/>
  <p>
  	<input type="text" name="pwd" id="pwd" autofocus/>
  	<input type="button" value="bCryptPassword암호화" onclick="bCryptPasswordCheck()" class="btn btn-success"/>
  	<input type="button" value="다시하기" onclick="location.reload()" class="btn btn-primary"/>
  </p>
  <div>
  	<div>출력 결과</div>
  	<span id="demo"></span>
  </div>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>