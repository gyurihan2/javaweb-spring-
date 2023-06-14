<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>title</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></P>
<div class="container">
  <h2>이곳은 회원 방 입니다.</h2>
  <hr/>
  <div>
  	<p><font color="blue"><b>${sNickName}</b></font></p>
  	<p>회원님의 현재 등급은 <font color="orange">${strLevel}</font></p>
  	<!-- 회원의 기본 정보를 출력 한다. (포인트, 방문횟수, .....) -->
  	<c:if test="${!empty sImsiPwd}">
  		<hr/>
  			현재 임시 비밀번호로 로그인 하셨습니다.<br/>
  			비밀번호를 변경하세요.<br/>
  			<a href="${ctp}/member/memberPwdUpdate" class="btn btn-success">비밀번호 변경</a>
  		<hr/>
  	</c:if>
  </div>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>