package ein.mono.qna.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.member.model.vo.MemberVo;
import ein.mono.qna.model.service.QnAService;
import ein.mono.qna.model.vo.QnAVo;

/**
 * Servlet implementation class QuestionWriteServlet
 */
@WebServlet("/insertQnA.do")
public class QuestionWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String partnerCode = request.getParameter("partnerCode");
		String title = request.getParameter("title");
		String writerCode = ((MemberVo)request.getSession().getAttribute("LoginMember")).getMemberCode();
		String content = request.getParameter("sky"); // 게시판 내용
		
		String url = null;
		
		if(1 > title.length() || 1 > content.length()){
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("alert('제목이나 내용이 비어있을 수 없습니다!');");
			out.println("location.href='/mono/views/post/writeGalleryPost.jsp?pType=QNA&partnerCode="+partnerCode+"';");
			out.println("</script>");
			out.println("</html>");
		}else{	
			QnAVo qna = new QnAVo();
			qna.setTitle(title);
			qna.setWriter_code(writerCode);
			qna.setContent(content);
			qna.setPtnCode(partnerCode);
			qna.setPost_type("QNA");
			
			String postCode = new QnAService().insertPtnQnA(qna);
			if(null != postCode) {
				url = "/mono/selectQnAList.do?partnerCode="+partnerCode;
				response.sendRedirect(url);				
			}else {
				url = "/views/common/errorPage.jsp";
				request.getRequestDispatcher(url).forward(request, response);
			}			
		}		
		
	}

}
