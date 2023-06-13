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
  	
  	function uuidCheck(){
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/uuid/uuidForm",
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
  <h2>UUID(Universally Unique Identifier)</h2>
  <pre>
  	UUID란 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 규약
  	32자리의 16진수(128bit)로 표현 된다.
  	표시: 8-4-4-4-12 자리로 표현한다.
  	
  </pre>
  <hr/>
  <p>
  	<input type="button" value="UUID" onclick="uuidCheck()" class="btn btn-success"/>
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