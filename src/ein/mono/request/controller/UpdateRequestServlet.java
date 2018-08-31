package ein.mono.request.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import ein.mono.request.model.service.RequestService;
import ein.mono.request.model.vo.RequestVo;

@WebServlet("/updateRequest.do")
public class UpdateRequestServlet extends HttpServlet { // 고객이 주문이나 경매의 상세 정보들을 수정한 결과를 반영
	private static final long serialVersionUID = 1L;
    
    public UpdateRequestServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html charset=UTF-8");
		
		int maxSize = 1024 * 1024 * 10;
		RequestDispatcher view = null;
		if(!ServletFileUpload.isMultipartContent(request)){
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "전송 데이터의 타입을 확인하십시오.");
			view.forward(request, response);
		}
		String root = request.getServletContext().getRealPath("/");
		String path = root + "upload/request_photo";
		MultipartRequest mRequest = new MultipartRequest(request, path,maxSize,"UTF-8");
		
		String reqCode = mRequest.getParameter("reqCode");
		String userCode = mRequest.getParameter("userCode");
		String ptnCode = mRequest.getParameter("ptnCode");
		String reqType = mRequest.getParameter("reqType");
		String constAddress = mRequest.getParameter("zipcode") + "," + mRequest.getParameter("address1") + "," + mRequest.getParameter("address2");
		int acreage = Integer.parseInt(mRequest.getParameter("acreage"));
		String veranda = mRequest.getParameter("veranda");
		String electrics = String.join(", ", mRequest.getParameterValues("elec"));
		String flooring = mRequest.getParameter("floor");
		String papering = mRequest.getParameter("paper");
		String coating = mRequest.getParameter("coating");
		String innerConst = mRequest.getParameter("innerConst");
		String cleaning = mRequest.getParameter("cleaning");
		
		String samplePhotoUrl1 = mRequest.getFilesystemName("photo1");
		String samplePhotoUrl2 = mRequest.getFilesystemName("photo2");
		String newPhotoUrl1 = mRequest.getFilesystemName("newPhoto1");
		String newPhotoUrl2 = mRequest.getFilesystemName("newPhoto2");
		if(null != newPhotoUrl1){
			File file = new File(path + "/" + samplePhotoUrl1);
			file.delete();
		}
		if(null != newPhotoUrl2){
			File file = new File(path + "/" + samplePhotoUrl2);
			file.delete();
		}
		String reqPrice = mRequest.getParameter("price");
		String reqContent = mRequest.getParameter("requestMsg");
		String reqDate = mRequest.getParameter("requestDate");
		System.out.println("reqDate : " + reqDate);
		
		//RequestVo req = new RequestVo(userCode, ptnCode, constAddress, reqContent, samplePhotoUrl1, samplePhotoUrl2, 
		//											reqPrice, reqDate, acreage, veranda, electrics, flooring, papering, coating, innerConst, cleaning, reqType);
		RequestVo req = new RequestVo();
		req.setReqCode(reqCode);
		req.setConstAddress(constAddress);
		req.setAcreage(acreage);
		req.setVeranda(veranda);
		req.setElectrics(electrics);
		req.setFlooring(flooring);
		req.setPapering(papering);
		req.setCoating(coating);
		req.setCleaning(cleaning);
		req.setSamplePhotoUrl1(null != samplePhotoUrl1 ? newPhotoUrl1 : samplePhotoUrl1);
		req.setSamplePhotoUrl2(null != samplePhotoUrl2 ? newPhotoUrl2 : samplePhotoUrl2);
		req.setReqPrice(reqPrice);
		req.setReqContent(reqContent);
		req.setReqDate(reqDate);

		int result = new RequestService().updateRequest(req);
		
		String url = "";
		if(0 < result){
			url = "views/request/requestDetail.jsp";
			//마이페이지 리스트 이동
			request.setAttribute("req", req);
		}else{
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "시공항목 수정에 실패하였습니다.");
		}
		view = request.getRequestDispatcher(url);
		view.forward(request, response);
		// 업데이트 완료 후, selectRequestDetail.do?reqCode= + req.getReqCode()로 이동
	}

}
