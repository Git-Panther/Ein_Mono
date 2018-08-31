package ein.mono.profile.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import ein.mono.common.MyRenamePolicy;
import ein.mono.partners.model.service.PartnersService;
import ein.mono.partners.model.vo.PartnersVo;

/**
 * Servlet implementation class ProfilUpdateServlet
 */
@WebServlet("/updatePartner.do")
public class UpdatePtnProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePtnProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String url = null;
		int maxSize = 1024 * 1024 * 10;
		//2. 파일 전송 시 enctype 정상적으로 설정 되었는지 확인
		if(!ServletFileUpload.isMultipartContent(request)){
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "전송 데이터의 타입을 확인하십시오!!");
			request.getRequestDispatcher(url).forward(request, response);;
		}
		//3. 파일 저장 경로 설정
		String root = request.getServletContext().getRealPath("/");
		String path = root + "upload/partners/";
		//4. request -> multipartrequest
		//filerenamePolicy -> FileRenamePolicy 상속 -> rename() override
		
		MultipartRequest mRequest = new MultipartRequest(request,
				path, maxSize, "UTF-8", new MyRenamePolicy());
		
		PartnersVo ptnProfile = new PartnersVo();
		ptnProfile.setPartnerCode(mRequest.getParameter("partnerCode"));
		ptnProfile.setPartnerLocation(String.join(", ", mRequest.getParameterValues("ptnLocation")));
		ptnProfile.setPartnerStyles(String.join(", ", mRequest.getParameterValues("ptnStyle")));
		ptnProfile.setWeekdaysStart(mRequest.getParameter("weekdaysStart"));
		ptnProfile.setWeekdaysEnd(mRequest.getParameter("weekdaysEnd"));
		ptnProfile.setWeekendStart(mRequest.getParameter("weekendStart"));
		ptnProfile.setWeekendEnd(mRequest.getParameter("weekendEnd"));
		
		String[] contactTypes = mRequest.getParameterValues("contactType");
		String[] contactContents = mRequest.getParameterValues("contactContent");
		ArrayList<String> contactSet = new ArrayList<String>(); // 세트로 묶음

		for(int i = 0; i < contactTypes.length; i++) {
			contactSet.add(String.join("♤", contactTypes[i], contactContents[i]));
		}
		ptnProfile.setPtnContacts(String.join("♠", contactSet));
		
		String[] updateTitles = mRequest.getParameterValues("updateTitle");
		String[] updateContents = mRequest.getParameterValues("updateContent");
		ArrayList<String> updateSet = new ArrayList<String>(); // 세트로 묶음

		if(null != updateTitles && null != updateContents) {
			for(int i = 0; i < updateTitles.length; i++) {
				updateSet.add(String.join("♤", updateTitles[i], updateContents[i]));
			}
			ptnProfile.setPtnUpdates(String.join("♠", updateSet));
		} else {
			ptnProfile.setPtnUpdates("");
		}
		ptnProfile.setPartnerIntro(mRequest.getParameter("intro"));
	
		String newLogo = "";
		String oldLogo = mRequest.getParameter("oldLogo");
		
		// 로고 처리
		//boolean logoFlag = false;
		Enumeration<String> photoEnum = (Enumeration<String>)mRequest.getFileNames();
		if(photoEnum.hasMoreElements()) { // 처음 로고로 돌림(logo)
			photoEnum.nextElement();
			newLogo = mRequest.getFilesystemName("newLogo");
			if(null != newLogo) {
				ptnProfile.setPartnerLogo(newLogo);			
				if(null != oldLogo) {
					File removedLogo = new File(path+oldLogo);
					if(removedLogo.exists()) {
						removedLogo.delete();
					}
				}
				//logoFlag = true;
			}else {
				 ptnProfile.setPartnerLogo(oldLogo);
			}	
		} 
		
		// 시공 사진들 처리(0~7)
		// String constPath = root + "upload/const_photo/";
		// mRequest = new MultipartRequest(request, constPath, maxSize, "UTF-8", new MyRenamePolicy());
		
		// 1. 시공 사진 정보 준비
		ArrayList<String> newConstPhotos = new ArrayList<String>();
		String oldConstPhotoString = mRequest.getParameter("oldConstPhoto");
		//System.out.println(oldConstPhotoString);
		String[] oldConstPhotoArray = (null != oldConstPhotoString ? oldConstPhotoString.split("§") : null);
		//photoEnum = (Enumeration<String>)mRequest.getFileNames();
		
		// 2. 새 시공 사진 처리
		//int photoIndex = 0;
		String newPhotoTemp = "";
		//if(logoFlag && photoEnum.hasMoreElements()) photoEnum.nextElement(); // 로고가 이미 등록되어 있다면
		for(int i = 0; i < 8; i++) {
			photoEnum.nextElement();
			newPhotoTemp = mRequest.getFilesystemName("constPhoto" + i);
			if(null != newPhotoTemp && !("".equals(newPhotoTemp))) {
				newConstPhotos.add(newPhotoTemp);
				//System.out.println("added : " + newPhotoTemp);
			}
			else {
				newConstPhotos.add("none");
				//System.out.println("added none");
			}
		}
		
		/*
		while(photoEnum.hasMoreElements()) { // 로고에 이어서 돌린다. 0 ~ 7까지.
			photoEnum.nextElement();
			newPhotoTemp = mRequest.getFilesystemName("constPhoto" + photoIndex++);
			if(null != newPhotoTemp && !("".equals(newPhotoTemp)) ) newConstPhotos.add(newPhotoTemp);
			else newConstPhotos.add("none");
		}
		*/
		
		// 3. 구 시공 사진 처리(길이 맞추기)
		ArrayList<String> oldConstPhotos = new ArrayList<String>();
		for(int index = 0; index < 8; index++) {
			if(null != oldConstPhotoArray && index < oldConstPhotoArray.length) {
				if(null != oldConstPhotoArray[index] && !("".equals(oldConstPhotoArray[index]))) {
					oldConstPhotos.add(oldConstPhotoArray[index]);
					//System.out.println("added : " + oldConstPhotoArray[index]);
				}else {
					oldConstPhotos.add("none");
					//System.out.println("added : none");
				}
			} else {
				oldConstPhotos.add("none");
				//System.out.println("added : none");
			}		
		}
		
		// 4. mergedPhotos 준비
		ArrayList<String> mergedPhotos = new ArrayList<String>(); // 정렬된 리스트
		boolean isOldNone, isNewNone;
		File removedPhoto = null; // 지울 사진 파일
		String[] delectChecks = mRequest.getParameterValues("deleteCheck"); // 총 8개이며, 사진의 순서와 일치한다.
		
		// 5. mergedPhotos 넣어주기
		for(int index = 0; index < 8; index++) {
			isOldNone = "none".equals(oldConstPhotos.get(index));
			isNewNone = "none".equals(newConstPhotos.get(index));
			
			//System.out.println(oldConstPhotos.get(index) + ", " + newConstPhotos.get(index) + ", " + delectChecks[index]);
			
			if(isOldNone && isNewNone) { // 둘 다 none
				// 처리 없음
			} else if(!isOldNone && isNewNone) { // 올드는 있고 새거는 없을 때
				if(Boolean.parseBoolean(delectChecks[index])) { // 삭제한 이력이 있다면 기존 것을 삭제(즉, 사진을 비움)
					removedPhoto = new File(path + oldConstPhotos.get(index));
					if(removedPhoto.exists()) { // 구 사진 삭제
						removedPhoto.delete();
					}
				} else { // 삭제 이력이 없으므로 그대로 유지
					mergedPhotos.add(oldConstPhotos.get(index));
				}			
			} else if(isOldNone && !isNewNone) { // 올드는 없고 새거는 있을 때
				mergedPhotos.add(newConstPhotos.get(index));
			} else { // 올드도 있고 새거도 있으면 변경되었단 뜻이므로 지난 것은 삭제
				mergedPhotos.add(newConstPhotos.get(index));
				removedPhoto = new File(path + oldConstPhotos.get(index)); // 구 사진 삭제
				if(removedPhoto.exists()) { // 구 사진 삭제
					removedPhoto.delete();
				}
			}
		}
		
