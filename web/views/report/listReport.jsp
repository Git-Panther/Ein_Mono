<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.report.model.vo.ReportVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
  		ArrayList<ReportVo> list = (ArrayList<ReportVo>)request.getAttribute("list");  
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
<meta  charset="UTF-8">
<title>신고리스트조회</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
<style>
	.outer{
		width:800px;
		height:450px;
		border:2px solid #0d0d26;
		margin-left:auto;
		margin-right:auto;
		border-radius: 5px;
	}
	
	.inner{
		width:700px;
		height:300px;
		
		margin-left:auto;
		margin-right:auto;
		border-bottom: 2px groove black;
	}
	

	#reason{
	display: inline-block; width: 150px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
	}
	
	#pageBtn, #pageNumBtn{
	background-color: white;
    color: black;
    border: 2px solid #0d0d26;
    border-radius: 10px;
    outline: none;
    cursor:pointer;
	}
	
	#pageNumBtn{
	font-size: 16px;
	}
</style>
<script>

	
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<br>
<br>
<br>
<br>
<div class = "outer" align = "center">
	<h2>신고리스트</h2>
	<div class = "inner" align = "center">
		<table align = "center" style="table-layout:fixed">
			<tr>
				<th width = "150">신고번호</th>
				<th width = "150">신고당한사람</th>
				<th width = "150">사유</th>
				<th width = "150">확인여부</th>
				<th width = "150">신고날짜</th>
			</tr>
			<%if(list.size() ==0){ %>
				<tr>
					<td colspan = "5" style="text-align:center;">조회된 신고글이 없습니다</td>
				</tr>
			<%}else{ %>
				<%for(ReportVo n:list){ %>
			
				<tr>
					<td align = "center" ><%=n.getReportCode()%></td>
					<td align = "center" ><%=n.getMemberId() %></td>
					<td align = "center" id = "reason"><%=n.getReportReason() %></td>
					<td align = "center" ><%=n.getAdminCheck()%></td>
					<td align = "center" ><%=n.getReportDate() %></td>			
				</tr>
				<%}; %>
			<%}; %>
		</table>
	</div>
	<br>
	
	<div class = "pageArea" align = "center"><!-- 페이징처리 부분 -->
		<button id = "pageBtn" onclick = "movePage(1);">맨앞으로</button>		
		<%for(int i = startPage;i<=endPage;i++){ %>	
	<%if(currentPage == i){%>
			<button id = "pageNumBtn" onclick = "movePage(<%=i%>);"  style = "background:#0d0d26; color:white;"><%=i %></button>
			<%}else{ %>
			<button id = "pageNumBtn" onclick = "movePage(<%=i%>);"  ><%=i %></button>
		<%}; %>		
			<%}; %>	
		<button id = "pageBtn" onclick = "movePage(<%=maxPage%>);">맨뒤로</button>
		
	</div>  
	
</div>

<script>
$(function(){
	$(".inner td").mouseenter(function(){		
		
		$(this).parent().css("cursor", "pointer");
		$(this).parent().css("background", "#0d0d26");
		$(this).parent().css("color", "white");
		$(this).parent().css("transition", "0.2s");
		
	}).mouseleave(function(){
		
		$(this).parent().css("background", "white");
		$(this).parent().css("color", "#0d0d26");
	
	}).click(function(){
		var reportNo = $(this).parent().children().eq(0).text();
		location.href = "/mono/detailListReport.do?reportNo=" + reportNo+"&currentPage="+<%=currentPage%>;
	});
});

function movePage(pageNum){
	location.href = "/mono/listReport.do?currentPage="+pageNum;
}



</script>

<%@ include file="../common/footer.jsp" %>
</body>
</html>