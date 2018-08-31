package ein.mono.mypage.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.mypage.model.service.MypageService;

/**
 * Servlet implementation class DeletePhotoServlet
 */
@WebServlet("/deletePhoto.do")
public class DeletePhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePhotoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String chk = request.getParameter("chk");
		String mCode = request.getParameter("mCode");
		
		if(chk.equals("")){
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("alert('파일을 선택해 주세요');");
			out.println("location.href='/mono/constList.do?mCode="+mCode+"';");
			out.println("</script>");
			out.println("</html>");
		}else{
			int result = new MypageService().deletePhoto(mCode,chk);
			if(0 < result){
				response.sendRedirect("/mono/constList.do?mCode="+mCode);
			}else{
				System.out.println("error");
			}			
		}

	}

}
