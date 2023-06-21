<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>userInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></P>
<div class="container">
	<form method="post">
	  <table class="table table-border">
	  	<tr>
	  		<th>아이디</th>
	  		<td><input type="text" name="mid" class="form-control" autofocus /></td>
	  	</tr>
	  	<tr>
	  		<th>성명</th>
	  		<td><input type="text" name="name" class="form-control"/></td>
	  	</tr>
	  	<tr>
	  		<th>나이</th>
	  		<td><input type="text" name="age" class="form-control" value="0"/></td>
	  	</tr>
	  	<tr>
	  		<th>주소</th>
	  		<td><input type="text" name="address" class="form-control"/></td>
	  	</tr>
	  	<tr>
	  	
	  		<td colspan="2">
	  			<input type="submit" value="등록" class="btn btn-success"/>
	  			<input type="reset" value="다시쓰기" class="btn btn-info"/>
	  			<input type="button" value="돌아가기" class="btn btn-warning" onclick="location.href=${ctp}/study/validator/validatorList"/>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td colspan="2"></td>
	  	</tr>
	  </table>
  </form>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>