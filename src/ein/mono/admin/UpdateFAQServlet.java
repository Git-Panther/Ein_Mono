package ein.mono.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;
import ein.mono.board.model.vo.PostVo;

@WebServlet("/updateFAQ.do")
public class UpdateFAQServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateFAQServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String post_code = request.getParameter("post_code");
		String post_type = request.getParameter("post_type");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		PostVo post = new PostService().selectPost(post_code, post_type);
		
		int result = 0;
		if(null != post) {
			result = new PostService().updatePost(post, title, content);
		}
		
		String url = "";
		if(0 < result) {
			url = "selectFAQListAdmin.do?post_type=" + post_type;
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "FAQ 수정 오류");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

}
