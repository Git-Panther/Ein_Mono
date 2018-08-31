package ein.mono.request.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.common.PageInfo;
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
		
		RequestService rqs = new RequestService();
		
		int currentPage;
		int limitPage;
		int maxPage;
		int startPage;
		int endPage;
		int limit;
		
		limit = 10;
		limitPage = 10;
		
		if(request.getParameter("currentPage") != null){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}else{
			currentPage = 1;
		}
		
		int listCount = rqs.selectRequestTotalCount();
		maxPage = (int)((double)listCount / limit + 0.9);
		startPage = (int)(currentPage / limitPage * limitPage) + 1;
		endPage = startPage + limitPage -1;
		if(maxPage < endPage){
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(currentPage, limit, maxPage, startPage, endPage, listCount);
		
		ArrayList<RequestVo> list = rqs.selectRequestListPage(currentPage, limit);
		
		RequestDispatcher view = null;
		if(0 < result){
			//view = request.getRequestDispatcher("views/request/endRequest.jsp");
			//request.setAttribute("requestValue", "경매신청성공");
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			view = request.getRequestDispatcher("views/request/requestList.jsp");
		}else{
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "견적 신청 오류");
		}
		view.forward(request, response);
	}

}
