<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String pType = request.getParameter("pType");
	String mCode = request.getParameter("mCode");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.js"></script>
<style>
.outer{
			width:1000px;
			height:500px;
			margin-left:auto;
			margin-right:auto;
		}
#sky {
	width: 1000px;
	height: 30px;
	border: solid 1px gray;
	margin-left: auto;
	margin-right: auto;
}

#btnDiv {
	width: 90px;
	height: 40px;
	margin-left: auto;
	margin-right: 350px;
}
#titleDiv{
	border:1px solid gray;
	font-size:20px;
}
</style>

</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<br>
	<br>
	<br>
	<h1 align="center">게시글 쓰기</h1>
	<div class="outer">
		<form method="post" action="/mono/writePost.do">
			<div id="titleDiv">글 제목 <input type="text" name="title" size="95"></div>
			<input type="hidden" name="pType" value="<%=pType%>">
			<input type="hidden" name="mCode" value="<%=mCode%>">
			<input type="hidden" name="sky">
			<div id="summernote"></div>
			<button id="submitBtn">확인</button>
		</form>
	</div>
    <script>
      $('#summernote').summernote({
        placeholder: 'Hello bootstrap 4',
        tabsize: 2,
        height: 100,
        height: 300,                 // set editor height
  	  	minHeight: 100,             // set minimum height of editor
  	  	maxHeight: 300,             // set maximum height of editor
  	  	focus: true                  // set focus to editable area after initializing summernote
      });
      $("#submitBtn").click(function(){
    	  var markupStr = $('#summernote').summernote('code');
    	  $("input[name=sky]").val(markupStr);
    	  $("form").submit();
      });
    </script>
    
</body>
</html>