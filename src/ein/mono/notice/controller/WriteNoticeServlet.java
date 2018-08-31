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
 * Servlet implementation class NoticeWriteServlet
 */
@WebServlet("/noticeWrite.do")
public class WriteNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberCode = request.getParameter("memberCode");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		NoticeVo notice= new NoticeVo(memberCode, title, content);
		
		int result = new NoticeService().writeNotice(notice);
		
		RequestDispatcher view = null;
		
		if(0<result) {
			request.setAttribute("currentPage", currentPage);
			view = request.getRequestDispatcher("noticeList.do");
		
		}else {
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "공지사항작성중오류가 발생했습니다");
		}
		view.forward(request, response);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
