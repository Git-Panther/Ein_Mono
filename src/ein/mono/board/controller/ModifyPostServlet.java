package ein.mono.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;
import ein.mono.board.model.vo.PostVo;

/**
 * Servlet implementation class ModifyBoardServlet
 */
@WebServlet("/modifyPost.do")
public class ModifyPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pCode = request.getParameter("pCode");
		String title = request.getParameter("title");
		String content = request.getParameter("sky");
		String pType = request.getParameter("pType");
		
		PostVo post = new PostVo();
		post.setPost_code(pCode);
		post.setTitle(title);
		post.setContent(content);
		
		
		int result = new PostService().updatePost(post);
		
		
		if(0 < result){
			response.sendRedirect("/mono/selectPostList.do?posttype="+pType);
		}else{
			System.out.println("error");
		}
	}

}
