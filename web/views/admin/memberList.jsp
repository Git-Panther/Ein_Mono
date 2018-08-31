<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@page import="ein.mono.partners.model.vo.PartnersVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% ArrayList<MemberVo> list = (ArrayList<MemberVo>) request.getAttribute("list"); %>
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
<% int selectOption = (int) request.getAttribute("selectOption"); %>
<% int condition = (int) request.getAttribute("condition"); %>
<% int searchBtnPressed = (int) request.getAttribute("searchBtnPressed"); %>    
<% String searchContent = (String) request.getAttribute("searchContent"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto;; margin-top:auto;}
    #jiSec1{width:950px;  height:300px; margin-left:30px;}
    #jiArticle1{width:900px; background-color:#efefef;}
    /* aside{float:left; width:104px; height:60px;} */
    footer{overflow:hidden;}
    
    #selectDiv{
   		margin-left:10px;
    	display:inline-block;
    	float:left;
    }
    #searchDiv{
	    margin-right:80px;
	    margin-top:55px;
    	width:800px; 
    	float:right;
    }
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
    
    a:link { color:white; text-decoration: none;}
	a:visited { color:white; text-decoration: none;}
	a:hover { color:black; text-decoration: none;}

    #ptn-license{
    	border-radius:5px;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    }
    .btnDiv{
    	padding:2px;
    	border-radius:5px;
    	display:inline-block;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    }

    .firstLastPageBtn, .pageBtn{
    	margin-top:80px;
    }
    .firstLastPageBtn, #searchBtn{
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
    
    #jiSec2{
		margin-top:75px;
    }
    </style>
    
    <script>
		$(function(){
			$(".tableArea td").mouseenter(function(){
				$(this).parent().css("background", "rgba(48, 49, 56,0.8)");
				$(this).parent().css("color", "white");
				$(this).parent().css("cursor", "pointer");
			}).mouseout(function(){
				$(this).parent().css("background", "white");
				$(this).parent().css("color", "black");
			}).click(function(){
				var member_code = $(this).parent().children().eq(0).text();
				location.href = "/mono/selectMember.do?member_code=" + member_code;
				<%-- location.href = "/mwp/selectBoard.do?boardNo=" + boardNo + "&currentPage=<%=currentPage%>"; --%>
			});
			
			$("#ptn-license, .btnDiv").mouseenter(function(){
				$(this).parent().parent().css("background", "rgba(48, 49, 56,0.8)");
				$(this).parent().parent().css("color", "white");
				$(this).parent().parent().css("cursor", "pointer");
				
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8);");
				$(this).css("color", "white");
			});
			
			$(".firstLastPageBtn, #searchBtn").mouseenter(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8)");
				$(this).css("color", "white");
			});
			$(".pageBtn").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
			
			$("#selectId").change(function(){
				var selectOption = $(this).val();
				location.href = "/mono/selectMemberList.do?currentPage=1&selectOption=" + selectOption;
			});
			
			// 페이지 로드되면 선택한 검색옵션 selected 되어있도록
			var options = $("#selectId").children();
			options.each(function(item, index){
				if(index.value == <%=selectOption%>) {
					$(this).prop("selected", true);
				} else {
					$(this).prop("selected", false);
				}
			});
			
			// 페이지 로드되면 선택한 검색옵션 selected 되어있도록
			var options = $("#searchCondition").children();
			options.each(function(item, index){
				if(index.value == <%=condition%>) {
					$(this).prop("selected", true);
				} else {
					$(this).prop("selected", false);
				}
			});
			
			
			
		});
		
		function searchMember(){
			var searchContent = $("#searchContent").val();
			var condition;
			var options = $("#searchCondition").children();
			options.each(function(item, index){
				if($(this).prop("selected")) {
					condition = $(this).val();
				}
			});
			location.href = "/mono/selectMemberListCon.do?currentPage=1&condition=" + condition + "&searchContent=" + searchContent;
		}
		
		function movePage(pageNum){
			if(<%=searchBtnPressed %> == 0) {
				var selectOption;
				var options = $("#selectId").children();
				options.each(function(item, index){
					if($(this).prop("selected")) {
						selectOption = $(this).val();
					}
				});
				
				location.href = "/mono/selectMemberList.do?currentPage=" + pageNum + "&selectOption=" + selectOption;
			} else {
				var searchContent = $("#searchContent").val();
				var condition;
				var options = $("#searchCondition").children();
				options.each(function(item, index){
					if($(this).prop("selected")) {
						condition = $(this).val();
					}
				});
				location.href = "/mono/selectMemberListCon.do?currentPage=" + pageNum + "&condition=<%=condition%>&searchContent=<%=searchContent%>";
			}
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
			    	<%@ include file="adminPageMenu.jsp" %>
			    </nav>
			    <div align="right" class="jiCenter">
					<div id="searchDiv"  align="right">
						<select id="searchCondition">
							<option value="1" selected>아이디</option>
							<option value="2">이름</option>
				   		</select>
			    		<input type="text" id="searchContent" name="searchContent"  value="<%=searchContent %>"/>
			    		<button id="searchBtn" onclick="searchMember();">검색</button>
				   		<div id="selectDiv" align="left">
					   		<select id="selectId">
								<option value="1" selected>전체</option>
								<option value="2">개인</option>
								<option value="3">업체</option>
					   		</select>
				   		</div>
		    		</div>
				    <section id="jiSec1" >
				    	<div class="tableArea">
				    		<%-- <% if(null != member && null != list) { %> --%>
							<table align="center" style="width:800px;">
								<tr>
									<!-- <th width="100">글번호</th> -->
									<th width="100">회원코드</th>
									<th width="100">이름/업체명</th>
									<th width="100">아이디</th>
									<th width="100">등급</th>
									<th width="150">이메일</th>
									<th width="150">가입일</th>
								</tr>
								<% if(list.size() == 0) { %>
									<tr>
										<td colspan="6">조회된 회원이 없습니다.</td>
									</tr>
									<% } else { %>
									<% for(MemberVo m : list) { %>
										<tr>
											<td><%= m.getMemberCode() %></td>
											<td><%= m.getMemberName() %>	</td>
											<td><%= m.getMemberId() %></td>
											<td>	<%= m.getMemberRank() %></td>
											<td><%= m.getMemberEmail() %></td>
											<td><%= m.getMemberJdate() %></td>
										</tr>
									<% } %>	
								<% } %>
							</table>
							<%-- <% } %> --%>
						</div>	
				        <!-- <article id="jiArticle1">article</article> -->
				    </section>
				    <!-- 페이징 처리 부분 -->
				    <section id="jiSec2">
						<div class="pageArea" align="center">
							<div class="firstLastPageBtn" onclick="movePage(1);"> << </div>
							<% for(int i = startPage; i <= endPage; i++) { %>
								<% if(i == currentPage) { %>
									<div class="pageBtn" style="color:lightgray; border-bottom:1px solid black;"><%=i %></div>
								<% } else { %>
									<div class="pageBtn" onclick="movePage(<%=i %>);"><%=i %></div>
								<% } %>
							<% } %>
							<div class="firstLastPageBtn" onclick="movePage(<%=maxPage %>);"> >> </div>
						</div>
					</section>
				</div>    
		    </div>
	    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>

</html>