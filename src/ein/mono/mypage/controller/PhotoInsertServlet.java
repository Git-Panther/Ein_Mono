package ein.mono.mypage.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import ein.mono.common.MyRenamePolicy;
import ein.mono.mypage.model.service.MypageService;

/**
 * Servlet implementation class PhotoInsertServlet
 */
@WebServlet("/photoInsert.do")
public class PhotoInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		int maxSize = 1024*1024*10;
		
		RequestDispatcher view = null;
		if(!ServletFileUpload.isMultipartContent(request)){
			System.out.println("error");
//			view = request.getRequestDispatcher("views/common/errorPage.jsp");
//			request.setAttribute("msg", "전송 데이터의 타입을 확인하십시오!!");
//			view.forward(request, response);
		}
		String root = request.getServletContext().getRealPath("/");
		String path = root + "upload/const_photo/";
		
		MultipartRequest mRequest = new MultipartRequest(request, path,maxSize,"UTF-8",new MyRenamePolicy());
		
		String mCode = mRequest.getParameter("mCode");
		String newName = mRequest.getFilesystemName("fileName");
		String oldName = mRequest.getOriginalFileName("fileName");
		
		if(null == oldName){
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("alert('파일을 선택해 주세요');");
			out.println("location.href='/mono/constList.do?mCode="+mCode+"';");
			out.println("</script>");
			out.println("</html>");
		}else{
			int result = new MypageService().insertPhoto(mCode,newName,oldName);		
			if(0 < result){
				response.sendRedirect("/mono/constList.do?mCode="+mCode);
			}else{
				System.out.println("error");
			}			
		}
		
	}

}
