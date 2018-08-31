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
 * Servlet implementation class NoticeUpdateFormServlet
 */
@WebServlet("/noticeUpdateForm.do")
public class UpdateFormNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UpdateFormNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String noticeNo = request.getParameter("noticeNo");
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		NoticeVo notice = new NoticeService().getNoticeFormData(noticeNo);
		
		RequestDispatcher view = null;
		
		if(null != notice) {
			view = request.getRequestDispatcher("views/notice/noticeUpdateForm.jsp");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("notice", notice);
		}else {
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg","공지사항수정페이지이동에 실패하였습니다" );
		}
		view.forward(request, response);
	}

	

}
