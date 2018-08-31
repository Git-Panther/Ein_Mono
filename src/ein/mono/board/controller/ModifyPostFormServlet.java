package ein.mono.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;
import ein.mono.board.model.vo.PostVo;

/**
 * Servlet implementation class ModifyBoardFormServlet
 */
@WebServlet("/modifyPostForm.do")
public class ModifyPostFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyPostFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pCode = request.getParameter("pCode");
		
		PostVo post = new PostService().updatePost(pCode);
		
		RequestDispatcher view = null;
		if(null != post){
			request.setAttribute("post", post);
			view = request.getRequestDispatcher("views/post/modifyPostForm.jsp");
		}else{
			System.out.println("error");
		}
		view.forward(request, response);
	}

}
