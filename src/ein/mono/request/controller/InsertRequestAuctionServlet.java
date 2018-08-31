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

/**
 * Servlet implementation class InsertRequestAuctionServlet
 */
@WebServlet("/insertRequestAuction.do")
public class InsertRequestAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertRequestAuctionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ptnCode = request.getParameter("ptnCode");
		String reqCode = request.getParameter("reqCode");
		System.out.println("ptnCode : " + ptnCode);
		System.out.println("reqCode : " + reqCode);
		int ptnPay = Integer.parseInt(request.getParameter("ptnPay"));
		System.out.println("ptnPay : " + ptnPay);
		
		
		RequestVo req = new RequestVo(reqCode, ptnCode, ptnPay);
		System.out.println("req : " + req);
		int result = new RequestService().insertRequestAuction(req);

		RequestDispatcher view = null;
		if(0 < result){
			//view = request.getRequestDispatcher("views/request/endRequest.jsp");
			//request.setAttribute("requestValue", "경매신청성공");
			response.sendRedirect("views/request/requestList.jsp");
		}else{
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "견적 신청 오류");
		}
		view.forward(request, response);
	}

}
