<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="ein.mono.partners.model.vo.PartnersVo"%>
<%@ page import="ein.mono.common.PageInfo"%>
<%@ include file="/views/common/header.jsp" %>
<%@ include file="/views/common/footer.jsp" %>
<%
	String listType = (String)request.getAttribute("listType");
	ArrayList<PartnersVo> list = (ArrayList<PartnersVo>)request.getAttribute("list");
	// listType.contains()로 비교하여 분할
	Iterator<PartnersVo> itrList = null;
	PartnersVo ptnPreview = null;
	String condition = null; // 표시 조건	
	//int listIndex = 0; // 화면에 표시되는 리스트 인덱스
	PageInfo pi = (PageInfo)request.getAttribute("pi");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/mono/css/partnerList.css?ver=1" type="text/css"/>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js">
	function selectPartner(partnerCode){
		location.href = "/mono/selectPartner.do?partnerCode="+partnerCode;
	}
	
	$(function(){
		
	});
</script>
</head>
<body>
<div class="ptnListArea">
<%if(null != list){	%>
	<%if(listType.contains("Main")){%>
	<div class="ptnCategory"><a href="/mono/selectPartnersList.do?category=우수업체">우수 업체</a></div><br><br>
	<table>
		<tr>
			<%
			ArrayList<PartnersVo> bestList = (ArrayList<PartnersVo>)request.getAttribute("bestList");
			if(0 != bestList.size()){
				itrList = bestList.iterator();
				while(itrList.hasNext()){
					ptnPreview = itrList.next();
			%>
			<td>
			<div class="ptnPreview">	
				<table>
					<tr>	
						<td colspan="4">
							<a href="/mono/selectPartner.do?partnerCode=<%=ptnPreview.getPartnerCode()%>"><img class="ptnPreviewPhoto" alt="none" src="/mono/upload/partners/<%=ptnPreview.getPtnPhotos()%>"></a>				
							<!--
							 	img 꼭 로고로 바꿔야겠다면 
							<a href="/mono/selectPartner.do?partnerCode=<%=ptnPreview.getPartnerCode()%>"><img class="ptnPreviewPhoto" alt="none" src="/mono/upload/partners/<%=ptnPreview.getPartnerLogo()%>"></a>				
							위 코드를 바로 위 a 태그 대신에 넣어주면 해결
							 -->
						</td>
					</tr>
					<tr>
						<td><%=ptnPreview.getMemberName()%></td><td><%=ptnPreview.getPartnerLocation()%></td><td><%=ptnPreview.getPartnerStyles()%></td><td>★<%=Math.floor(ptnPreview.getMetascore()*10)/10%></td>
					</tr>
				</table>
			</div>
			</td>
	<%}
	}else{%>
	<td><h1>검색결과가 존재하지 않습니다.</h1></td>
	<%}%>
		</tr>
	</table>	
	<br><div class="ptnCategory"><a href="/mono/selectPartnersList.do?category=All">전체</a></div><br><br>
	<%}else if(listType.contains("Search")){
		String listInfo = listType.replace("Search_", "");
		if(listInfo.contains("ptn_name")) {
			listInfo = listInfo.replace("ptn_name", "");
			condition = "업체명";
		}else if(listInfo.contains("ptn_location")){
			listInfo = listInfo.replace("ptn_location", "");
			condition = "지역";
		}else if(listInfo.contains("ptn_styles")){
			listInfo = listInfo.replace("ptn_styles", "");
			condition = "스타일";
		}
		
		if(listInfo.contains("▒")) listInfo = listInfo.replaceAll("▒", "");
	%>
	<div class="ptnCategory">검색 결과(<%=condition+listInfo%>)</div><br><br>
	<%}else if(listType.contains("Category")){
		if(listType.contains("All")) condition = "전체";
		else if(listType.contains("우수업체")) condition = "우수 업체";
	%>
	<div class="ptnCategory"><%=condition%></div><br><br>
	<%}
		int colCount = 0;
	%>
	<table>
		<%
		if(0 != list.size()){	
			itrList = list.iterator();
			while(itrList.hasNext()){
				if(0 == colCount % 3){
		%><tr>
		<%
				}
				ptnPreview = itrList.next();
		%>
			<td>
				<div class="ptnPreview">
					<table>
						<tr>	
							<td colspan="4">
								<a href="/mono/selectPartner.do?partnerCode=<%=ptnPreview.getPartnerCode()%>"><img class="ptnPreviewPhoto" alt="none" src="/mono/upload/partners/<%=ptnPreview.getPtnPhotos()%>"></a>	
								<!--
							 	img 꼭 로고로 바꿔야겠다면 
								<a href="/mono/selectPartner.do?partnerCode=<%=ptnPreview.getPartnerCode()%>"><img class="ptnPreviewPhoto" alt="none" src="/mono/upload/partners/<%=ptnPreview.getPartnerLogo()%>"></a>				
								위 코드를 바로 위 a 태그 대신에 넣어주면 해결
								 -->
							</td>
						</tr>
						<tr>
							<td><%=ptnPreview.getMemberName()%></td><td><%=ptnPreview.getPartnerLocation()%></td><td><%=ptnPreview.getPartnerStyles()%></td><td>★<%=Math.floor(ptnPreview.getMetascore()*10)/10%></td>
						</tr>
					</table>
				</div>
			</td>
				<%if(2 == colCount % 3){%>
		</tr>
		<%			
				}colCount++;
			}
		}else{%>
		<tr><td><h1>검색결과가 존재하지 않습니다.</h1></td></tr>
		<%} %>
	</table>
<%}else{%>
	<h1><%=(String)request.getAttribute("msg")%></h1>
<%}%>
<%if(null != pi && 0 != list.size()){%>
	<div class="pageArea">
		<p <%if(1 != pi.getCurrentPage()){%>class="pointer" onclick="movePage(1)"<%}else{%>class="disactive"<%}%>> << </p>
		<% for(int i = pi.getStartPage(); i <= pi.getEndPage(); i++){ %>
			<p
			<%if(i != pi.getCurrentPage()){%>
			onclick="movePage(<%=i%>)" class="pointer" 
			<%}else{%>
			class="pageSelected"
			<%}%>
			><%=i%></p>		
		<% }%>
		<p <%if(pi.getMaxPage() != pi.getCurrentPage()){%>class="pointer"  onclick="movePage(<%=pi.getMaxPage()%>)"<%}else{%>class="disactive"<%}%>> >> </p>
	</div>
<%}%>
	<br>
	<div class="searchArea">
		<select id="searchCondition">
			<option value="ptn_name">업체명</option>
			<option value="ptn_location">지역</option>
			<option value="ptn_styles">스타일</option>
		</select>		
		<input type="text" id="searchText" placeholder="검색어 입력"/>
		<input type="button" value="검색하기" onclick="searchPartners();"/>
	</div>
