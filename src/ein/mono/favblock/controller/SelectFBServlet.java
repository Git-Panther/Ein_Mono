package ein.mono.favblock.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.favblock.model.service.FBService;
import ein.mono.favblock.model.vo.FBVo;
import ein.mono.member.model.vo.MemberVo;

/**
 * Servlet implementation class LBListServlet
 */
@WebServlet("/selectFBList.do")
public class SelectFBServlet extends HttpServlet { // 즐겨찾기와 차단 다 가져옴
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectFBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mCode = request.getParameter("member_code");
		String fb_type = request.getParameter("fb_type");

		List<FBVo> list = new FBService().selectLBList(mCode,fb_type);
		
		RequestDispatcher view = null;
		String url = "";
		if(fb_type.equals("즐겨찾기")){
			if(list != null){
				url = "views/mypage/favList.jsp";
				request.setAttribute("list", list);
			}else{
				url = "views/common/errorPage.jsp";
				request.setAttribute("msg", "즐겨찾기/차단 조회 오류");
			}
		}else{
			if(list != null){
				url = "views/mypage/blockList.jsp";
				request.setAttribute("list", list);
			}else{
				url = "views/common/errorPage.jsp";
				request.setAttribute("msg", "즐겨찾기/차단 조회 오류");
			}
		}
		view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

}
