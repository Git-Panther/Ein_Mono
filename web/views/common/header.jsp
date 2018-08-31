<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%
	MemberVo member = (MemberVo)session.getAttribute("LoginMember");
%>
<meta charset="UTF-8">
<title>MONO</title>
<link href="https://fonts.googleapis.com/css?family=Rubik" rel="stylesheet">

<style>
 .menu{
 	width:1200px;
	margin-left:auto;
	margin-right:auto;
 }
  .null{
	width:1200px;
	height:20px;
  }
  .mainLoginArea{
  	float:right;
  	font-size:15px;
  }
  #loginBtnH, #joinBtnH,#name{
  	display:inline-block;
  	margin-left:20px;
  	cursor:pointer;
  }
  .menuList{
 	margin-top:10px;
  	font-family: 'Rubik', sans-serif;
  }
  .left{
	width:500px;
	border-top:1px solid #939393;
	border-bottom:1px solid #939393;
	float:left;
	font-size:20px;
  }
  .logo{
	width:200px;
	float:left;
	cursor:pointer;
	
  }
  .right{
	width:500px;
	border-top:1px solid #939393;
	border-bottom:1px solid #939393;
	float:left;
	font-size:20px;
  }
 .left li{
	float:left;
	margin-left:30px;
	margin-right:40px;
	vertical-algin:middle;
	list-style:none;
	padding-bottom:13px;
	padding-left:0px;
 }
 .right li{
	float:left;
	margin-left:43px;
	margin-right:40px;
	vertical-algin:middle;
	list-style:none;
	padding-bottom:13px;
 }
 li{
 	cursor:pointer;
 }

 .navi li ul{
 	z-index:2;
 	opacity:0;
 	position:absolute; 
 	width:230px;
 	text-align:center;
 	margin:0px;
 	padding:0px;
 	margin-top:13px;
 }
 .navi li:hover ul{
 	opacity:1;
 }
 .navi li ul li{
 	float:none;
 	position:static;
 	height:0;
 	line-height:0;
 	bakcgorund:none;
 }
 .navi li:nth-child(2) ul{
 	margin-left:-68px;
 }
 .navi li:nth-child(3) ul{
 	margin-left:-78px;
 }
 .navi li:hover ul li{
 	height:30px;
 	line-height:40px;
 	background:rgba(48, 49, 56,0.8);
 	color:white;
 }
 .navi li ul{
 	transition:all 0.5s;
 }
 .navi li ul li{
 	transition:all 0.5s;
 }
 label{
 	cursor:pointer;
 }
 #info, #memberInfoBtn, #memberLogoutBtn, .memberMyPageBtn{
	 display:inline-block;
	 font-weight:bold;
	 }
	 #memberInfoBtn, #memberLogoutBtn, .memberMyPageBtn{
	 cursor:pointer;
	 }
  </style>
  <script>
  function mainPage(){
	  location.href="/mono/index.jsp";
  }
  
  function monoPage(){
	  location.href="/mono/views/common/mono.jsp";
  }
  
  function joinPage(){
	  location.href="/mono/views/member/joinMember.jsp";
  }
  function faqPage(){
	  location.href="/mono/selectFAQList.do?post_type=FAQ&requestPage=header";
  }
  function auctionPage(auction){
	  <%if(member != null){%>
		  <%if(member.getMemberRank().equals("일반회원")){%>
		 	  location.href="/mono/views/request/requestForm.jsp?page="+auction;  
		  <%}else if(member.getMemberRank().equals("미승인업체")){%>
			  alert("승인을 먼저 해주시길 바랍니다.");	
		  <%}else{%>
		  	  location.href="/mono/selectRequestList.do";
		  <%}%>
	  <%}else{%>
		  alert("로그인을 먼저 해주시길 바랍니다.");		  
	  <%}%>
  }
  function selectPtn(choice){
	  location.href="/mono/views/request/requestForm.jsp?page="+choice;
  }
  function loginPage(){
	  var loc = location.href;
	  loc = loc.replace(/&/gi, "☆★");
	  location.href="/mono/views/member/loginMember.jsp?recentlyPath="+loc;
  }
  function logoutPage(){
	  var loc = location.href;
	  loc = loc.replace(/&/gi, "☆★");
	  location.href="/mono/logoutMember.do?recentlyPath="+loc;
  }
  function myPage(){
	  location.href="/mono/views/mypage/mypage.jsp";
  }
  function boardPage(posttype){
	  location.href="/mono/selectPostList.do?posttype="+posttype;
  }
  function noticePage(){
	  location.href="/mono/noticeList.do";
  }
  function partnersPage(){
	  location.href="/mono/selectPartnersListMain.do";
  }
  function adListPage(){
	  location.href = "/mono/selectADList.do";
  }
  function adminPage(){
	  location.href = "/mono/views/admin/adminPageMain.jsp";
  }
  </script>
</head>
<body>
	<header>
	<div class="menu">
		<div class="null">
		</div>
		<div class="mainLoginArea">
			<%if(member == null) {%>
			<div id="loginBtnH" onclick="loginPage();">LOGIN</div>
			<div id="joinBtnH" onclick="joinPage();">JOIN</div>
			<%}else{ %>
			<div id="name"><%=member.getMemberName()%>님 방문을 환영합니다.</div>
				<% if(member.getMemberId().equals("admin")) { %>
					<div class = "memberMyPageBtn" onclick = "adminPage();">MYPAGE</div>&emsp;
				<% } else {%>
					<div class="memberMyPageBtn" onclick="myPage();">MYPAGE</div>&emsp;
				<% } %>
			<!-- 
			 <div id="myPageBtn" onclick="myPage();">MY PAGE</div>
			 -->
			<div id="memberLogoutBtn" onclick="logoutPage();">LOGOUT</div>
			<%} %>		
		</div>
		<br>
		<div class="menuList" >
			<nav>
				<div class="left">
					<ul class="navi">
						<li onclick="monoPage();">MONO?</li>
						<li>
							<label onclick="auctionPage('auction');">REQUEST</label>
							<ul>
								<li onclick="auctionPage('auction');">경매</li>
								<li onclick="partnersPage();">업체지정</li>			
							</ul>
						</li>
						<li>
							<label onclick="boardPage('FRE');">BOARD</label>
							<ul>
								<li onclick="boardPage('FRE');">자유게시판</li>
								<li onclick="boardPage('SHO');">마이룸</li>
								<li onclick="boardPage('MAR');">오픈마켓</li>
								<li onclick="boardPage('REV');">후기게시판</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="logo" onclick="mainPage();">
					<img src="/mono/images/logo3.png" width="200px" height="60px"/>
				</div>
				<div class="right">
					<ul>
						<li onclick="faqPage();">FAQ</li>
						<li onclick="noticePage();">NOTICE</li>
						<li onclick="adListPage();">EVENT</li>
					</ul>
	   			</div>
			</nav>
		</div>
	</div>
	</header>
</body>
</html>