//		System.out.println(mergedPhotos);
		
		
		/*
		// 4. none이 없는 sortendPhotoList 준비
		Iterator<String> oldItr = null; // 구 사진 이터레이터(매번 갱신됨)
		Iterator<String> newItr = newConstPhotos.iterator(); // 신 사진 이터레이터(한 번만 돌음)
		ArrayList<String> sortedPhotos = new ArrayList<String>(); // 정렬된 리스트
		String newTemp = null; // 새 사진 임시 저장용
		String oldTemp = null; // 구 사진 임시 저장용
		
		
		// 5. 돌면서 비교
		while(newItr.hasNext()) {
			newTemp = newItr.next();
			oldItr = oldConstPhotos.iterator();
			while(oldItr.hasNext()) {
				oldTemp = oldItr.next();
				if(!newTemp.equals(oldTemp)) { // 같지 않으면 검사 시작
					if("none".equals(oldTemp)) { // 구 사진이 비어있으면
						if(!("none".equals(newTemp))) { // 신 사진이 안 비었으면
							if(-1 == sortedPhotos.indexOf(newTemp)) { // 신 사진 중복 삽입 방지
								sortedPhotos.add(newTemp); // 새 사진 넣음
								System.out.println("real add : " + newTemp);
							}
						} else { // 신 사진이 비었다면
							
						}			
					} else if(!("none".equals(oldTemp))) {// 구 사진이 비어있지 않으면
						if("none".equals(newTemp)) { // 새 사진이 비어있다면
							if(-1 == sortedPhotos.indexOf(oldTemp)) { // 구 사진 중복 삽입 방지
								sortedPhotos.add(oldTemp); // 구 사진 그대로 넣음
								System.out.println("real add : " + oldTemp);
							}
						} else { // 구 사진도, 신 사진도 안 비었을 때							
							removedPhoto = new File(path + oldTemp); // 구 사진 삭제
							if(removedPhoto.exists()) { // 구 사진 삭제
								removedPhoto.delete();
							}
							
							if(-1 == sortedPhotos.indexOf(newTemp)) { // 중복 방지
								sortedPhotos.add(newTemp); // 새 사진 넣음
								System.out.println("real add : " + newTemp);
							}
						}	
					}
				}
			}
		}
		*/
				
		// 6. 정렬시켜 완성시킨 최종 포토를 넣어준다
		ptnProfile.setPtnPhotos(String.join("§", mergedPhotos));
		
//		System.out.println(oldConstPhotos);
//		System.out.println(newConstPhotos);
//		System.out.println(ptnProfile.getPtnPhotos());
		
		int result = new PartnersService().updatePartner(ptnProfile);
			
		if(0 < result) {
			url = "/selectPartner.do?partnerCode=" + ptnProfile.getPartnerCode();
		}else {
			url = "views/common/errorPage.jsp";
			
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
