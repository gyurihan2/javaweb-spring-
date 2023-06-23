<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>qrcodeEx1</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	function qrCheck(){
  		let mid = $("#mid").val();
  		let name = $("#name").val();
  		let email = $("#email").val();
  		
  		if(mid.trim() == "" || name.trim() == "" || email.trim() == ""){
  			alert("입력 값을 확인하세요");
  			return false;
  		}
  		
  		let query={
  				mid:mid,
  				name:name,
  				email:email
  		}
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/qrCode/qrCodeEx1",
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
  <h2> 개인 정보를 QR Code로 생성</h2>
  <hr/>
  <form method="post">
  	<b>개인 정보 입력</b><br/>
  	<table class="table table-bordered">
  		<tr>
  			<th>아이디</th>
  			<td><input type="text" class="form-control" name="mid" id="mid" value="${sMid}"/></td>
  		</tr>
  		<tr>
  			<th>성명</th>
  			<td><input type="text" class="form-control" name="name" id="name" value="홍길동"/></td>
  		</tr>
  		<tr>
  			<th>이메일</th>
  			<td><input type="text" class="form-control" name="email" id="email" value="gksrbfl1234@naver.com"/></td>
  		</tr>
  		<tr>
  			<td colspan="2" class="text-center">
  				<input type="button" class="btn btn-success" value="QR 생성" onclick="qrCheck()"/>	
  				<input type="button" class="btn btn-warning" value="다시입력" onclick="location.reload();"/>
  			</td>
  		</tr>
  	</table>
  </form>
  <hr/>
  생성된 QR코드:<br/>
  <div id="demo"></div>
  <hr/>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>