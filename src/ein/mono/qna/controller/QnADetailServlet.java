package ein.mono.qna.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;
import ein.mono.board.model.service.ReplyService;
import ein.mono.board.model.vo.PostVo;
import ein.mono.board.model.vo.ReplyVo;

/**
 * Servlet implementation class QnADetailServlet
 */
@WebServlet("/selectQnA.do")
public class QnADetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnADetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String partnerCode = (String)request.getAttribute("partnerCode");
		String postCode = request.getParameter("post_code");
		int currentPage = (String)request.getAttribute("currentPage") == null ? 1 : Integer.parseInt((String)request.getAttribute("currentPage"));
		String listType = request.getParameter("listType") == null ? "common" : request.getParameter("listType"); // 전체 메시지가 온다. "Search_"+condition+":"+keyword 또는 Common
		// 뒤로가기 누르면 리스트 호출하고 현재 페이지, condition, keyowrd, 내지는 listType common or Search 이럴듯
				
		PostVo post = new PostService().selectPost(postCode);
		ArrayList<ReplyVo> replyList = new ReplyService().selectReplyList(postCode);
		
		String url = null;
		if(null != post && null != replyList) {
			url = "/views/qna/qnaDetail.jsp";
			request.setAttribute("post", post);
			
			request.setAttribute("replyList", replyList);
			request.setAttribute("partnerCode", partnerCode);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("listType", listType);
		}else {
			url = "/views/common/errorPage.jsp";
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // 삽입했을 때 와야 함
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
