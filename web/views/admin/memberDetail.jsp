<%@page import="ein.mono.partners.model.vo.PartnersVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% MemberVo m = (MemberVo) request.getAttribute("mem"); %>   
<% int flag = (int) request.getAttribute("flag"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 상세 화면</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:950px;  height:300px; margin-left:30px; margin-top:75px;}
    #jiArticle1{width:800px; height:150px; background-color:white; border-bottom:1px solid black;}
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

	 .cancelUpdateBtn{
    	margin-top:60px;
    }
    .cancelUpdateBtn, #updateRankBtn{
    	border:1px solid white;
    	padding:3px;
    	border-radius:5px;
    	display:inline-block;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    	font-size:11px;
    }
    #title{
    	float:left;
    }
    </style>
    
    <script>
		$(function(){
			$(".cancelUpdateBtn, #updateRankBtn").mouseenter(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8)");
				$(this).css("color", "white");
			});
			$(".cancelUpdateBtn, #updateRankBtn").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
			
			if(<%=flag%> == 1) {
				var options = $("#rankSelect").children();
				options.each(function(item, index){
					if(index.value == "<%=m.getMemberRank()%>") {
						$(this).prop("selected", true);
					} else {
						$(this).prop("selected", false);
					}
				});
			}
		});	
		
		function cancel(){
			location.href = "/mono/selectMemberList.do?selectOption=1";
		}
		function updateRank(member_code){
			location.href = "/mono/selectMember.do?member_code=" + member_code + "&flag=1";
		}
		function update(member_code){
			var rank;
			var options = $("#rankSelect").children();
			options.each(function(item, index){
				if($(this).prop("selected")) {
					rank = $(this).val();
				}
			});
			location.href = "/mono/updateMemberRank.do?member_code=" + member_code + "&rank=" + rank;
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
					<!-- <form id="memberForm" method="post" action="/mono/updateMember.do"> -->
			    		<%-- <% if(null != member && null != list) { %> --%>
						<%-- <input type="hidden" id="post_code" name="post_code" value="<%=post.getPost_code() %>"/>
						<input type="hidden" id="post_type" name="post_type" value="<%=post.getPost_type() %>"/> --%>
						<table align="center" style="width:800px;">
							<tr>
								<td colspan="2" rowspan="5"  style="border-top:1px solid black;">
									<div id="profileImgDiv"  align="center" style="width:130px; height:130px; margin:auto;">
									<% if(m.getMemberCode().charAt(0) == 'C') { %>
									<% PartnersVo partner = (PartnersVo) request.getAttribute("m"); %> 
										<%-- <% if(! "".equals(partner.getPartnerLogo()) && null != partner.getPartnerLogo()) { %>  
											<img src="/mono/images/<%=partner.getPartnerLogo() %>" width="130px" height="130px"/>
										<% } else { %> --%>
											<img src="/mono/images/profileImgSample.png" width="130px" height="130px"/>
										<%-- <% } %> --%>
									<% } else { %>
										<img src="/mono/images/profileImgSample.png" width="130px" height="130px"/>
									<% } %>
									</div>
								</td>
								<th width="100">회원코드</th>
								<td width="200"  style="border-top:1px solid black;"><%=m.getMemberCode() %></td>
								<th width="100">이름/업체명</th>
								<td width="200"  style="border-top:1px solid black;"><%=m.getMemberName() %></td>
							</tr>
							<tr>
								<th width="100">아이디<br>(닉네임)</th>
								<td width="200"><%=m.getMemberId() %><br>(<%=m.getMemberNname() %>)</td>
								<th width="100" >회원등급</th>
								<td width="200">
								<% if(flag == 0) { %>
									<%=m.getMemberRank() %><br>
									<button id="updateRankBtn" onclick="updateRank('<%=m.getMemberCode()%>');">등급변경</button>
								<% } else { %>
									<select id="rankSelect">
										<option value="일반회원">일반회원</option>
										<option value="일반업체">일반업체</option>
										<option value="우수업체">우수업체</option>
										<option value="미승인업체">미승인업체</option>
										<option value="관리자">관리자</option>
									</select>
								<% } %>
								</td>
							</tr>
							<tr>
								<th width="100">연락처</th>
								<td><%=m.getMemberTel() %></td>
								<th width="100">가입일</th>
								<td><%=m.getMemberJdate() %></td>
							</tr>
							<tr>
								<th width="100">주소</th>
								<td colspan="4"><%=m.getMemberAddress() %></td>
							</tr>
							<tr>
								<th width="100">정지시작일</th>
								<% if(m.getBan_startdate() == null) { %>
									<td> - </td>
								<% } else { %>
									<%=m.getBan_startdate() %>
								<% } %>
								<th width="100">정지종료일</th>
								<% if(m.getBan_enddate() == null) { %>
									<td> - </td>
								<% } else { %>
									<%=m.getBan_enddate() %>
								<% } %>
							</tr>
							<% if(m.getMemberCode().charAt(0) == 'C' && null != request.getAttribute("m")) { %>	
							<% PartnersVo partner = (PartnersVo) request.getAttribute("m"); %>  
								<tr>
									<th width="100" colspan="2">평점</th>
									<th>스타일</th>
									<td><%=partner.getPartnerStyles() %></td>
									<th>시공지역</th>
									<td><%=partner.getPartnerLocation() %></td>
								</tr>
								<tr>
									<td colspan="2" rowspan="3">★<%=Math.floor(partner.getMetascore()*10)/10%></td>
								</tr>
								<tr>
									<th>상담시간</th>
									<td colspan="5">평일 <%=partner.getWeekdaysStart() %> - <%=partner.getWeekdaysEnd() %>
											/ 주말 <%=partner.getWeekendStart() %> - <%=partner.getWeekendEnd() %></td>
								</tr>
								<tr>
									<th>문의</th>
									<td colspan="5"></td>
								</tr>
							<% } %>
						</table>
					<!-- </form> -->
					<%-- <% } %> --%>
				</div>
		    </section>
			<div class="pageArea" align="center">
				<div class="cancelUpdateBtn" id="cancelBtn" onclick="cancel();">취소</div>
				<div class="cancelUpdateBtn" id="updateBtn" onclick="update('<%=m.getMemberCode()%>');">수정</div>
			</div> 
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>
</html>