package ein.mono.favblock.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ein.mono.favblock.model.service.FBService;
import ein.mono.favblock.model.vo.FBVo;
import ein.mono.member.model.vo.MemberVo;

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
		System.out.println("실행");
		String fbType = request.getParameter("fbType");
		String memberCode = request.getParameter("memberCode");
		String partnerCode = request.getParameter("partnerCode");
		FBVo fb = new FBVo();
		fb.setFbType(fbType);
		fb.setMemberCode(memberCode);
		fb.setTargetCode(partnerCode);
		
		int result = new FBService().deleteFavorBlock(fb);
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		
		if(result > 0){
			list = new FBService().selectFBList(memberCode, fbType);
		}
		
		response.setContentType("application/json; charset=utf-8");
		new Gson().toJson(list,response.getWriter());
		
		
		/*if(fb.getFbType().equals("즐겨찾기")) {
			response.getWriter().print(result);
		}else{ // 차단
			
		}*/
		
	}
}
