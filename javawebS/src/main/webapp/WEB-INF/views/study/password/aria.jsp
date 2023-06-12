<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>aria.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	let str="";
  	let cnt = 0;
  	
  	function ariaCheck(){
  		let pwd = $("#pwd").val();
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/password/aria",
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
  <h2>Aria</h2>
  <p>
  	ARIA암호화 방식은 경령환경 및 하드웨어 구현을 위해 최적화된 알고리즘으로, Involutional SPN 구조를 갖는 범용 블록 암호화 알고리즘이다<br/>
  	ARIA가 사용하는 연산은 대부분 XOR과 같은 단순한 바이트 단위 연산으로, 블록 크기는 128 bit이다, 총 비트는 128/192/256(12/14/16라운드)<br/>
  	Academy Research Institute agency
  	ARIA 암호화는 복화가 가능하다.
  </p>
  <hr/>
  <p>
  	<input type="text" name="pwd" id="pwd" autofocus/>
  	<input type="button" value="ARIA암호화" onclick="ariaCheck()" class="btn btn-success"/>
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