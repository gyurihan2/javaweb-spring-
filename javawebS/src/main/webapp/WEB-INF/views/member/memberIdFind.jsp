<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberPwdFind.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <style >
  	th{
  		text-align:center;
  		background-color:#eee;
  	}
  </style>
  <script>
  	'use strict';
  	
  	function idSearch(){
  		let name = $("#name").val();
  		let toMail = $("#toMail").val();
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/member/memberIdFind",
  			data:{
  				name:name,
  				toMail:toMail
  			},
  			success:function(res){
  				if(res!=""){
  					alert("아이디: " + res);
  				}
  				else{
  					alert("회원 정보가 없습니다.");
  				}
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
  <h2>아이디 찾기</h2>
  <p>아이디와 이메일 주소를 입력하세요</p>
  <form method="post">
  	<table class="table table-bordered">
  		<tr>
  			<th>이름</th>
  			<td><input type="text" name="name" id="name" class="form-control" required/></td>
  		</tr>
  		<tr>
  			<th>E-Mail</th>
  			<td><input type="text" name="toMail" id="toMail" class="form-control" required/></td>
  		</tr>
  		<tr>
  			<td colspan="2">
  				<input type="button" value="아이디 찾기" class="btn btn-success" onclick="idSearch()"/>
  				<input type="reset" value="다시입력" class="btn btn-warning"/>
  				<input type="button" value="돌아가기" class="btn btn-secondary" onclick="location.href='${ctp}/member/memberLogin';"/>
  			</td>
  		</tr>
  	</table>
  </form>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>