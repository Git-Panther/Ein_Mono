<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@page import="ein.mono.common.PageInfo"%>
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
<meta charset="UTF-8">
<title></title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
#jiNav, #jiSec1, article, /*  aside, */ footer {
	display: block;
	width: 1200px;
	text-align: center;
}

#jiNav {
	width: 200px;
	height: auto;
	margin-top: auto;
}

#jiSec1 {
	width: 1100px;
	height: auto;
	margin-top: 75px;
}

#jiArticle1 {
	width: 1100px;
	background-color: #efefef;
}
/* aside{float:left; width:104px; height:60px;} */
footer {
	overflow: hidden;
}

.jiCenter {
	width: 1100px;
	height: auto;
	float: left;
}

#jiCenter {
	width: 1100px;
	height: auto;
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

#ptn-license {
	border-radius: 5px;
	background: rgba(61, 96, 131, 0.8);
	color: white;
}

.btnDiv {
	border: 1px solid white;
	padding: 1px;
	border-radius: 5px;
	display: inline-block;
	background: rgba(61, 96, 131, 0.8);
	color: white;
}

.firstLastPageBtn, .pageBtn {
	margin-top: 100px;
}

.firstLastPageBtn {
	padding: 3px;
	border-radius: 5px;
	border: 1px solid white;
	display: inline-block;
	background: rgba(61, 96, 131, 0.8);
	color: white;
	font-size: 11px;
}

.pageBtn {
	padding: 3px;
	display: inline-block;
	color: black;
	font-size: 11px;
}

.titleTermDiv {
	display: inline-block;
	text-align: left;
	float: left;
}

.thumnailDiv,.nullDiv{
	border:1px solid black;
}
.image-list{
	width:400px;
	display:inline-block;
}

</style>
</head>
<body>
	<header>
		<%@ include file="../common/header.jsp"%>
	</header>
	<div id="jiCenter">
		<div class="jiCenter">
			<!-- <section id="jiSec1" class="jiCenter"> -->
			<div id="jiSec1" class="jiCenter" align="center">

				<h1 align="center">후기 게시판</h1>
				<div class="thumnailArea">
					<%for (int i = 0; i < list.size(); i++) {%>
						<%PostVo av = list.get(i);%>
						<div class="image-list"	onclick="attachDetailPage('<%=av.getPost_code()%>');">
							<div class="imgDiv">
								<%
									if (av.getContent().length() < 5) {
								%>
								<div class="nullDiv" style="width: 398px; height: 200px;"></div>
								<%
									} else {
								%>
								<div class="thumnailDiv" style="width: 398px; height: 200px;"><%=av.getContent()%></div>
								<%
									}
								%>
								<%=av.getNum()%>. <%=av.getTitle()%><br> 조회수 :<%=av.getViews_count()%>
							</div>
						</div>
					<%}%>
				</div>
			</div>
		</div>

		<%
			if (member != null) {
		%>
		<input type="hidden" value="<%=member.getMemberCode()%>" id="mCode">
		<button id="writeBtn">글 작성</button>
		<%
			}
		%>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		
		 <div class="pageArea" align="center">
		<button onclick="movePage(1);"> << </button>
		<%for(int i = startPage ; i <= endPage ; i++){ %>
			<%if(currentPage == i){ %>
				<button disabled><%=i %></button>
			<%}else{ %>
				<button onclick="movePage(<%=i%>);"><%=i %></button>
			<%} %>
		<%} %>
		<button onclick="movePage(<%=maxPage%>);"> >> </button>
	</div>
		
	</div>
	<script>
		$("#writeBtn").click(function(){
			var mCode = $("#mCode").val();
			location.href = "/mono/views/post/writeGalleryPost.jsp?pType=<%=pType%>&mCode="+mCode;
		});
		function attachDetailPage(pCode,vCount){
			location.href = "/mono/detailPost.do?pCode="+pCode+"&pType=<%=pType%>";
		}
		
		function movePage(num){
			location.href = "/mono/selectPostList.do?currentPage="+num+"&posttype=<%=pType%>";
		}
		
		$(function() {
			$(".imgDiv img").css("width", 400).css("height", 200);
		});

		$(".imgDiv").mouseenter(function() {
			/*$(this).parent().parent().css("background", "rgba(61, 96, 131, 0.8)");
			$(this).parent().parent().css("color", "white");
			$(this).parent().parent().css("cursor", "pointer"); */

			$(this).css("border", "1px solid black");
			$(this).css("background", "rgba(61, 96, 131, 0.8)");
			$(this).css("color", "white");
			$(this).css("cursor", "pointer");
		}).mouseout(function() {
			$(this).css("border", "1px solid white");
			$(this).css("background", "white");
			$(this).css("color", "black");
		});

		$(".firstLastPageBtn").mouseenter(function() {
			$(this).css("border", "1px solid black");
			$(this).css("background", "white");
			$(this).css("color", "black");
			$(this).css("cursor", "pointer");
		}).mouseout(function() {
			$(this).css("border", "1px solid white");
			$(this).css("background", "rgba(61, 96, 131, 0.8)");
			$(this).css("color", "white");
		});
		$(".pageBtn").mouseenter(function() {
			$(this).css("cursor", "pointer");
		});
	</script>

</body>
</html>








