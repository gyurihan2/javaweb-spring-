<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>adminLogin.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
	<h2 class="text-center">관리자 로그인</h2>
	<form name="myform" method="post">
	<table>
		<tr>
			<th>관리자아이디</th>
			<td><input type="text" name="mid" id="mid" class="form-control"/></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="text" name="pwd" id="pwd" class="form-control"/></td>
		</tr>
		<tr>
			<td colspan="2" class="text-center">
				<input type="submit" value="관리자 로그인" class="btn btn-success"/>
				<input type="reset" value="다시입력" class="btn btn-warning"/>
				<input type="button" value="돌아가기" class="btn btn-danger" onclick="location.href='${ctp}/guest/guestList.gu';"/>
			</td>
			
		</tr>
	</table>
	</form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>