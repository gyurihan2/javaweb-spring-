<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>userList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	function userDelete(idx){
  		let ans = confirm("삭제하시겠습니까?");
  		
  		if(!ans) return false;
  		
  		location.href="${ctp}/study/validator/validatorDelete2?idx="+idx;
  	}
  	
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></P>
<div class="container">
  <h2>User 리스트</h2>
  <table class="table table-borderless">
  	<tr><td> <p><a href="${ctp}/study/validator/validatorForm">User등록</a></p></td></tr>
  </table>
 
  <table class="table table-hover text-center">
  	<tr>
  		<th>번호</th>
  		<th>아이디</th>
  		<th>성명</th>
  		<th>나이</th>
  		<th>주소</th>
  		<th>비고</th>
  	</tr>
  	<c:forEach var="vo" items="${vos}" varStatus="st">
  		<tr>
  			<td>${st.count}</td>
  			<td>${vo.mid}</td>
  			<td>${vo.name}</td>
  			<td>${vo.age}</td>
  			<td>${vo.address}</td>
  			<td><button class="btn btn-danger btn-sm" onclick="userDelete('${vo.idx}')">삭제</button></td>
  		</tr>
  	</c:forEach>
  	<tr><td colspan="6" class="m-0 p-0"></td></tr>
  </table>
  <p><a href="${ctp}/study/validator/validatorForm">돌아가기</a></p>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>