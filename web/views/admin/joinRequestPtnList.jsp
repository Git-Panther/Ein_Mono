<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@page import="ein.mono.partners.model.vo.PartnersVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% ArrayList<PartnersVo> list = (ArrayList<PartnersVo>) request.getAttribute("list"); %>
<% /* MemberVo member = (MemberVo) request.getAttribute("user"); */ 
%>    
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
<title>업체 가입 신청 관리</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:950px;  height:300px; margin-left:30px; margin-top:75px;}
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
    	padding:5px;
    	border-bottom:1px solid black;
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
    	margin-top:80px;
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
    </style>
    
    <script>
		$(function(){
			// 상세 페이지 없어서 클릭이벤트 x			
			
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
				$(this).css("color", "white");
			});
			
			$(".firstLastPageBtn").mouseenter(function(){
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
		});
		
		function movePage(pageNum){
			location.href = "/mono/selectJoinRequestPtnList.do?currentPage=" + pageNum;
		}
		
		function updatePtnCheck(ptn_code, flag){
			// 1 : 승인, 0 : 거절
			location.href = "/mono/updatePtnCheck.do?user_code=" + ptn_code + "&flag=" + flag;
		}
		function fileDownload(ptn_license){
			location.href = "/mono/fileDownload.do?filePath=/upload/licence&filename=" + ptn_license;
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
		    <section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
		    		<%-- <% if(null != member && null != list) { %> --%>
					<table align="center" style="width:800px;">
						<tr>
							<!-- <th width="100">글번호</th> -->
							<th width="100">업체명</th>
							<th width="100">아이디</th>
							<th width="150">연락처</th>
							<th width="100">사업자등록증</th>
							<th width="150">가입신청일</th>
							<th width="100">비고</th>
						</tr>
						<% if(list.size() == 0) { %>
							<tr>
								<td colspan="6">가입 신청 내역이 없습니다.</td>
							</tr>
							<% } else { %>
							<% for(PartnersVo p : list) { %>
								<tr>
									<td><%= p.getMemberName() %></td>
									<td><%= p.getMemberId() %></td>
									<td>	<%= p.getMemberTel() %></td>
									<td>
										<div id="ptn-license" onclick="fileDownload('<%=p.getPartnerLicense()%>');">
											첨부파일
											<%-- <a href="/mono/fileDownload.do?filePath=/upload/license&filename=<%=p.getPtn_license()%>">첨부파일</a> --%>
										</div>
									</td>
									<td>
										<%= p.getMemberJdate() %>
									</td>
									<td>
										<div class="btnDiv" onclick="updatePtnCheck('<%=p.getPartnerCode()%>', 1);">승인</div>
										<div class="btnDiv" onclick="updatePtnCheck('<%=p.getPartnerCode()%>',  0);">거절</div>
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
				<% for(int i = startPage; i <= endPage; i++) { %>
					<%if(i == currentPage) { %>
						<div class="pageBtn" style="color:lightgray; border-bottom:1px solid black;"><%=i %></div>
					<% } else { %>
						<div class="pageBtn" onclick="movePage(<%=i %>);"><%=i %></div>
					<% } %>
				<% } %>
				<div class="firstLastPageBtn" onclick="movePage(<%=maxPage %>);"> >> </div>
			</div>
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>

</html>