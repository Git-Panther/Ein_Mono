package ein.mono.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;
import ein.mono.board.model.vo.PostVo;

/**
 * Servlet implementation class WriteGalleryServlet
 */
@WebServlet("/writePost.do")
public class WritePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WritePostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		String title = request.getParameter("title");
		String content = request.getParameter("sky");
		String mCode = request.getParameter("mCode");
		String pType = request.getParameter("pType");
		
		if(5 > title.length() || 20 > content.length()){
			String str = title.length() < 5 ? "글 제목" : "내용";
			int num = title.length() < 5 ? 5 : 20;
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("alert('"+str+"을 "+num+"자 이상 입력해 주세요');");
			out.println("location.href='/mono/views/post/writeGalleryPost.jsp';");
			out.println("</script>");
			out.println("</html>");
		}else{	
			PostVo p = new PostVo();
			p.setTitle(title);
			p.setContent(content);
			p.setWriter_code(mCode);
			p.setPost_type(pType);
			
			int result = new PostService().insertPost(p);
			
			if(0 < result){
				response.sendRedirect("/mono/selectPostList.do?posttype="+pType+"&currentPage=1");		
			}else{
				System.out.println("error");
			}
		}
	}

}
