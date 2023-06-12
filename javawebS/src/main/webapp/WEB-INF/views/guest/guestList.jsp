<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>guestList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  
  <style>
    th {
      background-color:#eee;
      text-align: center;
    }
  </style>
  <script>
  	'use strcit';
  	function pageCheck() {
		let pageSize = $("#pageSize").val();
		location.href = "${ctp}/guest/guestList?pag=${pag}&pageSize="+pageSize;
	}
  	function delCheck(idx){
  		let ans = confirm("형재 게시물을 삭제 하시겠습니다.");
  		if(ans){
  			location.href = '${ctp}/guest/gusetDelete?idx='+idx;
  		}
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">방명록리스트</h2>
  <table class="table table-borderless mb-0 p-0">
    <tr>
    	<c:if test="${sAdmin != 'adminok'}">
	      	<td><a href="${ctp}/guest/adminLogin" class="btn btn-success btn-sm">관리자</a></td>    	
    	</c:if>
    	<c:if test="${sAdmin =='adminok'}">
	      	<td><a href="${ctp}/guest/adminLogout" class="btn btn-danger btn-sm">관리자 로그아웃</a></td>    	
    	</c:if>
      	<td style="text-align:right;"><a href="${ctp}/guest/guestInput" class="btn btn-secondary btn-sm">글쓰기</a></td>
    </tr>
  </table>
  <table class="table table-borderless mb-0 p-0">
    <tr>
    	<td>
    	<select name="pageSize" id="pageSize" onchange="pageCheck()">
	    		<option <c:if test="${pageSize==3}">selected</c:if>>3</option>
	    		<option <c:if test="${pageSize==5}">selected</c:if>>5</option>
	    		<option <c:if test="${pageSize==10}">selected</c:if>>10</option>
	    		<option <c:if test="${pageSize==15}">selected</c:if>>15</option>
	    		<option <c:if test="${pageSize==20}">selected</c:if>>20</option>
    	</select> 건
    	</td>
    	<td class="text-right">
    		<!-- 첫페이지 /이전페이지/ (현재페이지/총페이지) /다음페이지 /마지막페이지 -->
    		<c:if test="${pag > 1}">
    			<a href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=1">◁</a>
    			<a href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=${pag-1}">←</a>
    		</c:if>
    		(${pag} / ${totPage})
    		<c:if test="${pag < totPage}">
    			<a href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=${pag+1}">→</a>
    			<a href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=${totPage}">▷</a>
    		</c:if>
    		
    	</td>
    </tr>
  </table>
  <c:forEach var="vo" items="${vos}" varStatus="st">
	  <table class="table table-borderless mb-0 mt-0">
	    <tr>
	      <td>
	      	번호 : ${curScrStartNo-st.index}
	      	<c:if test="${sAdmin == 'adminok' }">
		      	<a href="javascript:delCheck('${vo.idx}')" class="btn btn-danger btn-sm">삭제</a>
	      	</c:if>
	      </td>
	      <td style="text-align:right;">방문IP : ${vo.hostIp}</td>
	    </tr>
	  </table>
	  <table class="table table-bordered mt-0">
	    <tr>
	      <th style="20%">성명</th>
	      <td style="25%">${vo.name}</td>
	      <th style="20%">방문일자</th>
	      <td style="35%">${fn:substring(vo.visitDate,0,19)}</td>
	    </tr>
	    <tr>
	      <th>메일주소</th>
	      <td colspan="3">
	      	<c:if test="${empty vo.email || fn:length(vo.email)<5 || fn:indexOf(vo.email,'@')==-1|| fn:indexOf(vo.email,'.')==-1}"> - 없음 - </c:if>
	      	<c:if test="${!empty vo.email && fn:length(vo.email)>=5 && fn:indexOf(vo.email,'@') >-1&& fn:indexOf(vo.email,'.')>1}">${vo.email}</c:if>
	      	
	      </td>
	    </tr>
	    <tr>
	      <th>홈페이지</th>
	      <td colspan="3">
	      	<c:if test="${empty vo.homePage || fn:length(vo.homePage)<10 || fn:indexOf(vo.homePage,'.')==-1}"> - 없음 - </c:if>
	      	<c:if test="${!empty vo.homePage && fn:length(vo.homePage)>=10 || fn:indexOf(vo.homePage,'.')>1}">${vo.homePage}</c:if>
	      </td>
	    </tr>
	    <tr>
	      <th>방문소감</th>
	      <td colspan="3" style="height:150px">${fn:replace(vo.content,newLine,'<br/>')}</td>
	    </tr>
	  </table>
  </c:forEach>
  <br/>
  <!-- 이전블록 / 1(4) 2(5) 3(6) /다음블록 -->
  <div class="text-center">
  	<ul class="pagination justify-content-center">
	  	<c:if test="${pag > 1}">
	  		 <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=1">첫페이지</a></li>
	  	</c:if>
	  	<c:if test="${curBlock > 0 }">
	  		 <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=${((curBlock-1)*blockSize)+1}">이전블록</a></li>
	  	</c:if>
	  	<c:forEach var="i" begin="${curBlock*blockSize+1}" end="${curBlock*blockSize+ blockSize}" varStatus="st">
	  		<c:if test="${i<=totPage && i==pag}">
	  			 <li class="page-item active"><a class="page-link text-white bg-secondary">${i}</a></li>
	  		</c:if>
	  		<c:if test="${i<=totPage && i!=pag}">
	  			 <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=${i}">${i}</a></li>
	  		</c:if>
	  	</c:forEach>
	  	<c:if test="${curBlock < lastBlock }">
	  		 <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=${((curBlock+1)*blockSize)+1}">다음블록</a></li>
	  	</c:if>
	  	<c:if test="${pag < totPage}">
	  		 <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pageSize=${pageSize}&pag=${totPage}">마지막 페이지</a></li>
	  	</c:if>
  	</ul>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>