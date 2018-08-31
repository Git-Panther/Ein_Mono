package ein.mono.favblock.controller;

import java.io.IOException;
import java.util.ArrayList;

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
@WebServlet("/selectFB.do")
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
		String userCode = ((MemberVo)request.getSession().getAttribute("user")).getMemberCode();
		ArrayList<FBVo> list = new FBService().selectLBList(userCode);//페이징처리나 가지고갈 변수 생각하기
	}

}
