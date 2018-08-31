<%@page import="ein.mono.event.model.vo.EventVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% EventVo event = (EventVo) request.getAttribute("event"); %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=event.getTitle() %></title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>

</head>
<body>
	<div class="tableArea">
   		<%-- <% if(null != member && null != list) { %> --%>
		<img src="/mono/upload/ad/<%=event.getAdvPhoto() %>"  width="600px"/>
		<%-- <% } %> --%>
	</div>	
</body>
</html>