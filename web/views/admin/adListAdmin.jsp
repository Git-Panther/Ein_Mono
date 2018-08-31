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
<% ArrayList<EventVo> mainEventList = (ArrayList<EventVo>) request.getAttribute("mainEventList"); %>
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
    	margin-left:8px;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:950px;  height:300px; margin-top:10px;}
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
    
    .btnDiv{
    	border:1px solid white;
    	padding:1px;
    	border-radius:5px;
    	display:inline-block;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    }

    .firstLastPageBtn, .resetSaveWriteBtn, #eventDetailBtn{
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
    
    .thum1, .thum2{
    	display:inline-block;
    }
    #thumnailDiv{
    	margin-top:55px;
    }
    .thumnailArea{
    	padding:10px;
    	padding-top:5px;
    }
    
    
    </style>
    
    <script>
		$(function(){
			// 페이지 로드될 때 메인 이벤트가 있다면 div에 이미지 넣어주기
			<% if(null != mainEventList) { %>
						var mainEventImg = new Array();
						var idx = 0;
						<% for(int i=0; i<mainEventList.size(); i++) { %>
							mainEventImg[idx] = "<%=mainEventList.get(i).getAdvBanner()%>";
							idx++;
						<% } %>
					var thumnailCheckDivs = $(".thumnailArea").children("img");
						thumnailCheckDivs.each(function(idx){
							$(this).attr("src", "/mono/upload/ad/" + mainEventImg[idx]);
					});
			<% } else { %>
						var thumnailCheckDivs = $(".thumnailArea").children("img");
						thumnailCheckDivs.each(function(idx){
							$(this).attr("src", "/mono/upload/ad/사진없음.png");
					});
			<% } %>			
			
			// 상세 페이지 없어서 클릭이벤트 x			
			$(".tableArea td").mouseenter(function(){
				$(this).parent().css("background", "rgba(48, 49, 56,0.8)");
				$(this).parent().css("color", "white");
				$(this).parent().css("cursor", "pointer");
			}).mouseout(function(){
				$(this).parent().css("background", "white");
				$(this).parent().css("color", "black");
			}).click(function(){ 
				// thumnailDiv에 클릭한 순서대로 해당 광고의 배너 이미지가 들어가야함.
				var advCode = $(this).parent().children().eq(1).text() + $(this).parent().children().eq(0).text();
				//console.log(advCode);
				var bannerImg;
				var eCode;
				<% for(EventVo e : list) { %>
							eCode = "<%=e.getPost_code()%>";
							if(eCode == advCode) {
								bannerImg = "<%=e.getAdvBanner()%>";
							}
				<% } %>
				//console.log(bannerImg);
				
				// 같은 광고 못들어가게 해야 해
				var flag = 1;
				var thumnailCheckDivs = $(".thumnailArea").children("img");
				thumnailCheckDivs.each(function(){
					if($(this).attr("src") == "/mono/upload/ad/" + bannerImg) {
						flag = 0;
					}
				});
				var thumnailDivs = $(".thumnailArea").children("img");
				//console.log(thumnailDivs);
				thumnailDivs.each(function(item, idx){
					var flag2 = 1;
					var src = $(this).attr("src");
					
					if(flag == 1 && $(this).attr("src") == "/mono/upload/ad/사진없음.png") {
						$(this).attr("src", "/mono/upload/ad/" + bannerImg);
						flag2 = 0;
						if(flag2 == 0) {
							bannerImg = "사진없음.png";
						}
					} 
				});
				
			});
			
			$(".firstLastPageBtn, .resetSaveWriteBtn, #eventDetailBtn").mouseenter(function(){
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
			location.href = "/mono/selectADListAdmin.do?currentPage=" + pageNum;
		}
		
		function resetThumnail(){
			var thumnailCheckDivs = $(".thumnailArea").children("img");
			thumnailCheckDivs.each(function(){
				$(this).attr("src", "/mono/upload/ad/사진없음.png");
			});
		}
		function saveThumnail(){
			var eThumnailList = new Array();
			var thumnailCheckDivs = $(".thumnailArea").children("img");
			var i = 0;
			thumnailCheckDivs.each(function(){
				eThumnailList[i] = $(this).attr("src");
				i++;
			});
			//console.log(eThumnailList);
			var eCodeList = new Array();
			var j = 0;	
			<% for(int i=0; i<4; i++) { %>
				<% for(EventVo e : list) { %>
						if(eThumnailList[j] == "/mono/upload/ad/<%=e.getAdvBanner()%>") {
							eCodeList[j] = "<%=e.getPost_code()%>";
							j++;
						}
				<% } %>
			<% } %>
			if(eCodeList.length < 4) {
				alert("사진 4개 다 채워야 저장해줌");
			} else {
				location.href = "/mono/updateMainEventList.do?eCodeList=" + eCodeList;
			}
		}
		
		function writeEvent(){
			location.href = "/mono/views/admin/insertEvent.jsp";
		}
		
		function eventDetail(postCode, title){
			//var eCode = $("#eventDetailBtn").parent().parent().children().eq(1).text() + $("#eventDetailBtn").parent().parent().children().eq(0).text();
			var url = "/mono/selectAD.do?postCode=" + postCode + "&requestPage=adminPage";
			window.open(url, title, "left=300, top=50, width=600, height=800, toolbar=1, status = 1, scrollbar=0, resizable = 1, location = no");
		}
	</script>
</head>
<body>
	<div style="margin-left:17px;">
		<header>
			<%@ include file="../common/header.jsp" %>
		</header>
	</div>
	<div id="jiCenter">
		<div class="jiCenter">
		    <nav id="jiNav" class="jiCenter">
		    	<%@ include file="adminPageMenu.jsp" %>
		    </nav>
		    <div align="right" class="jiCenter">
		    	<div id="thumnailDiv"  align="center">
		    		<div id="btnDiv" align="right" style="margin-right:85px;">
		    			<button class="resetSaveWriteBtn" id="resetThumnailBtn" onclick="resetThumnail();" style="width:50px;">초기화</button>
		    			<button class="resetSaveWriteBtn" id="saveThumnailBtn" onclick="saveThumnail();" style="width:50px;">저장</button>
		    		</div>
			    	<div class="thum1">
				    	<div class="thumnailArea" style="width:380px; height:150px;">
				    		<img id="thum1" src="/mono/upload/ad/사진없음.png" style="border:1px solid black; width:380px; height:150px;"/>
				    	</div>
				    	<div class="thumnailArea" style="width:380px; height:150px;">
				    		<img id="thum2" src="/mono/upload/ad/사진없음.png" style="border:1px solid black; width:380px; height:150px;"/>
				    	</div>
			    	</div>
			    	<div class="thum1">
				    	<div class="thumnailArea" style="width:380px; height:150px;">
				    		<img id="thum3" src="/mono/upload/ad/사진없음.png"" style="border:1px solid black; width:380px; height:150px;"/>
				    	</div>
				    	<div class="thumnailArea" style="width:380px; height:150px;">
				    		<img id="thum4" src="/mono/upload/ad/사진없음.png"" style="border:1px solid black; width:380px; height:150px;;"/>
				    	</div>
			    	</div>
			    </div>
			    <section id="jiSec1" class="jiCenter">
				    <div align="right" style="margin-right:80px; padding-bottom:3px;">
				    	<button class="resetSaveWriteBtn" id="writeBtn" onclick="writeEvent();" style="width:56px;">광고 작성</button>
			    	</div>	
			    	<div class="tableArea">
			    		<%-- <% if(null != member && null != list) { %> --%>
						<table align="center" style="width:800px;">
							<tr>
								<!-- <th width="100">글번호</th> -->
								<th width="100">게시글번호</th>
								<th width="100">게시글분류</th>
								<th width="400">제목</th>
								<th width="100">업체명</th>
								<th width="200">기간</th>
								<th width="100"></th>
							</tr>
							<% if(list.size() == 0) { %>
								<tr>
									<td colspan="6">조회된 광고가 없습니다.</td>
								</tr>
								<% } else { %>
								<% for(EventVo event : list) { %>
									<% String post_num = event.getPost_code().substring(3); %>
									<tr>
										<td><%= post_num%></td>
										<td><%= event.getPost_type()%></td>
										<td>	<%= event.getTitle() %></td>
										<td>	<%= event.getMember_name() %></td>
										<td><%= event.getAdvStartDate() %> - <%=event.getAdvEndDate() %></td>
										<td>
											<button id="eventDetailBtn" onclick="eventDetail('<%= event.getPost_code()%>', '<%= event.getTitle()%>');">상세보기</button>
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
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>
</html>