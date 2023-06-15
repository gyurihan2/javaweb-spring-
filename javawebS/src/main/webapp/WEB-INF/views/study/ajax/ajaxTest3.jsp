<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ajaxTest3</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	function nameCheck1(){
  		let name = $("#name").val();
  		
  		if(name.trim() == ""){
  			alert("이름을 입력하세요");
  			return false;
  		}
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/ajax/ajaxTest3_1",
  			data:{name:name},
  			success:function(vo){
  				let str="<b>VO로 전송된 자료의 출력</b><hr/>"
  				if(vo != ""){
  					str += "성명: "+vo.name+"<br/>";
  					str += "아이디: "+vo.mid+"<br/>";
  					str += "이메일: "+vo.email+"<br/>";
  				}
  				else{
  					alert("회원 정보가 없습니다.");
  					return false;
  				}
  				
  			},
  			error:function(){
  				alert("전송 실패");
  			}
  		});
  		
  	}
  	
  	function nameCheck2(){
  		let name = $("#name").val();
  		
  		if(name.trim() == ""){
  			alert("이름을 입력하세요");
  			return false;
  		}
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/ajax/ajaxTest3_2",
  			data:{name:name},
  			success:function(vos){
  				let str="";
  				str="<b>VO로 전송된 자료의 출력</b><hr/>"
  				if(vos != ""){
  					str+="<table class='table table-bordered'>";
  					str+="<tr><th>성명</th><th>이메일</th><th>홈페이지</th><th>성별</th><th>포인트</th></tr>"
  					for(let i=0; i<vos.length;i++){
  						str+="<tr>"
  						str+="<td>"+vos[i].name+"</td>";
  						str+="<td>"+vos[i].email+"</td>";
  						str+="<td>"+vos[i].homePage+"</td>";
  						str+="<td>"+vos[i].gender+"</td>";
  						str+="<td>"+vos[i].point+"</td>";
  						str+="</tr>"
  					}
  					str+="</table>"
  					
  					$("#demo").html(str);
  				}
  				
  				else{
  					alert("회원 정보가 없습니다.");
  					return false;
  				}
  				
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
  <h2>aJax를 활용한 회원 아이디 검색</h2>
  <form>
  	<p>아이디:
  		<input type="text" name="name" id="name" autofocus/> &nbsp;
  		<input type="button" value="성명일치 검색" onclick="nameCheck1()" class="btn btn-success">
  		<input type="button" value="부분 성명일치 검색" onclick="nameCheck2()" class="btn btn-success">
  		<input type="reset" value="다시입력"  class="btn btn-warning">
  		<input type="button" value="돌아가기" onclick="location.href='${ctp}/study/ajax/ajaxForm';" class="btn btn-success">
  	</p>
  </form>
  <div id="demo"></div>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>