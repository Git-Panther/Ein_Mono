<%@page import="ein.mono.request.model.vo.RequestVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<RequestVo> list = (ArrayList<RequestVo>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>경매 신청 업체 목록</title>
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
		margin-right:auto;
		margin-left:auto;
		width:1100px;
		font-size:20px;		
	}
	.choiceBtn{
		border:1px solid black;
		background:black;
		color:white;
		cursor:pointer;
	}
		.Btns{
		width:1100px;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
		text-align:center;
	}
	.Btn{
		background:rgb(48, 49, 56);
		color:white;
		font-weight:bold;
		font-size:30px;
		text-align:center;
		width:300px;
		padding:15px;
		cursor:pointer;
		font-family:'Gothic A1', sans-serif;
		display:inline-block;
	}
</style>
<script>
	function choicePtn(ptnCode,reqCode){
		location.href = "/mono/insertChoicePtn.do?ptnCode=" + ptnCode + "&reqCode=" + reqCode;
	}
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="outer">
	<div class="top">
		<label style="font-size:40px;">AUCTION : 경매</label>
		<hr width="1080px">
		<label>경매신청 업체리스트 입니다.<br></label>
	</div>
	<div class="tableArea">
		<table align="center">
			<tr>
				<th>업체명</th>
				<th>가격</th>
				<th></th>
			</tr>
			<%if(list.size() == 0){%>
				<tr>
					<td colspan="3">조회된 업체가 없습니다.</td>
				</tr>
			<%}else{ %>
				<%for(RequestVo n : list){ %>
				<tr>
					<td><%=n.getPtnName()%></td>
					<td><%=n.getPtnPay()%></td>
					<td><div class="choiceBtn" onclick="choicePtn('<%=n.getPtnCode()%>','<%=n.getReqCode()%>');">선택하기</div></td>
				</tr>
				<%} %>
			<%} %>
		</table>
	</div>
	<div class="Btns">
		<div class="Btn" onclick="reqList('<%=member.getMemberCode()%>');">목록으로</div>
	</div>
</div>
<script>
	function reqList(mCode){
		location.href="/mono/selectMyRequestList.do?mCode=" + mCode;
	}
</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>