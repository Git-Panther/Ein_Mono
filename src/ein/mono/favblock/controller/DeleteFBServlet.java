package ein.mono.favblock.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.favblock.model.service.FBService;
import ein.mono.favblock.model.vo.FBVo;

/**
 * Servlet implementation class LBDeleteServlet
 */
@WebServlet("/deleteFB.do")
public class DeleteFBServlet extends HttpServlet { // ajax 처리용
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FBVo fb = new FBVo();
		fb.setFbType(request.getParameter("fbType"));
		fb.setMemberCode(request.getParameter("memberCode"));
		fb.setTargetCode(request.getParameter("partnerCode"));
		
		int result = new FBService().deleteFavorBlock(fb);
		if(fb.getFbType().equals("즐겨찾기")) {
			response.getWriter().print(result);
		}else{ // 차단
			
		}
	}
}
