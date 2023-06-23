<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>kakaoEx2.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  
  function addressSearch() {
	 	var address=myform.address.value;
	 	
	 	if(address == ""){
	 		alert("검색할 지점을 선택하세요");
	 		return false;
	 	}
		myform.submit();
	}
  
  function addressDelete(){
	  var address=myform.address.value;
		
	 	if(address == ""){
	 		alert("삭제할 지점을 선택하세요");
	 		return false;
	 	}
	 	
	 	var ans = confirm("선택하신 지역을 삭제하시겠습니까?");
	 	if(!ans) return false;
	 	
	 	$.ajax({
	 		type:"post",
	 		url:"${ctp}/study/kakaomap/kakaoAddressDelete",
	 		data:{address:address},
	 		success:function(){
	 			alert("DB 삭제 완료");
	 			location.href="${ctp}/study/kakaomap/kakaoEx2";
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
<p><br/></p>
<div class="container">
	<h2>DB에 지정된 주소 찾아가기</h2>
	
	
	<div>
		<form name="myform" method="get">
			<select name="address" id="address">
    		<option >지역 선택</option>
	    	<c:forEach var="addressVO" items="${vos}">
	    		
	    		<option value="${addressVO.address}" <c:if test="${vo.address == addressVO.address }">selected</c:if>>${addressVO.address}</option>
    		</c:forEach>
			</select>
			<input type="button" value="지역검색" onclick="addressSearch()"/>
			<input type="button" value="지역삭제" onclick="addressDelete()"/>
		</form>
	</div>
	<hr/>
	<div id="map" style="width:100%;height:500px;"></div>
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ae00b678c1347ff37ea0b1b8884fea71"></script>
	<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = { 
	        center: new kakao.maps.LatLng(${vo.latitude}, ${vo.longitude}), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };
	
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	
	// 마커가 표시될 위치입니다 
	var markerPosition  = new kakao.maps.LatLng(${vo.latitude}, ${vo.longitude}); 
	
	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});
	
	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
	
	// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
	// marker.setMap(null);    
	</script>
	<hr/>
	<jsp:include page="kakaoMenu.jsp"/>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>