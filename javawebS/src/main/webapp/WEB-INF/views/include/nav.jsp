<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-black w3-card">
    <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <a href="http://192.168.50.87:9090/javawebS" class="w3-bar-item w3-button w3-padding-large">HOME</a>
    <!-- <a href="http://49.142.157.251:9090/javawebS" class="w3-bar-item w3-button w3-padding-large">HOME</a> -->
    <!-- <a href="${ctp}/" class="w3-bar-item w3-button w3-padding-large">HOME</a> -->
    <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Guest</a>
    <c:if test="${sLevel <= 3 }">
	    <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Board</a>
	    <a href="#" class="w3-bar-item w3-button w3-padding-large w3-hide-small">PDS</a>
	    
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Study 1<i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/study/password/sha256" class="w3-bar-item w3-button">암호화(SHA-256)</a>
	        <a href="${ctp}/study/password/aria" class="w3-bar-item w3-button">암호화(ARIA)</a>
	        <a href="${ctp}/study/password/bCryptPassword" class="w3-bar-item w3-button">암호화(security)</a>
	        <a href="${ctp}/study/mail/mailForm" class="w3-bar-item w3-button">메일연습</a>
	        <a href="${ctp}/study/uuid/uuidForm" class="w3-bar-item w3-button">uuid연습</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Study 2<i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/study/ajax/ajaxForm" class="w3-bar-item w3-button">AJAX연습</a>
	        <a href="${ctp}/study/fileUpload/fileUploadForm" class="w3-bar-item w3-button">파일 업로드</a>
	        <a href="${ctp}/study/validator/validatorList" class="w3-bar-item w3-button">Validator 연습</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">My Page <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/member/meberList" class="w3-bar-item w3-button">회원 List</a>
	        <a href="${ctp}/member/memberPwdUpdate" class="w3-bar-item w3-button">비밀번호 변경</a>
	        <a href="${ctp}/member/memberPwdChk" class="w3-bar-item w3-button">비밀번호 확인</a>
	        <a href="${ctp}/member/memberUpdate" class="w3-bar-item w3-button">정보 수정</a>
	        <a href="javascript:memberDelete()" class="w3-bar-item w3-button">회원 탈퇴</a>
	        <c:if test="${sLevel==0}">
	        	<a href="${ctp}/study/password/sha256" class="w3-bar-item w3-button">관리자</a>
	        </c:if>
	      </div>
	    </div>
    </c:if>
    <c:if test="${empty sLevel}">
	    <a href="${ctp}/member/memberLogin" class="w3-bar-item w3-button w3-padding-large w3-hide-small w3-right">Login</a>
	    <a href="${ctp}/member/memberJoin" class="w3-bar-item w3-button w3-padding-large w3-hide-small w3-right">Join</a>
    </c:if>
    <c:if test="${!empty sLevel}">
	    <a href="${ctp}/member/memberLogout" class="w3-bar-item w3-button w3-padding-large w3-hide-small w3-right">Logout</a>
    </c:if>
    <a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a>
  </div>
</div>

<!-- 회원 탈퇴  -->
<script>
	function memberDelete(){
		let ans = confirm("탈퇴하시겠습니까?");
		if(ans){
			ans = confirm("탈퇴할경우 30일간 같은 아이디로 회원가입이 불가합니다.\n")
			if(ans){
				location.href="${ctp}/member/memberDeleteOk";
			}
		}
	}
</script>


<!-- Navbar on small screens (remove the onclick attribute if you want the navbar to always show on top of the content when clicking on the links) -->
<div id="navDemo" class="w3-bar-block w3-black w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top:46px">
  <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Guest</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Board</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">PDS</a>
</div>