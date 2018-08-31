<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String msg = (String)request.getAttribute("msg");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.error{
		border:1px solid #939393;
		position:relative;
		width:1100px;
		height:500px;
		margin-top:100px;
		margin-left:auto;
		margin-right:auto;		
	}
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
	<div class="error">
		<h1>서비스 에러 발생</h1>
		<h3>ERROR : <%=msg%></h3>
		<h4>해당 사항 관련 문의는 010-6561-2016</h4>
		<p><a href="/mono/index.jsp">메인 페이지로</a></p>
	</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>