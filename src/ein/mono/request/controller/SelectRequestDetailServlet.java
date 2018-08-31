package ein.mono.request.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.member.model.vo.MemberVo;
import ein.mono.request.model.service.RequestService;
import ein.mono.request.model.vo.RequestVo;

@WebServlet("/selectRequestDetail.do")
public class SelectRequestDetailServlet extends HttpServlet { // 주문 목록을 선택했을 때 상세 정보 불러오기
	private static final long serialVersionUID = 1L;
       
    public SelectRequestDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqCode = request.getParameter("reqCode");
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		RequestVo req = new RequestService().selectRequestDetail(reqCode);
		String url = "";
		System.out.println("req : " + req);
		if(null != req){
			url = "views/request/requestDetail.jsp";
			request.setAttribute("req", req);
			request.setAttribute("currentPage", currentPage);
		}else{
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "견적 상세조회에 실패하였습니다.");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}
}
