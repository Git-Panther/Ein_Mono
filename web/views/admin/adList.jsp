<%@page import="ein.mono.event.model.vo.EventVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ein.mono.common.PageInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% ArrayList<EventVo> list = (ArrayList<EventVo>) request.getAttribute("list"); %>
<%
	PageInfo pi = (PageInfo) request.getAttribute("pi");
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
<title>광고 목록</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1100px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:950px;  height:auto; margin-top:75px;}
    #jiArticle1{width:900px; background-color:#efefef;}
    /* aside{float:left; width:104px; height:60px;} */
    footer{overflow:hidden;}
    
    .jiCenter{
    	width:1100px;
    	height:auto;
		float:left;
    }
    #jiCenter{
    	width:1100px;
    	height:auto;
    	margin-left:auto;
    	margin-right:auto;
    	text-align:center;
    }
    	
    #ptn-license{
    	border-radius:5px;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    }
    .btnDiv{
    	border:1px solid white;
    	padding:1px;
    	border-radius:5px;
    	display:inline-block;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    }
    .firstLastPageBtn, .pageBtn{
    	margin-top:100px;
    }
    .firstLastPageBtn{
    	padding:3px;
    	border-radius:5px;
    	border:1px solid white;
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
    
    .titleTermDiv{
    	display:inline-block;
    	text-align:left;
    	float:left;
    }
    
    .thumnailArea{
    	width:1100px;
    	margin-left:auto;
    	margin-right:auto;
    }
    </style>
    
    <script>
		$(function(){
			<% if(null != list) { %>
						var mainEventImg = new Array();
						var idx = 0;
						<% for(int i=0; i<list.size(); i++) { %>
							mainEventImg[idx] = "<%=list.get(i).getAdvBanner()%>";
							idx++;
						<% } %>
					var thumnailCheckDivs = $(".thumnailArea").children("img");
						thumnailCheckDivs.each(function(idx){
							$(this).attr("src", "/mono/upload/ad/" + mainEventImg[idx]);
					});
			<% } %>

			$("#ptn-license, .btnDiv").mouseenter(function(){
				/* $(this).parent().parent().css("background", "rgba(61, 96, 131, 0.8)");
				$(this).parent().parent().css("color", "white");
				$(this).parent().parent().css("cursor", "pointer"); */
				
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8)");
				$(this).css("color", "black");
			});
			
			$(".firstLastPageBtn").mouseenter(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8)");
				$(this).css("color", "black");
			});
			$(".pageBtn, .thumnailArea img").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
			
		});
		function adDetail(postCode){
			location.href = "/mono/selectAD.do?postCode=" + postCode;
		}
		
	</script>
</head>
<body>
	<header>
		<%@ include file="../common/header.jsp" %>
	</header>
	<div id="jiCenter">
		<div class="jiCenter">
		    <!-- <section id="jiSec1" class="jiCenter"> -->
		    <div id="jiSec1" class="jiCenter" align="center">
		    	<% int i = 0; %>
		    	<% for(EventVo event : list) { %>
			    	<div class="thumnailArea">
				    	<div id="thumnailDiv<%=i+1 %>" class ="thumnailArea" onclick="adDetail('<%=event.getPost_code() %>');" style="width:700px; height:200px;">
				    		<img id="thum<%=i+1 %>" src="/mono/upload/ad/사진없음.png" style="border:1px solid black; width:700px; height:200px;"/>
							<div id="titleTermDiv<%=i+1 %>" class="titleTermDiv" style="padding-bottom:20px; width:700px;">
					    		* 제목 : <%=event.getTitle() %><br>
								* 기간 : <%=event.getAdvStartDate() %> - <%=event.getAdvEndDate() %>
						    	<hr>
							</div>
				    	</div>
			    	</div>
			    <% i++; %>	
		    	<% } %>
		    </div>
		    <!-- </section> -->
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>
</html>