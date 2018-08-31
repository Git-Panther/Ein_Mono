<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.request.model.vo.RequestVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<RequestVo> list = (ArrayList<RequestVo>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	int listCount = pi.getTotalCount();
	int currentPage = pi.getCurrentPage();
	int maxPage = pi.getMaxPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>경매신청목록</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style>
	.top{
		margin-top:90px;
		margin-left:auto;
		margin-right:auto;
		width:1100px;
		height:110px;
		background:rgba(48, 49, 56,0.8);
		color:white;
		padding:3px;
	}
	.top label{
		font-family: 'Rubik', sans-serif;
		font-size:20px;
		margin-left:10px;
		padding:10px;
	}
	.outer{
		margin-left:auto;
		margin-right:auto;
	}
	table td{
		text-align:center;
	}
	.tableArea{
		margin-top:10px;
	}
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="outer">
	<div class="top">
		<label style="font-size:40px;">AUCTION : 경매</label>
		<hr width="1080px">
		<label>경매신청 리스트 입니다.<br></label>
	</div>
	<div class="tableArea">
		<table align="center">
			<tr>
				<th>경매코드</th>
				<th>시공타입</th>
				<th>경매자 이름</th>
				<th>주소</th>
				<th>시공희망일</th>
				<th>진행상태</th>
			</tr>
			<%if(list.size() == 0){%>
				<tr>
					<td colspan="6">조회된 경매가 없습니다.</td>
				</tr>
			<%}else{ %>
				<%for(RequestVo n : list){ %>
				<tr>
					<td><%=n.getReqCode()%></td>
					<td><%=n.getReqType()%></td>
					<td><%=n.getUserName()%></td>
					<td><%=n.getConstAddress()%></td>
					<td><%=n.getReqDate().substring(0,10)%></td>
					<td><%=n.getReqCheck()%></td>
				</tr>
				<%} %>
			<%} %>
		</table>
	</div>
	<!-- 페이징 처리 부분 -->
	<div class="pageArea" align="center">
		<button onclick="movePage(1);"> << </button>
		<%for(int i = startPage; i<=endPage; i++){ %>
			<%if(i == currentPage){ %>
				<button><%=i%></button>
			<%}else{ %>
				<button onclick="movePage(<%=i%>);"><%=i%></button>
			<%} %>
		<%} %>
		<button onclick="movePage(<%=maxPage%>);"> >> </button>
	</div>
</div>
<script>
$(function(){
	$(".tableArea td").mouseenter(function(){
		$(this).parent().css("background","darkgray");
		$(this).parent().css("cursor","pointer");
	}).mouseout(function(){
		$(this).parent().css("background","white");
	}).click(function(){
		var reqCode = $(this).parent().children().eq(0).text();	
		location.href = "/mono/selectRequestDetail.do?reqCode=" + reqCode + "&currentPage=" + <%=currentPage%>;
	});
});
	
	function movePage(pageNum){
		location.href = "/mono/selectRequestList.do?currentPage=" + pageNum;
	}
</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>