<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>qrCodeEx3</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	function qrCheck(){
  		let movieTemp = '';
  		
  		let movieName = $("#movieName").val();
  		let movieDate = $("#movieDate").val();
  		let movieTime = $("#movieTime").val();
  		let movieAdult = $("#movieAdult").val();
  		let movieChild = $("#movieChild").val();
  		let mid = $("#mid").val();
  		let name = $("#name").val();
  		let email = $("#email").val();
  		
  		
  		
  		if(movieName.trim() == "" || movieDate.trim() == "" || movieTime.trim() == "" || mid.trim() == "" ||name.trim() == "" ||email.trim() == ""){
  			alert("영화명과 상영일자, 상영시간을 확인하세요");
  			return false;
  		}
  		
  		let now = new Date();
  		let publishNow = now.getFullYear() + "-" + (now.getMonth()+1) + "-" + now.getDate(); // 발행일
  		
  		alert("티켓 발행 일: " + publishNow);
  		
  		// QR코드 내역? : 영화 제목_상영일자_상영시간_성인티켓_어린이티켓_아이디
  		movieTemp += "아이디: "+mid+",\n 이름: "+name+",\n 이메일: "+email+",\n 영화이름: ";
  		movieTemp += movieName+",\n 상영일자: "+movieDate+",\n 상영 시간: "+movieTime+",\n 성인: "+movieAdult+"매,\n 아동"+movieChild+"매,\n";		
  		movieTemp += "발행일자: " + publishNow;
  		let query={
  				mid:mid,
  				name:name,
  				email:email,
  				movieName:movieName,
  				movieDate:movieDate,
  				movieTime:movieTime,
  				movieAdult:movieAdult,
  				movieChild:movieChild,
  				publishNow:publishNow,
  				movieTemp:movieTemp
  		}
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/qrCode/qrCodeEx4",
  			data:query,
  			success:function(res){
  				alert("QR Code 생성 되었습니다.\n 이름은?" + res);
  				
  				let qrCode = 'QR Code명:'+res+'<br>';
  				qrCode +='<img src="${ctp}/data/qrCode/'+res+'.png"/>';
  				
  				$("#demo").html(qrCode);
  				$("#bigo").val(res+".png");
  				$("#qrCodeView").show();
  			},
  			error:function(){
  				alert("전송 실패")
  			}
  		});
  		
  	}
  	
  	// QRcode 정보를 DB에서 검색하여 출력
  	function bigoCheck(){
  		let qrCode= $("#bigo").val();
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/qrCode/qrCodeSearch",
  			data:{qrCode:qrCode},
  			success:function(res){
  				alert(res);
  				$("#demoBigo").html(res.qrCodeName);
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
  <h2>영화 티켓 예매하기</h2>
  <P>(생성된 QR코드를 메일로 보내드립니다. QR코드를 입장시 매표소에 제시해주세요.)</P>
  <hr/>
  <form method="post">
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
			<th>
				영화 선택
			</th>
			<td>
				<select name="movieName" id="movieName" class="form-control">
					<option>인어공주</option>
					<option>범죄도시3</option>
					<option>엘리멘탈</option>
					<option>귀공자</option>
					<option>스파이더맨</option>
					<option>플래시</option>
				</select>
  			</td>
  		</tr>
  		<tr>
  			<th>상영 일자</th>
  			<td><input type="date" name="movieDate" id="movieDate" value="<%=LocalDate.now()%>" class="form-control"/></td>
  		</tr>
  		<tr>
  			<th>상영 시간 선택</th>
  			<td>
  				<select name="movieTime" id="movieTime" class="form-control">
  					<option value="">상영시간 선택</option>
  					<option >12시00분</option>
  					<option >14시00분</option>
  					<option >16시00분</option>
  					<option >18시00분</option>
  					<option >20시00분</option>
  					<option >22시00분</option>
  				</select>
  			</td>
  		</tr>
  		<tr>
  			<th>인원수</th>
  			<td>
  				성인 <input type="number" name="movieAdult" id="movieAdult" value="1" min="1"/>
  				어린이 <input type="number" name="movieChild" id="movieChild" value="0" min="0"/>
  			</td>
  		</tr>
  		<tr>
  			<td colspan="2">
  				<input type="button" class="btn btn-success mr-2" value="티켓 예매하기" onclick="qrCheck()"/>
  				<input type="reset" class="btn btn-warning mr-2" value="리셋"/>
  				<input type="button" class="btn btn-danger mr-2" value="돌아가기" onclick="location.href='${ctp}/study/qrCode/qrCodeForm';"/>
  			</td>
  		</tr>
  	</table>
  </form>
  <div id="demo"></div>
  <hr/>
  <div id="qrCodeView" style="display:none;">
  	<h3>생성된 QR코드와 DB의 자료 확인하기</h3>
  	<div>
  		-생성된 QR코드를 찍어 보고, 아래 검색버튼을 눌러 출력 자료와 비교해 본다.
  		<input type="text" name="bigo" id="bigo"/>
  		<input type="button" value="DB검색" onclick="bigoCheck()" class="btn btn-info"/>
  	</div>
  	<div id="demoBigo"></div>
  </div>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>