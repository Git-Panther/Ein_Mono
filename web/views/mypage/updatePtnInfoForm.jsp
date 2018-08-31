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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>업체 정보 수정</title>
<link rel="stylesheet" href="/mono/css/updatePtnInfoForm.css?ver=2" type="text/css"/>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<div class="ptnProfileArea">
<div class="profileArea">
<form method="post" action="/mono/updatePartner.do" onsubmit = "return validate();" enctype="multipart/form-data">
<%if(null != ptnProfile){
	request.setAttribute("partnerCode", ptnProfile.getPartnerCode());
	isMe = (null != member) && (member.getMemberCode().equals(ptnProfile.getPartnerCode()));
	
	String[] ptnPhotoList = null != ptnProfile.getPtnPhotos() ? ptnProfile.getPtnPhotos().split("§") : new String[0]; // 사진 파싱
	String[] ptnUpdates = null != ptnProfile.getPtnUpdates() ? ptnProfile.getPtnUpdates().split("♠") : new String[0]; // 추가 정보
	String[] ptnContacts = null != ptnProfile.getPtnContacts() ?  ptnProfile.getPtnContacts().split("♠") : new String[0];
	
	String[] ptnLoc = ptnProfile.getPartnerLocation().split(", ");
	String[] ptnStyle = ptnProfile.getPartnerStyles().split(", ");
%>	
	<input type="hidden" name="partnerCode" value="<%=ptnProfile.getPartnerCode()%>" />
	<input type="hidden" name="oldLogo" value="<%=ptnProfile.getPartnerLogo()%>" />
	<input type="hidden" name="oldConstPhoto" <%if(null != ptnProfile.getPtnPhotos()){%>value="<%=ptnProfile.getPtnPhotos()%>"<%}%> />
	<input type="file" name="newLogo" style="display:none;" />
	<table class="ptnInfo">
		<tr>
			<td colspan="2"><div class="ptnCategory">업체 정보 수정</div></td>
		</tr>
		<tr>
			<td>
				<table class="ptnDefaultInfo">
					<tr><td><img class="ptnLogo" alt="none" src="/mono/upload/partners/<%=ptnProfile.getPartnerLogo()%>"></td></tr>
					<%if(isMe){%>
					<tr><td><div class="ptnBtn" id="updateLogoBtn">로고 변경</div></td></tr>
					<tr><td><div class="ptnBtn" id="submitBtn">변경 내용 저장</div></td></tr>
					<%}%>	
				</table>
			</td>
		</tr>
	</table>
	<table class="detailInfoTab">
		<tr>
			<td><div class="ptnInfoTab" id="ptnDetail">상세정보</div></td>
			<td><div class="ptnInfoTab" id="ptnContent">소개</div></td>
			<td><div class="ptnInfoTab" id="ptnPhoto">시공사진</div></td>
		</tr>
	</table>
	<div id="detailInfoArea" class="contents">	
		<table>
			<tr>
				<td class="detailInfoColTitle">업체명</td>
				<td class="detailInfoColContent"><%=ptnProfile.getMemberName()%></td>
			</tr>
			<tr>
				<td class="detailInfoColTitle">시공 지역 <div class="addBtn" id="addLocBtn">추가</div></td>
				<td class="detailInfoColContent">
					<%for(int i = 0; i < ptnLoc.length; i++){%>
					<div class="infoRow">
						<input type="text" class="inputString" placeholder="시공 지역을 입력하세요" name="ptnLocation" value="<%=ptnLoc[i]%>"/>
						<div class="<%=i != 0 ? "removeBtn" : "removeBlank"%>"><%=i != 0 ? "삭제" : ""%></div>
					</div>
					<%}%>
				</td>
			</tr>
			<tr>
				<td class="detailInfoColTitle">스타일 <div class="addBtn" id="addStyleBtn">추가</div></td>
				<td class="detailInfoColContent">
					<%for(int i = 0; i < ptnStyle.length; i++){%>
					<div class="infoRow">
						<input type="text" class="inputString" placeholder="스타일을 입력하세요" name="ptnStyle" value="<%=ptnStyle[i]%>"/>
						<div class="<%=i != 0 ? "removeBtn" : "removeBlank"%>"><%=i != 0 ? "삭제" : ""%></div>
					</div>
					<%}%>	
				</td>
			</tr>
			<tr>
				<td class="detailInfoColTitle">상담시간</td>
				<td class="detailInfoColContent">
					평일 <input type="time" name="weekdaysStart" value="<%=null == ptnProfile.getWeekdaysStart() ? 0 : ptnProfile.getWeekdaysStart()%>">
					~
					<input type="time" name="weekdaysEnd" value="<%=null == ptnProfile.getWeekdaysEnd() ? 0 : ptnProfile.getWeekdaysEnd()%>"><br>
					주말 <input type="time" name="weekendStart" value="<%=null == ptnProfile.getWeekendStart() ? 0 : ptnProfile.getWeekendStart()%>">
					~ <input type="time" name="weekendEnd" value="<%=null == ptnProfile.getWeekendEnd() ? 0 : ptnProfile.getWeekendEnd()%>">
				</td>
			</tr>
			<tr>
				<td class="detailInfoColTitle">문의 <div class="addBtn" id="addContactBtn">추가</div></td>
				<td class="detailInfoColContent">
				<% 
					if(0 < ptnContacts.length){
						for(int i = 0; i < ptnContacts.length; i++){
							if(ptnContacts[i].split("♤").length <= 1){
				%>
				<input type="text" class="inputString" name="contactType" placeholder="연락처 종류"/> : <input type="text" class="inputString" name="contactContent" placeholder="연락처 정보"/>
				<%				
								break;
							}
				%>
				<div class="infoRow">
					<input type="text" class="inputString" name="contactType" placeholder="연락처 종류" value="<%=ptnContacts[i].split("♤")[0]%>"/>
					 : 
					<input type="text" class="inputString" name="contactContent" placeholder="연락처 정보" value="<%=ptnContacts[i].split("♤")[1]%>"/>
					<div class="<%=i != 0 ? "removeBtn" : "removeBlank"%>"><%=i != 0 ? "삭제" : ""%></div>
				</div>
				<%		}
					}else{
				%>
					<input type="text" class="inputString" name="contactType" placeholder="연락처 종류"/> : <input type="text" class="inputString" name="contactContent" placeholder="연락처 정보"/>
				<%	}%>	
				</td>
			</tr>
			<%
				if(0 < ptnUpdates.length){
					for(int i = 0; i < ptnUpdates.length; i++){
						if(ptnUpdates[i].split("♤").length <= 1) break; // 스플릿 했을 때 1 이하가 될 수 없음
			%>
			<tr class="updateRow">
				<td class="detailInfoColTitle">
					<input type="text" name="updateTitle" value="<%=ptnUpdates[i].split("♤")[0]%>" placeholder="제목을 입력하세요"/>
				</td>
				<td class="detailInfoColContent"><input type="text" name="updateContent" value="<%=ptnUpdates[i].split("♤")[1]%>" placeholder="내용을 입력하세요"/>
				<div class="removeRowBtn">삭제</div>
				</td>
			</tr>
			<%
					}
				}
			%>
		</table>
		<div class="addBtn" id="addUpdateInfoBtn">새 정보 추가</div>
	</div>
	<div id="introArea" class="contents">
		<textarea name="intro" rows="100" cols="130" placeholder="소개글을 입력하세요."><%=ptnProfile.getPartnerIntro()%></textarea>
	</div>
	<div id="ptnPhotoArea" class="contents">
		<table>
			<tr>
				<td colspan="2">맨 앞의 사진이 표시됩니다.</td>
			</tr>
	<%for(int index = 0; index < 8; index++){
		if(0 == index%2){
	%>
		<tr>
		<%}%>
			<td>
				<div class="photoWrapper">
					<div class="<%=ptnPhotoList.length > index ? "photoArea" : "photoBlank"%>">
					<%if(ptnPhotoList.length > index){%>
					<img alt="none" src="/mono/upload/partners/<%=ptnPhotoList[index]%>">
					<%}else{%>
						사진 추가
					<%}%>
					</div>
					<input type="file" class="<%=ptnPhotoList.length > index ? "updatePhotoBtn" : "addPhotoBtn"%>" name="constPhoto<%=index%>"/>
					<div class="<%=ptnPhotoList.length > index ? "deletePhotoBtn" : "removePhotoBlind"%>"><%=ptnPhotoList.length > index ? "X" : ""%></div>
					<input type="hidden" name="deleteCheck" value="false"><!-- 이름이 같으면 values로 뽑아서 순서대로 가져온다. 아마? -->
				</div>
			</td>
		<%if(1 == index%2){%>
		</tr>		
		<%}%>
	<%
	}
	%>
		</table>
	</div>
</form>
</div>
</div>
<script>	
	var constPhoto = {
		<%for(int i = 0; i < 8; i++){%>
			constPhoto<%=i%> : <%if(ptnPhotoList.length > i){%>'<%=ptnPhotoList[i]%>'<%}else{%>null<%}%>
			<%if(i != 7){%>,<%}%>
		<%}%>
	};

	console.log(constPhoto);
	console.log($("input[name^=constPhoto]").eq(0).val());
	
	function validate(){
		var $ptnLocation = $("input[name=ptnLocation]");
		var $ptnStyle = $("input[name=ptnStyle]");
		var $time = $("input[name^=week]");
		var $contact = $("input[name^=contact]");
		var $update = $("input[name^=update]");
		var $intro = $("textarea[name=intro]");	
		
		var notAllowedSpecial = /.*[^(,/\-.:~\s\(\)#%+*&^=|@!'"\w가-힣ㄱ-ㅎㅏ-ㅣ)].*/;	
		var notKorEngNumSpace = /.*[^(A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ\s)].*/;
		
		var result = true;
		
		if($ptnLocation.length == 1 && "" == $ptnLocation.eq(0).val()){
			alert("지역은 최소 1곳 이상은 있어야 합니다.");
			result = false;
			return false;
		}else{
			$ptnLocation.each(function(){
				var val = $(this).val();
				if("" == val){
					alert("지역 정보가 비어있습니다.");
					result = false;
					return false;
				}
				
				if(notKorEngNumSpace.test(val)){
					alert("지역에는 공백을 제외한 특수 문자가 들어갈 수 없습니다.");
					result = false;
					return false;
				}
			});
		}
		
		if($ptnStyle.length == 1 && "" == $ptnStyle.eq(0).val()){
			alert("스타일은 최소 1개 이상은 있어야 합니다.");
			return false;
		}else{
			$ptnStyle.each(function(){
				var val = $(this).val();
				if("" == val){
					alert("스타일 정보가 비어있습니다.");
					result = false;
					return false;
				}
				
				if(notKorEngNumSpace.test(val)){
					alert("스타일에는 공백을 제외한 특수 문자가 들어갈 수 없습니다.");
					result = false;
					return false;
				}
			});
		}
		
		$time.each(function(){
			var val = $(this).val();
			if("" == val){
				alert("시간을 비워놓을 수는 없습니다.");
				result = false;
				return false;
			}
		});
		
		if($time.eq(0).val() > $time.eq(1).val() || $time.eq(2).val() > $time.eq(3).val()){
			alert("시작 시간이 종료 시간보다 뒤에 있을 수는 없습니다.");
			result = false;
			return false;
		}
		
		if($contact.length == 2 && ("" == $contact.eq(0).val() || "" == $contact.eq(1).val()) ){
			alert("연락처는 최소 1개 필요합니다.");
			result = false;
			return false;
		}else{
			$contact.each(function(){
				var val = $(this).val();
				if("" == val){
					alert("연락처가 비어있습니다. 다시 확인해주세요.");
					result = false;
					return false;
				}
				
				if(notAllowedSpecial.test(val)){
					alert("사용할 수 없는 특수 문자가 포함되어 있습니다. 다시 확인해주세요.");
					result = false;
					return false;
				}
			});
		}
		
		if($update.length > 0){
			$update.each(function(){
				var val = $(this).val();
				if("" == val){
					alert("추가 정보 공간은 비워놓을 수 없습니다.");
					result = false;
					return false;
				}
				
				if(notAllowedSpecial.test(val)){
					alert("사용할 수 없는 특수 문자가 포함되어 있습니다. 다시 확인해주세요.");
					result = false;
					return false;
				}
			});
		}
		
		if("" == $intro.val()){
			alert("소개글을 비워놓을 수 없습니다.");
			result = false;
			return false;
		}
		
		if(result) return confirm("정말로 수정하시겠습니까?");
		else return false;
	}
	
	function addRemoveEvent(){
		$(".removeBtn").click(function(){
			$(this).parent().remove();
		});
	}
	
	function printLogo(e){
		var files = e.target.files;
		var filesArr = Array.prototype.slice.call(files);
		
		filesArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("이미지 파일만 선택이 가능합니다.");
				return;
			}
			
			sel_file = f;
			
			var reader = new FileReader();
			reader.onload = function(e){
				$(".ptnLogo").attr("src", e.target.result);
			}
			reader.readAsDataURL(f);
		});
	}
	
	function addPhoto(e){
		var files = e.target.files;
		var filesArr = Array.prototype.slice.call(files);
		var $this = $(this);
		var $parent = $this.parent(); // photoWrapper
		var $td = $parent.parent();
		var $photoBlank = $parent.find(".photoBlank");
		var $removeBtn = $parent.find(".removePhotoBlind");
		var $deleteCheck = $parent.find("input[name=deleteCheck]");
		var img = $("<img>");
		var plot = true;
		//var $cloneWrapper = $parent.clone();
		var $table = $("#ptnPhotoArea table");
		
		filesArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("이미지 파일만 선택이 가능합니다.");
				plot = false;
				$this.val("");
				return;
			}
			
			sel_file = f;
			
			var reader = new FileReader();
			reader.onload = function(e){
				if(plot){
					img.attr({"alt":"none", "src":e.target.result});
					$photoBlank.html(img);
					$photoBlank.removeClass("photoBlank");
					$photoBlank.addClass("photoArea");
					$removeBtn.removeClass("removePhotoBlind");
					$removeBtn.addClass("deletePhotoBtn");
					$removeBtn.text("X");
					$this.removeClass("addPhotoBtn");
					$this.addClass("updatePhotoBtn");
					$deleteCheck.val(false);
					
					$removeBtn.off();
					$removeBtn.click(deletePhoto);
					$this.off();
					$this.on("change", updatePhoto);
					$photoBlank.off();
					$photoBlank.click(function(){$this.click();});
				}	
			}
			reader.readAsDataURL(f);
		});	
		
		console.log($this.val());
	}
	
	function deletePhoto(){
		var $this = $(this); // 삭제 버튼
		var $parent = $this.parent();
		var $td = $parent.parent(); // photoWrapper.parent = td
		var $photoArea = $parent.find(".photoArea");
		var $inputBtn = $parent.find(".updatePhotoBtn"); // input type=image
		var $table = $("#ptnPhotoArea table");
		var $img = $photoArea.find("img");
		//var index = $inputBtn.attr("name");
		var $deleteCheck = $parent.find("input[name=deleteCheck]");
		
		$img.remove();
		$inputBtn.val(null);
		$inputBtn.removeClass("updatePhotoBtn");
		$inputBtn.addClass("addPhotoBtn");
		$photoArea.removeClass("photoArea");
		$photoArea.addClass("photoBlank");
		$photoArea.append("사진 추가");
		$this.removeClass("deletePhotoBtn");
		$this.addClass("removePhotoBlind");
		$this.text("");
		$deleteCheck.val(true);
		
		$this.off();
		$inputBtn.off();
		$inputBtn.on("change", addPhoto);
		$photoArea.off();
		$photoArea.click(function(){$inputBtn.click();});
		
		console.log($inputBtn.val());
	}
	
	function updatePhoto(e){
		var files = e.target.files;
		var filesArr = Array.prototype.slice.call(files);
		var $this = $(this); // 버튼
		var $parent = $this.parent(); // photoWrapper
		var $photoArea = $parent.find(".photoArea");
		var $img = $photoArea.find("img");
		var plot = true;

		filesArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("이미지 파일만 선택이 가능합니다.");
				plot = false;
				return;
			}
			
			sel_file = f;
			
			var reader = new FileReader();
			reader.onload = function(e){
				if(plot){
					$img.attr({"src":e.target.result});		
				}
			}
			reader.readAsDataURL(f);
		});
		
		console.log($this.val());
	}
	
	function addPhotoBtnEvent(){
		$(".photoBlank").click(function(){
			$(this).parent().children(".addPhotoBtn").eq(0).click();
		});
	}
	
	function updatePhotoBtnEvent(){
		$(".photoArea").click(function(){
			$(this).parent().children(".updatePhotoBtn").eq(0).click();	
		});
	}
	
	function addRemovePhotoEvent(){
		$(".deletePhotoBtn").click(deletePhoto);
	}
	
	function addPhotoEvent(){
		$(".addPhotoBtn").on("change", addPhoto);
	}
	
	function updatePhotoEvent(){
		$(".updatePhotoBtn").on("change", updatePhoto);
	}
	
	function updateEvent(){
		addRemoveEvent();
		addPhotoBtnEvent();
		addPhotoEvent();
		addRemovePhotoEvent();
		updatePhotoBtnEvent();
		updatePhotoEvent();
	}
	
	$(function(){
		<%if(null == member){%>
			alert("로그인이 필요한 서비스입니다!");
			location.href="/mono/views/member/loginMember.jsp?recentlyPath="+location.href;
		<%}%>
		
		updateEvent();
		
		$("input[name=newLogo]").on("change", printLogo);
		
		$("#ptnDetail").click(function(){
			$(".contents").hide();
			$("#detailInfoArea").show();		
		});

		$("#ptnContent").click(function(){
			$(".contents").hide();
			$("#introArea").show();
		});
		
		$("#ptnPhoto").click(function(){
			$(".contents").hide();
			$("#ptnPhotoArea").show();
		});
		
		$("#updateLogoBtn").click(function(){
			var $partnerLogo = $("input[name=newLogo]");
			$partnerLogo.click();
		});
		
		$("#submitBtn").click(function(){
			$("form").submit();
		});
		
		$("#addLocBtn").click(function(){
			var div = $("<div>");
			div.addClass("infoRow")
			.html("<input type='text' class='inputString' placeholder='시공 지역을 입력하세요' name='ptnLocation'/> <div class='removeBtn'>삭제</div>");		
			$(".detailInfoColContent").eq(1).append(div);
			addRemoveEvent();
		});
		
		$("#addStyleBtn").click(function(){
			var div = $("<div>");
			div.addClass("infoRow")
			.html("<input type='text' class='inputString' placeholder='스타일을 입력하세요' name='ptnStyle'/> <div class='removeBtn'>삭제</div>");
			$(".detailInfoColContent").eq(2).append(div);
			addRemoveEvent();
		});
			
		$("#addContactBtn").click(function(){
			var div = $("<div>");
			div.addClass("infoRow")
			.html("<input type='text' class='inputString' name='contactType' placeholder='연락처 종류'/> : <input type='text' class='inputString' name='contactContent' placeholder='연락처 정보'/> <div class='removeBtn'>삭제</div>");
			$(".detailInfoColContent").eq(4).append(div);
			addRemoveEvent();		
		});
		
		$("#addUpdateInfoBtn").click(function(){
			var tr = $("<tr>");
			tr.addClass("updateRow");			
			var td1 = $("<td>");
			td1.addClass("detailInfoColTitle");
			td1.html("<input type='text' name='updateTitle' placeholder='제목을 입력하세요'/>");
			tr.append(td1);
			var td2 = $("<td>");
			td2.addClass("detailInfoColContent");
			td2.html("<input type='text' name='updateContent' placeholder='내용을 입력하세요'/> <div class='removeRowBtn'>삭제</div>");
			tr.append(td2);
			$("#detailInfoArea table").append(tr);
			$(".removeRowBtn").click(function(){
				$(this).parent().parent().remove();
			});
		});
		
		$(".removeRowBtn").click(function(){
			$(this).parent().parent().remove();
		});
		
		$("#ptnDetail").click();
	});
</script>
<%}%>
</body>
</html>