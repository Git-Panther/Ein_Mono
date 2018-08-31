package ein.mono.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class DetialGalleryServlet
 */
@WebServlet("/detailPost.do")
public class DetailPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailPostServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String pCode = request.getParameter("pCode");
		

		PostVo result = new PostService().getPost(pCode);
		ArrayList<ReplyVo> reply = new ReplyService().selectReplyList(pCode);

		RequestDispatcher view = null;
		if (null != result) {
			request.setAttribute("post", result);
			request.setAttribute("reply", reply);
			view = request.getRequestDispatcher("views/post/detailGalleryPost.jsp");
		} else {
			System.out.println("error");
		}
		view.forward(request, response);

	}

}