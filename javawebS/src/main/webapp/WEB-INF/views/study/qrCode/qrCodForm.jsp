<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>qrcodeForm</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></P>
<div class="container">
  <h2>QR Code연습 폼</h2>
  <hr/>
  <div>
  	<div class="row">
  		<div class="col"><a href="${ctp}/study/qrCode/qrCodeEx1" class="btn btn-success">개인정보 등록</a></div>
  		<div class="col"><a href="${ctp}/study/qrCode/qrCodeEx2" class="btn btn-primary">소개사이트 등록</a></div>
  		<div class="col"><a href="${ctp}/study/qrCode/qrCodeEx3" class="btn btn-info">티겟예매 등록</a></div>
  		<div class="col"><a href="${ctp}/study/qrCode/qrCodeEx4" class="btn btn-info">티겟예매 등록 확인</a></div>
  	</div>
  </div>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>