<%@page import="ein.mono.board.model.vo.ReplyVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<%
	PostVo post = (PostVo) request.getAttribute("post");
	ArrayList<ReplyVo> reply = (ArrayList<ReplyVo>) request.getAttribute("reply");

	boolean postIsMine = (null != member) && (post.getWriter_code().equals(member.getMemberCode()));
	String memberCode = null;
	if(null != member) memberCode = member.getMemberCode();

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
</head>
<body>
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
			<%if(postIsMine) {%>
			<button onclick="modifyPost();">수정</button>
			<button onclick="removePost();">삭제</button>
			<%}else{%>
			<button id="reportPostBtn" onclick="reportPost();">신고</button>
			<%}%>
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
					<%if(  !( (null != member) && (reply.get(i).getWriter_code().equals( member.getMemberCode() ) ) )  ){%>
						<a href='javascript:reportReply("<%=reply.get(i).getWriter_code()%>", "<%=reply.get(i).getReply_code()%>");'>신고</a>
					<%}%>
					<% if(user.getMemberCode().equals(reply.get(i).getWriter_code())){ %>
						<input type="button" name="deleteReplyBtn" value="삭제" onclick="deleteReply('<%=reply.get(i).getReply_code()%>');"/>
					<%}%>
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
		function modifyPost(){
			location.href = "/mono/modifyPostForm.do?pCode=<%=post.getPost_code()%>";
		}
		function removePost(){
			location.href = "/mono/removePost.do?pCode=<%=post.getPost_code()%>&pType=<%=post.getPost_type()%>";
		}
		function reportPost(){
			if(<%=null != member%>){
				location.href = "/mono/insertReport.do?reportedcode=<%=post.getWriter_code()%>&postcode=<%=post.getPost_code()%>";
			}else{
				alert("로그인 후에 신고할 수 있습니다!");	
			}
		}
		function reportReply(writerCode, replyCode){
			if(<%=null != member%>){
				location.href = "/mono/insertReport.do?reportedcode="+writerCode+"&replycode="+replyCode;
			}else{
				alert("로그인 후에 신고할 수 있습니다!");	
			}
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
							 if(data[i].writer_code != '<%=memberCode%>'){
								str +=  "<a href = 'javascript:reportReply("
									+ '"' + data[i].writer_code + '"'
									+ ', "' + data[i].reply_code+ '"'
									+ ");'>신고</a>";
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
	<script>
	$(function(){
		//console.log(<%=postIsMine%>);		
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
							//console.log(data[i].writer_code != '<%=memberCode%>');
							if(data[i].writer_code != '<%=memberCode%>'){
								str +=  "<a href = 'javascript:reportReply("
									+ '"' + data[i].writer_code + '"'
									+ ', "' + data[i].reply_code+ '"'
									+ ");'>신고</a>";
							}
							if(userCode == data[i].writer_code) {
								str += "<input type='button' name='deleteReplyBtn' value='삭제' onclick='deleteReply(\"" + data[i].reply_code +"\");'/>";
							}
							str += "</div></td></tr>";
							str += "<tr><td colspan='3'>"+data[i].reply_content+"</td></tr>";
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