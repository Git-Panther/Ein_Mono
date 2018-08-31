<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% ArrayList<MemberVo> list = (ArrayList<MemberVo>) request.getAttribute("list"); %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>즐겨찾기 목록</title>
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
    .firstLastPageBtn, #deleteFavBtn{
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
    
    <script>
		$(function(){
			$(".tableArea td").mouseenter(function(){
				$(this).parent().css("background", "rgba(48, 49, 56,0.8);");
				$(this).parent().css("color", "white");
				$(this).parent().css("cursor", "pointer");
			}).mouseout(function(){
				$(this).parent().css("background", "white");
				$(this).parent().css("color", "black");
			}).click(function(){ // 클릭하면 해당 업체 상세정보 페이지로 이동하게
				//var keywordNo = $(this).parent().children().eq(0).text();
				
				//location.href = "/mono/selectKeywordList.do?requestPage=adminPageUpdateKeyword&keywordNo=" + keywordNo;
//				location.href = "/mwp/selectBoard.do?boardNo=" + boardNo;
				<%-- location.href = "/mwp/selectBoard.do?boardNo=" + boardNo + "&currentPage=<%=currentPage%>"; --%>
			});
			
			$(".firstLastPageBtn, #deleteFavBtn").mouseenter(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8);");
				$(this).css("color", "white");
			});
			$(".pageBtn").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
		});	
		
		function deleteFav(member_code, target_code){
			location.href = "/mono/deleteFB.do?member_code=" + member_code + "&target_code=" + target_code + "&fb_type=즐겨찾기";
		}
	</script>
</head>

<body>
	<header>
		<%@ include file="../common/header.jsp" %>
	</header>
	<div id="jiCenter">
		<div class="jiCenter">
		    <nav id="jiNav" class="jiCenter">
		    	<%@ include file="maside.jsp" %>
		    </nav>
		    <section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
		    		<%-- <% if(null != member && null != list) { %> --%>
					<table align="center" style="width:800px;">
						<tr>
							<th width="100">분류</th>
							<th width="600">이름(업체명)</th>
							<th width="100"></th>
						</tr>
						<% if(list.size() == 0) { %>
							<tr>
								<td colspan="3">즐겨찾기된 업체가 없습니다.</td>
							</tr>
							<% } else { %>
							<% for(MemberVo m : list) { %>
								<tr>
									<td>즐겨찾기</td>
									<td>	<%= m.getMemberName() %></td>
									<td>
										<button id="deleteFavBtn" onclick="deleteFav('<%=member.getMemberCode()%>', '<%=m.getMemberCode()%>');">해제</button>
									</td>
								</tr>
							<% } %>	
						<% } %>
					</table>
					<%-- <% } %> --%>
				</div>	
		        <!-- <article id="jiArticle1">article</article> -->
		    </section>
		    <!-- 페이징 처리 부분 -->
			<div class="pageArea" align="center">
				<div class="firstLastPageBtn" onclick="movePage(1);"> << </div>
				<% for(int i = 1; i <= 1; i++) { %>
					<%if(i == 1) { %>
						<div class="pageBtn" style="color:lightgray; border-bottom:1px solid black;"><%=i %></div>
					<% } else { %>
						<div class="pageBtn" onclick="movePage(<%=i %>);"><%=i %></div>
					<% } %>
				<% } %>
				<div class="firstLastPageBtn" onclick="movePage(1);"> >> </div>
			</div> 
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>
</html>