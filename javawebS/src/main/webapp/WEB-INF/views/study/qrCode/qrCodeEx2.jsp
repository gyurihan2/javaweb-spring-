<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>qrCodeEx2</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	function qrCheck(){
  		let moveUrl = $("#moveUrl").val();
  		
  		
  		if(moveUrl.trim() == ""){
  			alert("입력 값을 확인하세요");
  			return false;
  		}
  		
  		let query={
  				moveUrl:moveUrl
  		}
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/qrCode/qrCodeEx2",
  			data:query,
  			success:function(res){
  				alert("QR Code 생성 되었습니다.\n 이름은?" + res);
  				
  				let qrCode = 'QR Code명:'+res+'<br>';
  				qrCode +='<img src="${ctp}/data/qrCode/'+res+'.png"/>';
  				
  				$("#demo").html(qrCode);
  			},
  			error:function(){
  				alert("전송 실패")
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
  <h2>정보 사이트로 이동하기</h2>
  <hr/>
  <form method="post">
  	이동할 주소:
  	<div class="input-group">
	  	<input type="text" class="form-control" name="moveUrl" id="moveUrl" value="www.cgv.co.kr"/>
	  	<div class="input-group-append">
		  	<input type="button" class="btn btn-success" value="소개QR코드생성" onclick="qrCheck()"/>
	  	</div>
  	</div>
  </form>
  <div id="demo"></div>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>