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

@WebServlet("/insertChoicePtn.do")
public class InsertChoicePtnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertChoicePtnServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ptnCode = request.getParameter("ptnCode");
		String reqCode = request.getParameter("reqCode");
		

		RequestVo req = new RequestVo(ptnCode, reqCode);
		
		int result = new RequestService().insertChoicePtn(req);

		RequestDispatcher view = null;
		if(0 < result){
			//view = request.getRequestDispatcher("views/request/endRequest.jsp");
			//request.setAttribute("requestValue", "업체선택성공");
			response.sendRedirect("views/request/choicePtn.jsp");
		}else{
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "견적 신청 오류");
		}
		view.forward(request, response);
	}

}
