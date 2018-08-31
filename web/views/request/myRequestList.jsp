<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.request.model.vo.RequestVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<RequestVo> list = (ArrayList<RequestVo>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	int listCount = pi.getTotalCount();
	int currentPage = pi.getCurrentPage();
	int maxPage = pi.getMaxPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>나의 인테리어 신청목록</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style>
	
	.top{
		margin-top:90px;
		margin-left:auto;
		margin-right:auto;
		width:1100px;
		height:110px;
		background:rgba(48, 49, 56,0.8);
		color:white;
		padding:3px;
	}
	.top label{
		font-family: 'Rubik', sans-serif;
		font-size:20px;
		margin-left:10px;
		padding:10px;
	}
	.outer{
		margin-left:auto;
		margin-right:auto;
	}
	table td{
		text-align:center;
	}
	.tableArea{
		margin-top:10px;
	}
	.reqBtn{
		cursor:pointer;
		border:1px solid black;
		padding:5px;
	}
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="outer">
	<div class="top">
		<label style="font-size:40px;">인테리어</label>
		<hr width="1080px">
		<label>인테리어 리스트 입니다.<br></label>
	</div>
	<div class="tableArea">
		<table align="center">
			<tr>
				<th>구분</th>
				<th>업체명</th>
				<th>시공지 주소</th>
				<th>시공상태</th>
				<th>1</th>
				<th>2</th>
			</tr>
			<%if(list.size() == 0){%>
				<tr>
					<td colspan="6">조회된 경매가 없습니다.</td>
				</tr>
			<%}else{ %>
				<%for(RequestVo n : list){ %>
				<tr>
					<td><%=n.getReqType()%></td>
					<%if(n.getUserName() != null){%>
					<td><%=n.getUserName()%></td>
					<%}else{ %>
					<td>미등록</td>
					<%} %>
					<td><%=n.getConstAddress()%></td>
					<td><%=n.getReqCheck()%></td>
					<td><div class="reqBtn" onclick="requestDetail('<%=n.getReqCode()%>','myList');">견적내용</div></td>
					<%if(n.getReqCheck().equals("경매중")){ %>
						<td><div class="reqBtn" onclick="ptnPage('<%=n.getReqCode()%>');">신청업체목록</div></td>
					<%}else{ %>
						<td><div class="reqBtn" onclick="ptnProfil('<%=n.getPtnCode()%>');">업체프로필</div></td>
					<%} %>
				</tr>
				<%} %>
			<%} %>
		</table>
	</div>
	<!-- 페이징 처리 부분 -->
	<div class="pageArea" align="center">
		<button onclick="movePage(1,'<%=member.getMemberCode()%>');"> << </button>
		<%for(int i = startPage; i<=endPage; i++){ %>
			<%if(i == currentPage){ %>
				<button><%=i%></button>
			<%}else{ %>
				<button onclick="movePage('<%=i%>','<%=member.getMemberCode()%>');"><%=i%></button>
			<%} %>
		<%} %>
		<button onclick="movePage('<%=maxPage%>','<%=member.getMemberCode()%>');"> >> </button>
	</div>
</div>
<script>
	function movePage(pageNum,mCode){
		location.href = "/mono/selectMyRequestList.do?currentPage=" + pageNum + "&mCode=" + mCode;
	}
	
	function requestDetail(reqCode,myList){
		location.href = "/mono/selectRequestDetail.do?reqCode=" + reqCode + "&currentPage=" + <%=currentPage%> + "&page=" + myList;
	}
	
	function ptnPage(reqCode){
		location.href = "/mono/selectChoicePtnList.do?reqCode=" + reqCode ;
	}
	
	function ptnProfil(ptnCode){
		location.href = "/mono/selectPartner.do?partnerCode="+ptnCode;
	}
</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>