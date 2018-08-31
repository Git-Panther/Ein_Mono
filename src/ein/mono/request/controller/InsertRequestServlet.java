package ein.mono.request.controller;

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

@WebServlet("/insertRequest.do")
public class InsertRequestServlet extends HttpServlet { // 고객의 일반 주문 삽입
	private static final long serialVersionUID = 1L;
       
    public InsertRequestServlet() {
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
		
		String userCode = mRequest.getParameter("userCode");
		String ptnCode = mRequest.getParameter("ptnCode");
		String reqType = mRequest.getParameter("reqType");
		String constAddress = mRequest.getParameter("zipcode") + "/" + mRequest.getParameter("address1") + "/" + mRequest.getParameter("address2");
		int acreage = Integer.parseInt(mRequest.getParameter("acreage"));
		String veranda = mRequest.getParameter("veranda");
		String electrics = "";
		if(mRequest.getParameterValues("elec") != null){
			electrics = String.join(", ", mRequest.getParameterValues("elec"));
		}
//		String electrics = mRequest.getParameterValues("elec") != null ? String.join(", ", mRequest.getParameterValues("elec"));
		String flooring = mRequest.getParameter("floor");
		String papering = mRequest.getParameter("paper");
		String coating = mRequest.getParameter("coating");
		//String innerConst = mRequest.getParameter("innerConst");
		String ceiling = mRequest.getParameter("ceiling");
		String middleDoor = mRequest.getParameter("middleDoor");
		String window = mRequest.getParameter("window");
		String tile = "";
		if(mRequest.getParameterValues("tile") != null){
			tile = String.join(", ", mRequest.getParameterValues("tile"));
		}
//		String tile = String.join(", ", mRequest.getParameterValues("tile"));
		String bathroom = mRequest.getParameter("bathroom");
		String kitchen = mRequest.getParameter("kitchen");
		String cleaning = mRequest.getParameter("cleaning");
		String samplePhotoUrl1 = mRequest.getFilesystemName("photo1");
		String samplePhotoUrl2 = mRequest.getFilesystemName("photo2");
		String reqPrice = mRequest.getParameter("price");
		String reqContent = mRequest.getParameter("requestMsg");
		String reqDate = mRequest.getParameter("requestDate");
	/*	
		RequestVo req = new RequestVo(userCode, ptnCode, constAddress, reqContent, samplePhotoUrl1, samplePhotoUrl2, 
													reqPrice, reqDate, acreage, veranda, electrics, flooring, papering, coating, innerConst, cleaning, reqType);
	*/
		RequestVo req = new RequestVo(userCode, ptnCode, constAddress, reqContent, samplePhotoUrl1, samplePhotoUrl2, 
												reqPrice, reqDate, acreage, veranda, electrics, flooring, papering, coating, ceiling, middleDoor, 
												window, tile, bathroom, kitchen, cleaning, reqType);
		
		int result = new RequestService().insertRequest(req);

		if(1 < result){
			view = request.getRequestDispatcher("views/request/endRequest.jsp");
			if(reqType == "경매"){
				request.setAttribute("requestValue", "경매성공");				
			}else{
				request.setAttribute("requestValue", "견적신청성공");
			}
		}else{
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "견적 신청 오류");
		}
		view.forward(request, response);
	}

}
