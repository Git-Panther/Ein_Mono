package ein.mono.request.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.request.model.service.RequestService;
import ein.mono.request.model.vo.RequestVo;

@WebServlet("/deleteRequest.do")
public class DeleteRequestServlet extends HttpServlet { // 주문(경매, 시공 포함)을 취소하여 삭제해야 할 때 발생. delflag를 바꾸거나 행 자체를 삭제.
	private static final long serialVersionUID = 1L;
       
    public DeleteRequestServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqCode = request.getParameter("reqCode"); // 삭제할 주문
		
		RequestVo req = new RequestVo(reqCode);
		int result = new RequestService().deleteRequest(reqCode,req);
		
		RequestDispatcher view = null;
		if(0 < result){
			response.sendRedirect("/mono/views/request/endRequest.jsp");
		}else{
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "견적 신청 오류");
			view.forward(request, response);
		}
		// 삭제 문법은 모두가 공유하므로 통일된 것을 쓴다.
		// url은 selectRequestList.do?reqType=all&reqCheck=all 
	}


}
