
<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.notice.model.vo.NoticeVo"%>

<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	ArrayList<NoticeVo> list = (ArrayList<NoticeVo>)request.getAttribute("list");
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
<meta charset="UTF-8">
<title>노티스 리스트</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
<style>
	.outer{
		border:1px solid black;
		width:900px;
		height:450px;
		margin-left:auto;
		margin-right:auto;
		/* border-radius: 5px; */
		
	}
	.inner{
	margin-left:auto;
	margin-right:auto;
	border:1px solid white;
	width:700px;
	height:300px;
	border-bottom: 1px groove black;
	}
	
	#title{
	display: inline-block; width: 280px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
	
	}
	
	.noticeBtn{
	float:right;
	  margin-top:-10px; 
	 margin-right:20px;
	 cursor:pointer;
	 
	
	}
	 #write:hover{
	 border-radius: 5px;
	 background:#0d0d26;
		color:white;
		transition:0.5s;
	 }
	 
	 #pageBtn, #pageNumBtn{
	background-color: white;
    color: black;
    border: 1px solid #0d0d26;
    border-radius: 10px;
    outline: none;
    cursor:pointer;
	}
	
	#pageNumBtn{
	font-size: 16px;
	}
	
	th{
	background:#0d0d26;
	color:white;
	}
	
	td{
	 border-bottom: 1px solid black;
	}
</style>
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
		var noticeNo = $(this).parent().children().eq(0).text();
		location.href = "/mono/noticeDetail.do?noticeNo=" + noticeNo+"&currentPage="+<%=currentPage%>;
	});
});




</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<br>
<br>
<br>
<br>
<br>
<div class = "outer" align = "center">
	<h2 align = "center">공지사항</h2>
	<div class = "inner" align = "center">
		<table align = "center" style = "table-layout:fixed">
			<tr>
				<th width = "50">번호</th>
				<th width = "100">작성자</th>
				<th width = "300">제목</th>
				<th width = "100">작성일</th>
				<th width = "50">조회수</th>
			</tr>
			<%if(list.size() ==0){ %>
			<tr>
				<td colspan = "5" align = "center">게시된 공지글이 없습니다</td>
			</tr>
			<%}else{ %>
				<%for(NoticeVo n:list){ %>
				<tr>
					<td align = "center"><%=n.getPostCode() %></td>
					<td align = "center"><%=n.getMemberName() %></td>
					<td align = "center" id = "title"><%=n.getTitle() %></td>
					<td align = "center"><%=n.getWrittenDate() %></td>
					<td align = "center"><%=n.getViewsCount() %></td>
				</tr>
				<%} %>
			<%} %>
		</table>
	</div>
	<br>

	
	<div class = "pageArea" align = "center"><!-- 페이징처리 부분 -->
		<button id = "pageBtn" onclick = "movePage(1);">맨앞으로</button>		
		<%for(int i = startPage;i<=endPage;i++){ %>	
	<%if(currentPage == i){%>
			<button id = "pageNumBtn"  onclick = "movePage(<%=i%>);" style = "background:#0d0d26; color:white;"  disabled><%=i %></button>
			<%}else{ %>
			<button id = "pageNumBtn"  onclick = "movePage(<%=i%>);"  ><%=i %></button>
		<%}; %>		
			<%}; %>	
		<button id = "pageBtn" onclick = "movePage(<%=maxPage%>);">맨뒤로</button>
		
	</div>  
	<%if(member != null && "admin".equals(member.getMemberId())){ %>
	<div class = "noticeBtn">
		<div id = "write" onclick = "noticeWrite();">작성</div>
	</div>
	<%}; %>

	
</div>
<script>
function noticeWrite(){
	<%-- location.href = "/mono/views/notice/noticeWrite.jsp?currentPage=<%=currentPage%>"; --%>
	location.href = "/mono/views/notice/noticeWrite.jsp?currentPage=<%=currentPage%>";
}

function movePage(pageNum){
	location.href = "/mono/noticeList.do?currentPage="+pageNum;
}
</script>

<%@ include file="../common/footer.jsp" %>
</body>
</html>