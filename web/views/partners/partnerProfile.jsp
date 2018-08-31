<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="ein.mono.partners.model.vo.*"%>
<%@ page import="ein.mono.member.model.vo.*"%>
<%@ include file="/views/common/header.jsp" %>
<%@ include file="/views/common/footer.jsp" %>
<%
	PartnersVo ptnProfile = (PartnersVo)request.getAttribute("ptnProfile");
	boolean isMember = (null != member) && ('B' == member.getMemberCode().charAt(0));
	boolean isMe = false;
	boolean isNotAdmin = (null == member) || ((null != member) && ('A' != member.getMemberCode().charAt(0)));
	//request.setAttribute("partnerCode", ptnProfile.getPartnerCode());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>업체 정보</title>
<link rel="stylesheet" href="/mono/css/partnerProfile.css" type="text/css"/>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js">
	
</script>
</head>
<body>
<div class="ptnProfileArea">
<%if(null != ptnProfile){
	request.setAttribute("partnerCode", ptnProfile.getPartnerCode());
	isMe = (null != member) && (member.getMemberCode().equals(ptnProfile.getPartnerCode()));
	String[] ptnPhotoList = null != ptnProfile.getPtnPhotos() ? ptnProfile.getPtnPhotos().split("§") : new String[0]; // 사진 파싱
	String[] ptnUpdates = null != ptnProfile.getPtnUpdates() ? ptnProfile.getPtnUpdates().split("♠") : new String[0]; // 추가 정보
	String[] ptnContacts = null != ptnProfile.getPtnContacts() ?  ptnProfile.getPtnContacts().split("♠") : new String[0];
%>	
<div class="profileArea">
	<table class="ptnInfo">
		<tr>
			<td colspan="2"><div class="ptnCategory">업체 정보 : <%=ptnProfile.getMemberName()%></div></td>
		</tr>
		<tr>
			<td>
				<table class="ptnDefaultInfo">
					<tr><td><img class="ptnLogo" alt="none" src="/mono/upload/partners/<%=ptnProfile.getPartnerLogo()%>"></td></tr>
					<%if(isNotAdmin)%><tr><td><div class="ptnBtn">★<%=Math.floor(ptnProfile.getMetascore()*10)/10%></div></td></tr>
					<%if((null == member) || isMember){%>
					<tr><td><div class="ptnBtn" id="favBtn"></div></td></tr>
					<tr><td><div class="ptnBtn" id="reqBtn">견적 신청</div></td></tr>
					<%}%>
					<%if(isMe){%>
					<tr><td><div class="ptnBtn" id="updatePtnBtn" onclick="updatePtnInfo();">정보 수정</div></td></tr>
					<%}%>	
				</table>
			</td>
			<td>
				<div class="ptnPhotoList">
					<table>
						<tr>
							<td colspan="6"><img id="mainPhoto" alt="none"></td>
						</tr>
						<tr>
							<td><div id="prevImgBtn"> < </div></td>
							<%for(int index = 0; index < 4; index++){%>
								<td>
								<%if(ptnPhotoList.length > index){%>
								<div class="photoThumnail"><img  alt="none" name="<%=index%>" src="/mono/upload/partners/<%=ptnPhotoList[index]%>"/></div>
								<%}else{%>
								<div class="photoBlank"></div>
								<%}%>
								</td>
							<%}%>
							<td><div id="nextImgBtn"> > </div></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	<table class="detailInfoTab">
		<tr>
			<td><div class="ptnInfoTab" id="ptnDetail">상세정보</div></td>
			<td><div class="ptnInfoTab" id="ptnContent">소개</div></td>
			<td><div class="ptnInfoTab" id="ptnQnA" onclick="qnaPage();">Q&A게시판</div></td>
		</tr>
	</table>
	<div id="detailInfoArea">	
		<table>
			<tr>
				<td class="detailInfoColTitle">업체명</td>
				<td class="detailInfoColContent"><%=ptnProfile.getMemberName()%></td>
			</tr>
			<tr>
				<td class="detailInfoColTitle">시공 지역</td>
				<td class="detailInfoColContent"><%=ptnProfile.getPartnerLocation()%></td>
			</tr>
			<tr>
				<td class="detailInfoColTitle">스타일</td>
				<td class="detailInfoColContent"><%=ptnProfile.getPartnerStyles()%></td>
			</tr>
			<tr>
				<td class="detailInfoColTitle">상담시간</td>
				<td class="detailInfoColContent">평일 <%=ptnProfile.getWeekdaysStart()%> ~ <%=ptnProfile.getWeekdaysEnd()%><br>
					주말 <%=ptnProfile.getWeekendStart()%> ~ <%=ptnProfile.getWeekendEnd()%><br>
				</td>
			</tr>
			<tr>
				<td class="detailInfoColTitle">문의</td>
				<td class="detailInfoColContent">
				<% 
					if(0< ptnContacts.length){
						for(String contact : ptnContacts){
							if(contact.split("♤").length <= 1) {
				%>
							연락처 정보가 존재하지 않습니다.
				<%break;
					}
				%>
					<%=contact.split("♤")[0]%> : <%=contact.split("♤")[1]%><br>
				<%		}
					}else{
				%>
					연락처 정보가 존재하지 않습니다.
				<%	}%>
				</td>
			</tr>
			<%
				if(0 < ptnUpdates.length){
					for(String updateInfo : ptnUpdates){
						if(updateInfo.split("♤").length <= 1) break;
			%>
			<tr>
				<td class="detailInfoColTitle"><%=updateInfo.split("♤")[0]%></td>
				<td class="detailInfoColContent"><%=updateInfo.split("♤")[1]%></td>
			</tr>	
			<%
					}
				}
			%>
		</table>
	</div>
	<div id="introArea">
		<%=ptnProfile.getPartnerIntro().replaceAll("\n", "<br>")%>
	</div>
</div>
</div>
<script>
	var startIndex = 0; // 첫 번째 썸네일
	var photoIndex = 0; // 현재 보여주고 있는 사진 칸
	var photoList = {
	<%for(int index = 0; index < ptnPhotoList.length; index++){%>		
		<%=index%> : "<%=ptnPhotoList[index]%>"
		<%if(index < ptnPhotoList.length - 1){%>
		,
		<%}%>
	<%}%>
	}; // 사진 객체

	function qnaPage(){
		location.href = "/mono/selectQnAList.do?partnerCode=<%=ptnProfile.getPartnerCode()%>";
	}
	
	function selectFavCount(content){		
		$.ajax({
			 url : "/mono/selectFavCount.do"
			 , type : "post"
			 , data : {partnerCode : '<%=ptnProfile.getPartnerCode()%>'}
			 , success : function(data){
				//console.log(data);
				var value = "";
				if(data > -1){
					value = content + data
				} else{
					value = "업로드 실패!"
				}		
				$("#favBtn").text(value);
			 }
			 , error : function(e){
				//console.log(e);
				 $("#favBtn").text("업로드 실패!");
			 }
			 , complete : function(data){
				//console.log(data)
			 }
		 });
	}
	
	function checkFavPtn(){		
		$.ajax({
			 url : "/mono/checkFavPtn.do"
			 , type : "post"
			 , data : {memberCode : '<%if(isMember){%><%=member.getMemberCode()%><%}%>', partnerCode : '<%=ptnProfile.getPartnerCode()%>'}
			 , success : function(data){
			 	var content = "즐겨찾기 ";
				if(data == "true"){
					content += "♥";
				}else{
					content += "♡";
				}			
				selectFavCount(content);
			 }
			 , error : function(e){
				 $("#favBtn").text("업로드 실패!");
			 }
			 , complete : function(data){

			 }
		 });
	}
	
	function updatePtnInfo(){
		<%if(isMe){%>
			location.href = "/mono/selectPartner.do?partnerCode=<%=ptnProfile.getPartnerCode()%>&isUpdate=1";
		<%}%>
	}
	
	$(function(){	
		<%if(isMember){ // 로그인 중이고 업체 아니면 검사%>
			checkFavPtn();
		<%}else{%>
			selectFavCount("즐겨찾기 ♡");
		<%}%>
		
		$(".photoThumnail img").click(function(){
			$("#mainPhoto").attr("src", $(this).attr("src"));
			$(".photoThumnail img").parent().removeClass("showing");
			$(this).parent().addClass("showing");
			photoIndex = parseInt($(this).attr("name"));
		});
		
		$(".photoThumnail img:first").click();
		
		$("#favBtn").click(function(){
			if(<%=isMember%>){
				if($("#favBtn").text().includes("♥")){ // 즐겨찾기가 이미 등록되어 있다면
					if(confirm("즐겨찾기를 해제하시겠습니까?")){
						$.ajax({
							 url : "/mono/deleteFB.do"
							 , type : "get"
							 , data : {memberCode : '<%if(isMember){%><%=member.getMemberCode()%><%}%>', partnerCode : '<%=ptnProfile.getPartnerCode()%>', fbType : '즐겨찾기'}
							 , success : function(data){
								alert("즐겨찾기를 해제했습니다.");		
							 }
							 , error : function(e){
								 alert("예기치 못한 오류!");
							 }
							 , complete : function(data){
								 checkFavPtn();
							 }
						 });
					}
				} else{ // 등록이 안 되어 있다면
					if(confirm("<%=ptnProfile.getMemberName()%> 업체를 즐겨찾기하시겠습니까?")){
						$.ajax({
							 url : "/mono/insertFB.do"
							 , type : "post"
							 , data : {memberCode : '<%if(isMember){%><%=member.getMemberCode()%><%}%>', partnerCode : '<%=ptnProfile.getPartnerCode()%>', fbType : '즐겨찾기'}
							 , success : function(data){
								alert("즐겨찾기에 등록했습니다.");	
							 }
							 , error : function(e){
								alert("예기치 못한 오류!");
							 }
							 , complete : function(data){
								checkFavPtn();
							 }
						 });
					}
				}				
			}else{
				alert("즐겨찾기로 등록하기 위해서는 로그인을 먼저 하십시오.");
				loginPage();
			}
		});
		
		$("#reqBtn").click(function(){
			if(<%=isMember%>){
				location.href="/mono/views/request/requestForm.jsp?page=request&ptnCode=<%=ptnProfile.getPartnerCode()%>";
			}else{
				alert("견적을 신청하기 위해서는 로그인을 먼저 하십시오.");
				loginPage();
			}
		});
		
		
		
		$("#prevImgBtn").click(function(){
			var imgSelector = ".photoThumnail img:eq(";
			var photoTotal = <%=ptnPhotoList.length%>;
			//console.log(photoIndex);
			if(photoIndex == 0 && 4 < photoTotal && 0 < startIndex){
				startIndex--;
				for(var index = startIndex; index < 4 + startIndex; index++){
					$(imgSelector+(index - startIndex).toString()+")").attr("src", "/mono/upload/partners/"+photoList[index.toString()]);
				}
			}
			
			if(photoIndex > 1){
				photoIndex--;		
			} else{
				photoIndex = 0;
			}
			
			imgSelector += photoIndex.toString() + ")";
			$(imgSelector).click();
		});
		
		$("#nextImgBtn").click(function(){
			var imgSelector = ".photoThumnail img:eq(";
			var photoTotal = <%=ptnPhotoList.length%>;
			//console.log(photoIndex);
			if(photoIndex == 3 && 4 < photoTotal && photoTotal - 4 > startIndex){
				startIndex++;
				for(var index = startIndex; index < 4 + startIndex; index++){
					$(imgSelector+(index - startIndex).toString()+")").attr("src", "/mono/upload/partners/"+photoList[index.toString()]);
				}
			}
			
			if(photoIndex < 2){
				photoIndex++;
			} else{
				photoIndex = 3;
			}
			
			imgSelector += photoIndex.toString() + ")";
			$(imgSelector).click();
		});
		
		$("#ptnDetail").click(function(){
			$("#detailInfoArea").show();
			$("#introArea").hide();
		});

		$("#ptnContent").click(function(){
			$("#introArea").show();
			$("#detailInfoArea").hide();
		});
		
		$("#ptnDetail").click();
	});
</script>
<%}%>
</body>
</html>