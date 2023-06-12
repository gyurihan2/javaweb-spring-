<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>guestInput.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<p><br/></p>
<div class="container">
	<h2 class="text-center">방명록 글올리기</h2>
	<form name="myform" method="post" class="was-validated">
	    <div class="form-group">
  			<label for="uname">성명</label>
		    <input type="text" class="form-control" id="name" placeholder="Enter username" name="name" required/>
		    <div class="valid-feedback">OK~!.</div>
		    <div class="invalid-feedback">이름을 입력하세요.</div>
		</div>
	    <div class="form-group">
  			<label for="uname">E-mail</label>
		    <input type="text" class="form-control" id="email" placeholder="Enter email" name="email"/>
		</div>
	    <div class="form-group">
  			<label for="uname">HomePage</label>
		    <input type="text" class="form-control" id="homePage" placeholder="Enter homePage" name="homePage" value="https://"/>
		</div>
    	<div class="form-group">
		      <label for="pwd">방문소감</label>
		      <textarea rows="5" required name="content" id="content" class="form-control"></textarea>
		      <div class="valid-feedback">OK~!.</div>
		      <div class="invalid-feedback">방문소감을 입력하세요</div>
    	</div>
    	<div class="form-group text-center">
    		<button type="submit" class="btn btn-primary mr-2">방명록 등록</button>
    		<button type="reset" class="btn btn-warning mr-2">방명록 다시쓰기</button>
    		<button type="button" onclick="location.href='${ctp}/guest/guestList'" class="btn btn-danger mr-2" >돌아가기</button>
    	</div>
    	<input type="hidden" name="hostIp" value="<%=request.getRemoteAddr()%>" />
	</form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>