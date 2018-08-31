<%@page import="ein.mono.board.model.vo.ReplyVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PostVo post = (PostVo) request.getAttribute("post");
	ArrayList<ReplyVo> reply = (ArrayList<ReplyVo>) request.getAttribute("reply");
	MemberVo user = (MemberVo) session.getAttribute("LoginMember");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style>
.outer {
	width: 1000px;
	height: auto;
	margin-left: auto;
	margin-right: auto;
}

#sky {
	width: 1000px;
	height: 30px;
	border: solid 1px gray;
	margin-left: auto;
	margin-right: auto;
}

#btnDiv {
	width: 150px;
	height: 40px;
	margin-left: auto;
	margin-right: auto;
	margin-top:10px;
}

#titleDiv {
	border: 1px solid gray;
	font-size: 20px;
}

#replyDiv, #writeDiv {
	width: 1000px;
	height: auto;
	margin-left: auto;
	margin-right: auto;
}

#replyBtn {
	width: auto;
	height: 35px;
}
#replyTable{
	width: 1000px;
	height: auto;
	margin-left: auto;
	margin-right: auto;
	text-align:left;
	background:white;
}
.deleteReply{
	cursor:pointer;
}
</style>
<script>
	function modifyPost(){
		location.href = "/mono/modifyPostForm.do?pCode=<%=post.getPost_code()%>";
	}
	function removePost(){
		location.href = "/mono/removePost.do?pCode=<%=post.getPost_code()%>&pType=<%=post.getPost_type()%>";
	}
	function reportPost(){
		location.href = "/mono/insertReport.do?reported=<%=post.getWriter_code()%>&post_code=<%=post.getPost_code()%>";
	}
	function reportBtn(rCode){
		location.href = "/mono/insertReport.do?reported=<%=post.getWriter_code()%>&reply_code="+rCode;
	}
	function deleteReply(rCode){
		 /* var remove = $("div#replyTable").remove(); */
		 $("#replyTable").html("");
		var pCode = "<%=post.getPost_code()%>";
		var userCode = "<%=user.getMemberCode()%>";
		$.ajax({
			url : "/mono/deleteReply.do",
			type : "get",
			data : { pCode : pCode, rCode : rCode},
			success : function(data){
				var str = "";
				if(data.length > 0){
					 for(i=0; i<data.length; i++){
						 str += "<table id='replyTable'>";
						 str += "<tr><th rowspan='2'>"+data[i].member_nName+"</th><td>"+data[i].reply_dateSte+"</td>";
						 str += "<td><div style='float:right;'>";
						 if(userCode != data[i].writer_code) {
							 str += "<a href = '/mono/insertReport.do?reported="+data[i].writer_code+"&reply_code="+data[i].reply_code+"';>신고</a>";
						 }
						 if(userCode == data[i].writer_code) {
							 str += "<input type='button' name='deleteReplyBtn' value='삭제' onclick='deleteReply(\"" + data[i].reply_code +"\");'/>";
						 }
						 str += "</div></td></tr>";
						 str += "<tr><td colspan='3'>"+data[i].reply_content+"</td>";
						 str += "</tr>";
						 str += "</table>";
						 str += "<div>";
		             }			                
				} else {			                
		            str += "<table>";
		            str += "<tr><th colspan='3'>등록 된 댓글이 없습니다.</th></tr>";
		            str += "</table>";			                
		        }
				$("#replyDiv").html(str);
				$("#contents").val("");
			},error : function(data){
				console.log(data);
			}
		});
	}
	
	
</script>

