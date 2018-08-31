package ein.mono.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;

/**
 * Servlet implementation class RemoveServlet
 */
@WebServlet("/removePost.do")
public class RemovePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemovePostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pCode= request.getParameter("pCode");
		String pType= request.getParameter("pType");
		
		int result = new PostService().removePost(pCode);
		
		
		if(0 < result){
			response.sendRedirect("/mono/selectPostList.do?posttype="+pType);
		}
	}

}
