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

@WebServlet("/selectFAQ.do")
public class SelectFAQServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectFAQServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String post_code = request.getParameter("post_code");
		String post_type = request.getParameter("post_type");

		PostVo post = new PostService().selectPost(post_code, post_type);
		
		String url = "";
		if(null != post) {
			url = "views/admin/faqDetail.jsp";
			request.setAttribute("post", post);
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "FAQ 상세 조회 오류");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

}
