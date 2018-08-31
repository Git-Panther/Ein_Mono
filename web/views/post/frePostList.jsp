<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<PostVo> list = (ArrayList<PostVo>)request.getAttribute("list");
	String pType = (String)request.getAttribute("pType");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	int listCount = pi.getTotalCount();
	int currentPage = pi.getCurrentPage();
	int maxPage = pi.getMaxPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	if(endPage == 0) {
	      endPage = 1;
	   }
%>     
<!DOCTYPE html>
<html> 
<head>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<meta charset="UTF-8">

<title>게시판 목록</title>

<style>
.outer{
	width:1100px;
		height:500px;
		margin-left:auto;
		margin-right:auto;
		text-align:center;
}
.tableArea{
	width:1100px;
	height:300px;
	margin-left:auto;
	margin-right:auto;
	margin-bottom:100px;
 }
 .tableArea th{
    	border-top:1px solid black;
    	border-bottom:1px solid black;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    	font-size:15px;
    	padding:10px;
    }
    .tableArea td{
    	font-size:15px;
    	padding:10px;
    	border-bottom:1px solid black;
    }
.searchArea{
	width:650px;
	margin-left:auto;
	margin-right:auto;
	margin-top:100px;
 }
 	.auction{
		margin-top:40px;
		margin-left:auto;
		margin-right:auto;
		width:1100px;
		height:90px;
		background:rgba(48, 49, 56,0.8);
		color:white;
		padding:3px;
		text-align: left;
	}
	.auction label{
		font-family: 'Rubik', sans-serif;
		font-size:17px;
		margin-left:10px;
		padding:10px;
	}
	a:link{ text-decoration:none;}
	a:visited{ color:black; text-decoration:none;}
	a:hover{ color:black; text-decoration:none;}
	
	#btn1, #btn2{
    	border:1px solid white;
    	padding:3px;
    	border-radius:5px;
    	display:inline-block;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    	font-size:11px;
    }
</style>
<script>
	function writeBoard(){
		location.href="views/board/boardForm.jsp";
	}
	function searchBoard(){
		var condition = $("#searchCondition").val();
		var searchText = $("#searchText").val();
		//쿼리 스트링(파라미터 값 작성 방법)
		//url?key1=value1&key2=value2;
		location.href = "/mono/searchPost.do?condition=" + condition+ "&keyword=" + searchText;
	}
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
   <br>
   <br>
	<div class="auction" >
		<label style="font-size:33px;" >자유게시판</label>
		<hr width="1080px">
	</div>
	<div class="tableArea">
		<table align="center" style="width:1100px;">
	<br>
			<tr>
				<th width="100">글번호</th>
				<th width="300">글제목</th>
				<th width="100">작성자</th>
				<th width="100">조회수</th>
				<th width="150">작성일</th>
			</tr>
			<%if(list.size()<=0){ %>
				<tr>
					<td colspan="5">조회 된 게시글이 없습니다.</td>
				</tr>
			<%}else{ %>
				<%for(PostVo n : list){ %>
				<tr>
					
					<td><%=n.getNum()%></td>			
					<td><a href="/mono/detailPost.do?pCode=<%=n.getPost_code()%>"><%=n.getTitle()%></a></td>			
					<td><%=n.getWriter_nickname()%></td>
					<td><%=n.getViews_count()%></td>			
					<td><%=n.getWritten_date() %></td>	
				</tr>
				<%} %>
			<%} %>
		</table>
	</div>	
	
	
	<div class="searchArea" align="center">
		<select id="searchCondition">		
			<option value="0">전체</option>
			<option value="1">제목</option>
			<option value="2">내용</option>
			<option value="3">작성자</option>
		</select>
		
				
		<input type="text"  id="searchText" placeholder="검색어 입력"/>
		<input type="button" id="btn1" value="검색하기" onclick="searchBoard();"/>
		
		<%-- <%if(null != request.getSession().getAttribute("member")){%> --%>
		<%if(member != null){ %>
		<input type="hidden" value="<%=member.getMemberCode() %>" id="mCode">
		<input type="button" id="btn2"  value="작성하기" onclick="writePost();"/>
		<%} %>
		<%-- <%} %>  --%>
	</div>
	
	<div class="pageArea" align="center" style="margin-top:20px;">
		<button onclick="movePage(1);"> << </button>
		<%for(int i = startPage ; i <= endPage ; i++){ %>
			<%if(currentPage == i){ %>
				<button disabled><%=i %></button>
			<%}else{ %>
				<button onclick="movePage(<%=i%>);"><%=i %></button>
			<%} %>
		<%} %>
		<button onclick="movePage(<%=maxPage%>);"> >> </button>
	<br>
</div>
<script>
$(function(){
	$(".tableArea td").mouseenter(function(){
		$(this).parent().css("cursor","pointer");
	})
	
	$("#btn1, #btn2").mouseenter(function(){
		$(this).css("border", "1px solid black");
		$(this).css("background", "white");
		$(this).css("color", "black");
		$(this).css("cursor", "pointer");
	}).mouseout(function(){
		$(this).css("border", "1px solid white");
		$(this).css("background", "rgba(48, 49, 56,0.8)");
		$(this).css("color", "white");
	});
	$("#btn1, #btn2").mouseenter(function(){
		$(this).css("cursor", "pointer");
	});
});

function movePage(num){
	location.href = "/mono/selectPostList.do?currentPage="+num+"&posttype=<%=pType%>";
}

function writePost(){
	var mCode = $("#mCode").val();
	location.href = "/mono/views/post/writeGalleryPost.jsp?pType=<%=pType%>&mCode="+mCode;
}
</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>









