<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<div>
	<p>
		<a href="${ctp}/study/kakaomap/kakaoEx1" class="btn btn-success">마커표시/저장</a>
		<a href="${ctp}/study/kakaomap/kakaoEx2" class="btn btn-primary">저장된 지명 검색(local DB)</a>
		<a href="${ctp}/study/kakaomap/kakaoEx3" class="btn btn-info">키워드 검색(kakao DB)</a>
		<a href="${ctp}/study/kakaomap/kakaoEx4" class="btn btn-secondary">카테고리 검색</a>
		<a href="${ctp}/study/kakaomap/kakaoEx5" class="btn btn-secondary">테스트</a>
	
	</p>
</div>