</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<br>
	<br>
	<br>
	<h1 align="center">게.시.글 보.기</h1>
	<div class="outer">
		<table id="sky">
			<tr>
				<th>글 제목</th>
				<td colspan="5"><%=post.getTitle()%></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=post.getWriter_nickname()%></td>
				<th>작성일</th>
				<td><%=post.getWritten_date()%></td>
				<th>조회수</th>
				<td><%=post.getViews_count()+1%></td>
			</tr>
			<tr>
				<td colspan="6" style="height:300px;"><%=post.getContent()%></td>
			</tr>
		</table>
		<div id="btnDiv">
			<%if(member != null && post.getWriter_code().equals(member.getMemberCode())) {%>
			<button onclick="modifyPost();">수정</button>
			<button onclick="removePost();">삭제</button>
			<%} %>
			<button onclick="reportPost();">신고</button>
		</div>
		<br> <br> <br>
	</div>
	<hr/>
	<div id="replyDiv">
		<table id="replyTable">
			<%
				for (int i = 0; i < reply.size(); i++) {
			%>
			<tr>
				<th rowspan="2"><%=reply.get(i).getMember_nName()%></th>
				<td><%=reply.get(i).getReply_dateSte()%></td>
				<td>
					<div style="float:right;">
						<% if(! user.getMemberCode().equals(reply.get(i).getWriter_code())){ %>
							<a href="/mono/insertReport.do?reported=<%=reply.get(i).getWriter_code()%>&reply_code=<%=reply.get(i).getReply_code()%>">신고</a>
						<% } %>
						<% if(user.getMemberCode().equals(reply.get(i).getWriter_code())){ %>
							<input type="button" name="deleteReplyBtn" value="삭제" onclick="deleteReply('<%=reply.get(i).getReply_code()%>');"/>
						<% } %>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3"><%=reply.get(i).getReply_content()%></td>
			</tr>
			<%
				}
			%>
		</table>

	</div>
	<hr/>
	<br>
	<%if(null != member){ %>
	<div id="writeDiv">
		<!-- 댓글 쓰기<br> -->
		<textarea rows="3" cols="135" id="contents" placeholder="댓글 내용을 입력해 주세요."></textarea>
		<input type="hidden" value="<%=member.getMemberCode() %>" id="mCode">
		<button id="replyBtn" Accesskey="13" style="float:right;">댓글 작성</button>
	</div>
	<%} %>
	<br><br><br><br>
	
	<script>
	$(function(){		
		$("#replyBtn").click(function(){
			 /* var remove = $("div#replyTable").remove(); */
			 $("#replyTable").html("");
			 var mCode = $("#mCode").val();
			var pCode = "<%=post.getPost_code()%>";
			var content = $("#contents").val();
			var userCode = "<%=user.getMemberCode()%>";
			$.ajax({
				url : "/mono/insertReply.do",
				type : "get",
				data : { content : content , pCode : pCode, mCode : mCode},
				success : function(data){
					var str = "";
					if(data.length > 0){
						 for(i=0; i<data.length; i++){
							 str += "<table id='replyTable'>";
							 str += "<tr><th rowspan='2'>"+data[i].member_nName+"</th><td>"+data[i].reply_dateSte+"</td>";
							 str += "<td><div style='float:right;'>";
							 if(userCode != data[i].writer_code) {
								 str += "<a href = '/mono/insertReport.do?reported="+data[i].writer_code+"&reply_code="+data[i].reply_code+"';>신고</a>";
							 }
							 if(userCode == data[i].writer_code) {
								 str += "<input type='button' name='deleteReplyBtn' value='삭제' onclick='deleteReply(\"" + data[i].reply_code +"\");'/>";
							 }
							 str += "</div></td></tr>";
							 str += "<tr><td colspan='3'>"+data[i].reply_content+"</td>";
							 str += "</tr>";
							 str += "</table>";
							 str += "<div>";
			             }			                
					} else {			                
			            str += "<table>";
			            str += "<tr><th colspan='3'>등록 된 댓글이 없습니다.</th></tr>";
			            str += "</table>";			                
			        }
					$("#replyDiv").html(str);
					$("#contents").val("");
				},error : function(data){
					console.log(data);
				}
			});
		});
				
		$("#contents").keyup(function(key){
			if(key.keyCode == 13){
				$("#replyBtn").click();
			}
		});
		
	});
	</script>

</body>
</html>