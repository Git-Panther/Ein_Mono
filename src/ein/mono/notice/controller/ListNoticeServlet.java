package ein.mono.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.common.PageInfo;
import ein.mono.notice.model.service.NoticeService;
import ein.mono.notice.model.vo.NoticeVo;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/noticeList.do")
public class ListNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeService ns = new NoticeService();
		
		int currentPage; //현재페이지 번호
		int limit; //한페이지에 출력할 글에 갯수
		int limitPage; //한페이지에 출력할 페이지갯수
		int maxPage; //가장 마지막 페이지
		int startPage; // 시작페이지 변수
		int endPage; //마지막페이지 변수
		
		limit = 10;
		limitPage = 10;
		
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}else {
			currentPage = 1;
		}
		
		int listCount = ns.selectreportTotalCount();
		maxPage = (int)((double)listCount/limit + 0.9);
		startPage = (int)(currentPage/limitPage*limitPage)+1;
		endPage = startPage+limitPage-1;
		
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		PageInfo pi = new PageInfo(currentPage, limitPage, maxPage, startPage, endPage, listCount);
		
		ArrayList<NoticeVo> list = ns.selectNoticeList(currentPage, limit);
		
		String url = "";
		
		if(null != list) {
			url = "views/notice/noticeList.jsp?currentPage = "+currentPage;
			
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);

		}else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "공지사항조회에 실패하였습니다");
		}
		
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	
	

}
