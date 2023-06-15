<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberPwdChk.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <style >
  	th{
  		text-align:center;
  		background-color:#eee;
  	}
  </style>
  <script>
  	'use strict';
  	
  	function pwdCheck(){
  		
  		let pwd = $("#pwd").val();
  		let rePwd = $("#rePwd").val();
  		
  		if(pwd == "" || rePwd==""){
  			alert("비밀번호를 입력하세요");
  			return false;
  		}
  		else if(pwd != rePwd){
  			alert("비밀번호가 일치하지 않습니다.");
  			return false;
  		}
  		
  		myform.submit();
  	}
  	
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></P>
<div class="container">
  <h2>비밀번호 확인</h2>
  
  <form method="post" name="myform">
  	<table class="table table-bordered">
  		<tr>
  			<th>비밀번호</th>
  			<td><input type="password" name="pwd" id="pwd" class="form-control" required/></td>
  		</tr>
  		<tr>
  			<td colspan="2">
  				<input type="button" value="회원 정보확인" class="btn btn-success" onclick="pwdCheck()"/>
  				<input type="reset" value="다시입력" class="btn btn-warning"/>
  				<input type="button" value="돌아가기" class="btn btn-secondary" onclick="location.href='${ctp}/member/memberLogin';"/>
  			</td>
  		</tr>
  	</table>
  	<input type="hidden" name="mid" value="${sMid}"/>
  </form>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>