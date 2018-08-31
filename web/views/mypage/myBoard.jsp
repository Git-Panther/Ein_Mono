<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<PostVo> list = (ArrayList<PostVo>)request.getAttribute("list");

	PageInfo pi = (PageInfo)request.getAttribute("pi");
	int curp = pi.getCurrentPage();
	int limit = pi.getLimit();
	int maxp = pi.getMaxPage();
	int startp = pi.getStartPage();
	int endp = pi.getEndPage();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴글</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:950px;  height:300px; margin-left:50px; margin-top:75px;}
    #jiArticle1{width:900px; background-color:#efefef;}
    /* aside{float:left; width:104px; height:60px;} */
    footer{overflow:hidden;}
    
    .jiCenter{
		float:left;
    }
    #jiCenter{
    	width:1200px;
    	margin-left:auto;
    	margin-right:auto;
    }
    .tableArea th{
    	border-top:1px solid black;
    	border-bottom:1px solid black;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    	font-size:13px;
    	padding:5px;
    }
    .tableArea td{
    	font-size:12px;
    	padding:7px;
    	border-bottom:1px solid black;
    }

	 .firstLastPageBtn, .pageBtn{
    	margin-top:80px;
    }
    .firstLastPageBtn, #deleteBlockBtn{
    	border:1px solid white;
    	padding:3px;
    	border-radius:5px;
    	display:inline-block;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    	font-size:11px;
    }
    .pageBtn{
    	padding:3px;
    	display:inline-block;
    	color:black;
    	font-size:11px;
    }
    </style>

</head>
<body>
<header>
	<%@include file="/views/common/header.jsp"%>
</header>
	<div id="jiCenter">
		<div class="jiCenter">
			<nav id="jiNav" class="jiCenter">
				<%@include file="/views/mypage/maside.jsp"%>
			</nav>
			<section id="jiSec1" class="jiCenter">		
				<h3>내가 쓴글</h3>
				<div class="tableArea">
				<table align="center" style="width:800px;">
					<tr>
						<th>no</th><th>분류</th><th>제목</th><th>작성일</th><th>조회수</th>
					</tr>
					<%if (0 < list.size()) {%>
						<%for (int i = 0; i < list.size(); i++) {%>
						<tr>
							<td><%=list.size() - i %></td>
							<td><%=list.get(i).getPost_type() %></td>
							<td><a href="/mono/detailPost.do?pCode=<%=list.get(i).getPost_code()%>"><%=list.get(i).getTitle()%></a></td>
							<td><%=list.get(i).getWritten_date()%></td>
							<td align="center"><%=list.get(i).getViews_count()%></td>
						</tr>
						<%}%>
					<%} else {%>
						<tr>
							<td colspan="4">조회 된 내역이 없습니다.</td>
						</tr>
					<%}%>
				</table>
			</div>
			</section>
				<div class="pageArea" align="center">
					<div class="firstLastPageBtn" onclick="movePage(1);"> << </div>
						<%for(int i=startp ; i<=maxp ; i++){ %>
							<%if(curp==i){ %>
								<div class="pageBtn" style="color:lightgray; border-bottom:1px solid black;"><%=i %></div>
							<%}else{ %>
								<div class="pageBtn" onclick="movePage(<%=i %>);"><%=i %></div>
							<%} %>
						<%} %>
					<div class="firstLastPageBtn" onclick="movePage(1);"> >> </div>
				</div>
			</div>
		</div>
	</div>
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
<script>
	function movePage(num){
		location.href = "/mono/myPostList.do?currentPage="+num+"&mCode=<%=member.getMemberCode()%>";
	}
	$(".pageBtn").mouseenter(function(){
		$(this).css("cursor", "pointer");
	});
	$(".firstLastPageBtn").mouseenter(function(){
		$(this).css("border", "1px solid black");
		$(this).css("background", "white");
		$(this).css("color", "black");
		$(this).css("cursor", "pointer");
	}).mouseout(function(){
		$(this).css("border", "1px solid white");
		$(this).css("background", "rgba(48, 49, 56,0.8);");
		$(this).css("color", "white");
	});
</script>

</body>
</html>