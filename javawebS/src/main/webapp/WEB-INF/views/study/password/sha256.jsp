<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>sha256.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	let str="";
  	let cnt = 0;
  	
  	function sha256Check(){
  		let pwd = $("#pwd").val();
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/password/sha256",
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
  <h2>SHA-256</h2>
  <p>
  	SHA256 암호화 방식은 SHA(Secure Hash Algorithm)의 한 종류로 256비트로 구성 되어있으며 32자리 문자열로 구성 된다<br/>
  	단방향 암호화 방식이기에 복호화가 불가능하다.
  </p>
  <hr/>
  <p>
  	<input type="text" name="pwd" id="pwd" autofocus/>
  	<input type="button" value="sha256암호화" onclick="sha256Check()" class="btn btn-success"/>
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