</div>
<script>
	function searchPartners(){
		var condition = $("#searchCondition").val();
		var keyword = $("#searchText").val();
		var notKorEngNumSpace = /[^(A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ\s)]/gi;
		//쿼리 스트링(파라미터 값 작성 방법)
		//url?key1=value1&key2=value2;		
		keyword = keyword.replace(notKorEngNumSpace, "");
		if("" == keyword) keyword = "▒";
		location.href = "/mono/searchPartnersList.do?condition="+condition+"&keyword="+keyword;		
	}
	
	function movePage(pageNum){ // 여기부터 시작
		<%if(listType.contains("Search")){%>
			var condition = "";
			var keyword = "";
			<%if(listType.contains("ptn_name")){%>
				condition = "ptn_name";
			<%}else if(listType.contains("ptn_location")){%>
				condition = "ptn_location";
			<%}else if(listType.contains("ptn_styles")){%>
				condition = "ptn_styles";
			<%}%>
			keyword = "<%=listType.split(":")[1]%>";
			location.href = "/mono/searchPartnersList.do?condition="+condition+"&keyword="+keyword+"&currentPage=" + pageNum;
			<%}else if(listType.contains("Category")){%>
			var category = "All";
			<%if(listType.contains("우수업체")){%>
				category = "우수업체";
			<%}%>
			location.href="/mono/selectPartnersList.do?category="+category+"&currentPage=" + pageNum;
		<%}%>
	}
	
	$(function(){
		$("#searchText").keyup(function (key) {		 
	        if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
	        	searchPartners();
	        }
	    });
	});
</script>
</body>
</html>