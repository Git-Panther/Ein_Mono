<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="ein.mono.qna.model.vo.QnAVo"%>
<%@ page import="ein.mono.common.PageInfo"%>
<%@ include file="/views/common/header.jsp" %>
<%@ include file="/views/common/footer.jsp" %>
<%
	ArrayList<QnAVo> list = (ArrayList<QnAVo>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");	
	int listCount = -1;
	int currentPage = -1;
	int maxPage = -1;
	int startPage = -1;
	int endPage = -1;
	
	String partnerCode = (String)request.getAttribute("partnerCode");
	String listType = (String)request.getAttribute("listType");
	String partnerName = (String)request.getAttribute("partnerName");
	String condition = null; // 표시 조건
	//request.setAttribute("partnerCode", partnerCode);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Q&A 게시판</title>
<link rel="stylesheet" href="/mono/css/qnaList.css?ver=1" type="text/css"/>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<%if(null != list && null != pi){
	listCount = pi.getTotalCount();
	currentPage = pi.getCurrentPage();
	maxPage = pi.getMaxPage();
	startPage = pi.getStartPage();
	endPage = pi.getEndPage();	
	request.setAttribute("currentPage", currentPage); // 상세정보 페이지 전용. 리스트 넘기는 건 parameter로 한다.
%>
<div class="qnaListArea">
<div class="qnaTable">
	<div class="tableArea">
		<div class="ptnCategory">Q&A 게시판 : <%=partnerName%></div>
		<table align="center">
			<tr>
				<th width="100">글번호</th>
				<th width="300">글제목</th>
				<th width="100">작성자</th>
				<th width="100">조회수</th>
				<th width="150">작성일</th>
			</tr>
			<%if(list.size() == 0){ %>
				<tr>
					<td id="none" colspan="5" >조회된 게시글이 없습니다.</td>
				</tr>
			<%}else{ %>
				<%for(QnAVo qna : list){ %>
				<tr>
					<td><%=qna.getPost_num()%></td>
					<!-- a href="/mono/selectQnA.do?post_code=pCode -->
					<td><a href="/mono/detailPost.do?pCode=<%=qna.getPost_code()%>&partnerCode=<%=partnerCode%>"><%=qna.getTitle()%><%if(qna.getReply_count() != 0){%>[<%=qna.getReply_count()%>]<%}%></a></td>				
					<td><%=null == qna.getWriter_nickname() ? qna.getWriter_name() : qna.getWriter_nickname()%></td>			
					<td><%=qna.getViews_count()%></td>			
					<td><%=qna.getWritten_date()%></td>				
				</tr>
				<%} %>
			<%} %>
		</table>
	</div>
	<!-- 페이지 -->
	<%if(null != pi && 0 != list.size()){%>
	<div class="pageArea">
		<p <%if(1 != pi.getCurrentPage()){%>onclick="movePage(1)" class="pointer"<%}else{%>class="disactive"<%}%>> << </p>
		<% for(int i = pi.getStartPage(); i <= pi.getEndPage(); i++){ %>
			<p
			<%if(i != pi.getCurrentPage()){%>
			onclick="movePage(<%=i%>)" class="pointer"
			<%}else{%>
			class="pageSelected"
			<%}%>
			><%=i%></p>		
		<% }%>
		<p <%if(pi.getMaxPage() != pi.getCurrentPage()){%>onclick="movePage(<%=pi.getMaxPage()%>)" class="pointer"<%}else{%>class="disactive"<%}%>> >> </p>
	</div>
	<%}%>
	<br>

	<div class="searchArea">
		<select id="searchCondition">
			<option value="title">제목</option>
			<option value="content">내용</option>
			<option value="writer_name,writer_nname">작성자</option>
		</select>		
		<input type="text" id="searchText" placeholder="검색어 입력"/>
		<input type="button" value="검색하기" onclick="searchQnA();"/>
		<%if(null != member && member.getMemberCode().charAt(0) == 'B'){ %>
			<input type="button" value="작성하기" onclick="writeQnA();"/>
		<%} %>
	</div>
</div>
</div>
<script>
	function searchQnA(){
		var condition = $("#searchCondition").val();
		var keyword = $("#searchText").val();
		var notKorEngNumSpace = /[^(A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ\s)]/gi;
		//쿼리 스트링(파라미터 값 작성 방법)
		//url?key1=value1&key2=value2;
		
		keyword = keyword.replace(notKorEngNumSpace, "");
		if("" == keyword) keyword = "▒";
		
		location.href = '/mono/searchQnAList.do?partnerCode=<%=partnerCode%>&condition='+condition+'&keyword='+keyword;
	}
	
	function writeQnA(){
		location.href = '/mono/views/post/writeGalleryPost.jsp?pType=QNA&partnerCode=<%=partnerCode%>';
	}
	
	function movePage(pageNum){ // 여기부터 시작
		<%if(listType.contains("Search")){%>
			var condition = "";
			var keyword = "";
			<%if(listType.contains("title")){%>
				condition = "title";
			<%}else if(listType.contains("content")){%>
				condition = "content";
			<%}else if(listType.contains("writer_name,writer_nname")){%>
				condition = "writer_name,writer_nname";
			<%}%>
			keyword = "<%=listType.split(":")[1]%>";
			location.href = '/mono/searchQnAList.do?partnerCode=<%=partnerCode%>&condition='+condition+'&keyword='+keyword+'&currentPage='+pageNum;
			<%}else if(listType.contains("Common")){%>
			location.href= '/mono/selectQnAList.do?partnerCode=<%=partnerCode%>&currentPage='+pageNum;
		<%}%>
	}
	
	$(function(){
		$("#searchText").keyup(function (key) {		 
	        if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
	        	searchQnA();
	        }
	    });
		
		$(".ptnCategory").click(function(){
			location.href = "/mono/selectQnAList.do?partnerCode=<%=partnerCode%>";
		});
	});
</script>
<%}%>
</body>
</html>