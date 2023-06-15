<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>fileUpload</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  	
  	// 처음 로딩 시 처리 내용(다운로드 파일은 가려준다.)
  	$(function(){
  		$("#downloadFile").hide();
  		$("#fileHideBtn").hide();
  		
  		$("#fileViewBtn").click(function(){
  			$("#downloadFile").show();
  			$("#fileHideBtn").show();
  			$("#fileViewBtn").hide();
  		});
  		$("#fileHideBtn").click(function(){
  			$("#downloadFile").hide();
  			$("#fileHideBtn").hide();
  			$("#fileViewBtn").show();
  		});
  	});
  	
  	// 파일 업로드 체크
  	function fCheck(){
		let fName1 = $("#fName").val();
		let title = $("#title").val();
		let pwd = $("#pwd").val();
		let maxSize = 1024*1024*20; // 20메가
		
		if(fName1.trim()==""){
			alert("업로드할 파일명을 선택하세요");
			return false;
		}
		else if(pwd.trim() == ""){
			alert("비밀번호를 입력하세요");
			return false;
		}
		
		
		// 파일 사이즈 처리..
    	let fileSize = 0;
    	for(let i=1; i<=cnt; i++) {
    		let imsiName = 'fName' + i;
    		if(isNaN(document.getElementById(imsiName))) {
    			let fName = document.getElementById(imsiName).value;
    			if(fName != "") {
    				fileSize += document.getElementById(imsiName).files[0].size;
    				let ext = fName.substring(fName.lastIndexOf(".")+1).toUpperCase();
    				if(ext != "JPG" && ext != "GIF" && ext != "PNG" && ext != "ZIP" && ext != "HWP" && ext != "PPT" && ext != "PPTX") {
    					alert("업로드 가능한 파일은 'jpg/gif/png/zip/hwp/ppt' 파일입니다.");
    					return false;
    				}
    			}
    		}
    	} 
		
		
    	if(fileSize > maxSize) {
    		alert("업로드할 파일의 최대용량은 20MByte입니다.");
    		return false;
    	}
    	else {
    		//alert("파일사이즈 : " + fileSize);
	    	myform.fileSize.value = fileSize;
	    	myform.submit();
    	}
	}
  	
  	// 파일 삭제 처리
  	function fileDelete(fName){
  		let ans = confirm("선택한 파일을 삭제 하시겠습니까?");
  		
  		if(!ans) return false;
  		
  		$.ajax({
  			type:"post",
  			url:"${ctp}/study/fileUpload/fileDelete",
  			data:{fName:fName},
  			success:function(res){
  				if(res == "1"){
  					alert("파일이 삭제 되었습니다.");
  					location.reload();
  				}
  				else{
  					alert("파일삭제 실패");
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
  <h2>파일 업로드 연습</h2>
  <form name="myform" method="post" enctype="multipart/form-data">
  	<p>
  		 올린이:
  		 <input type="text" name="mid" value="${sMid}"/>
  	</p>
  	<p>
  		파일명:
  		<input type="file" name="fName" id="fName" class="form-control-file border" accept=".jpg,gif,.png,.zip,.ppt,.pptx,.hwp"/>
  	</p>
  	<p>
  		<input type="submit" value="파일 업로드" class="btn btn-success" />
  		<input type="reset" value="다시 선택" class="btn btn-warning"/>
  	</p>
  </form>
  <hr/>
  	<input type="button" value="저장된 파일 보기" id="fileViewBtn" class="btn btn-info">
  	<input type="button" value="저장된 파일 감추기" id="fileHideBtn" class="btn btn-secondary">
  <hr/>
  <div id="downloadFile">
  	<h3>저장된 파일 정보 (총: ${fn:length(files) })</h3>
  	<p>저장 경로: ${ctp}/resources/data/study/</p>
  	<table class="table table-hover text-center">
  		<tr class="table-dark text-dark">
  			<th>번호</th>
  			<th>파일명</th>
  			<th>파일 형식</th>
  			<th>비고</th>
  		</tr>
  		<c:forEach var="file" items="${files}" varStatus="st">
  			<tr >
  				<td>${st.count}</td>
  				<td><a href="${ctp}/resources/data/study/${file}" download>${file}</a></td>
  				<td>
  					<c:set var="fNameArr" value="${fn:split(file,'.' )}"/>
  					<c:set var="extName" value="${fn:toLowerCase(fNameArr[fn:length(fNameArr)-1])}"/>
  					<c:if test="${extName == 'jpg'||extName == 'gif'||extName == 'png'}">
  						<img src="${ctp}/resources/data/study/${file}" width="150px">
  					</c:if>
  					<c:if test="${extName == 'zip'}">압축 파일</c:if>
  					<c:if test="${extName == 'pptx' || extName == 'ppt'}">PPT파일</c:if>
  					<c:if test="${extName == 'hwp'}">한글파일</c:if>
  					${extName}
  				</td>
  				<td>
						<input type="button" value="다운로드" class="btn btn-success btn-sm" onclick="location.href='${ctp}/study/fileUpload/fileDownAction?fName=${file}';">
						<input type="button" value="삭제" class="btn btn-success btn-sm" onclick="fileDelete('${file}')"/>
  				</td>
  			</tr>
  		</c:forEach>
  		<tr><td colspan="4" class="m-0 p-0"></td></tr>
  	</table>
  </div>
</div>
<p><br/></P>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>