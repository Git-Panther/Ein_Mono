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

@WebServlet("/insertChoicePtn.do")
public class InsertChoicePtnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertChoicePtnServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ptnCode = request.getParameter("ptnCode");
		String reqCode = request.getParameter("reqCode");
		RequestService rqs = new RequestService();
		

		RequestVo req = new RequestVo(ptnCode, reqCode);
		
		int result = new RequestService().insertChoicePtn(req);
		
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
		String mCode = request.getParameter("mCode");
		ArrayList<RequestVo> list = rqs.selectMyRequestListPage(currentPage, limit, mCode);

		RequestDispatcher view = null;
		if(0 < result){
			//view = request.getRequestDispatcher("views/request/endRequest.jsp");
			//request.setAttribute("requestValue", "업체선택성공");
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			view = request.getRequestDispatcher("views/request/choicePtn.jsp");
		}else{
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "견적 신청 오류");
		}
		view.forward(request, response);
	}

}
