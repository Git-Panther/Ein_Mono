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
 * Servlet implementation class NoticeUpdateServlet
 */
@WebServlet("/noticeUpdate.do")
public class UpdateNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String noticeno = request.getParameter("noticeNo");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		int limit = 10;
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		NoticeVo notice = new NoticeVo(title, content);
		
		int result = new NoticeService().rewriteNotice(notice, noticeno);
		
		RequestDispatcher view = null;
		
		if(0<result) {
			view = request.getRequestDispatcher("noticeList.do");
			request.setAttribute("list", new NoticeService().selectNoticeList(currentPage, limit));			
		}else {
			
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "공지사항수정에실패했습니다");
			
		}
		view.forward(request, response);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
