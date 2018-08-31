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

@WebServlet("/selectMyRequestList.do")
public class SelectMyRequestListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SelectMyRequestListServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String mCode = request.getParameter("mCode");
		ArrayList<RequestVo> list = rqs.selectMyRequestListPage(currentPage, limit, mCode);
		String url="";
		if(null != list){
			url = "views/request/myRequestList.jsp";
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
		}else{
			url = "vies/request/endRequest.jsp";
			request.setAttribute("requestValue", "내 견적 목록 조회 실패");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);

	}

}
