package ein.mono.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.notice.model.service.NoticeService;
import ein.mono.notice.model.vo.NoticeVo;

/**
 * Servlet implementation class NoticeDetailServlet
 */
@WebServlet("/noticeDetail.do")
public class DetailNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String noticeNo = request.getParameter("noticeNo");
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		NoticeVo notice =  new NoticeService().detailNotice(noticeNo);
		
		String url = "";
		
		if(null != notice) {
			url = "views/notice/noticeListDetail.jsp";
			notice.setViewsCount(notice.getViewsCount()+1);
			request.setAttribute("notice", notice);
			request.setAttribute("currentPage", currentPage);
		}else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "공지사항상세조회에 실패하였습니다");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}
	


}
