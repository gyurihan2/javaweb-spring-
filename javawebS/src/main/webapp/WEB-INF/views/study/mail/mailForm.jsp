<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>mailForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	function memberMailChk() {
  		let mailStr="";
			let mailChks = document.getElementsByName("userMailChk");
			for(let i=0; i<mailChks.length; i++){
				if(mailChks[i].checked == true){
					mailStr += mailChks[i].value +";";
				}
			}
			
			if(mailChks.length >1){
				mailStr = mailStr.substring(0,mailStr.length-1);
			}
			
			$("#toMail").val(mailStr);
		}
  	
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></P>
<div class="container">
  <h2>Mail 보내기</h2>
  <p>(받는 사람의 메일 주소를 정확히 입력하셔야 합니다.)</p>
  <form name="myform" method="post">
  	<table class="table table-bordered">
  		<tr>
  			<th>받는 사람</th>
  			<td class="input-group input-group-sm">
  				<input type="text" name="toMail" id="toMail" value="${email}" placeholder="받는 사람 메일주소를 입력하세요" class="form-control mr-2" required autofocus/>
  				<input type="button" value="주소록" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal1">
  			</td>
  		</tr>
  		<tr>
  			<th>메일 제목</th>
  			<td><input type="text" name="title" placeholder="메일 제목을 입력하세요" class="form-control" required/></td>
  		</tr>
  		<tr>
  			<th>메일 내용</th>
  			<td>
  				<textarea rows="7" name="content" class="form-control" required></textarea>
  			</td>
  		</tr>
  		<tr>
  			<td colspan="2">
  				<input type="submit" value="메일보네기" class="btn btn-success"/>
  				<input type="reset" value="다시 쓰기" class="btn btn-secondary"/>
  				<input type="button" value="돌아가기" class="btn btn-danger" onclick="location.href='${ctp}/';"/>
  			</td>
  		</tr>
  		
  	</table>
  </form>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
<div class="modal fade" id="myModal1">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">주소록</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
      	<c:if test="${!empty vos}">
      		<table class="table table-bordered text-center">
      			<tr>
      				<th>이름</th>
      				<th>Email</th>
      				<th>비고</th>
      			</tr>
	      		<c:forEach var="vo" items="${vos}" varStatus="st">
	      			<tr>
	      				<td>${vo.name}</td>
	      				<td >${vo.email}</td>
	      				<td><input type="checkbox" name="userMailChk" value="${vo.email}"/></td>
	      			</tr>
	      		</c:forEach>
      		</table>
      	</c:if>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
  			
        <button type="button" class="btn btn-success" data-dismiss="modal" onclick="memberMailChk()">선택</button>
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>