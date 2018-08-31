package ein.mono.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.partners.model.service.PartnersService;
import ein.mono.partners.model.vo.PartnersVo;

@WebServlet("/updatePtnCheck.do")
public class UpdatePtnCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdatePtnCheckServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_code = request.getParameter("user_code");
		int flag = Integer.parseInt(request.getParameter("flag"));
		
		PartnersVo ptn = new PartnersService().selectPartnerAdmin(user_code);
		//ptn.setPartnerCheck("N");
		
		if(null != ptn) {
			int result = new PartnersService().updatePtnCheck(ptn, flag);
			
			String url = "";
			RequestDispatcher view = null;
			if(0 < result) {
				response.sendRedirect("selectJoinRequestPtnList.do");
			} else {
				url = "views/common/errorPage.jsp";
				request.setAttribute("msg", "업체 가입 승인 오류");
				view = request.getRequestDispatcher(url);
				view.forward(request, response);
			}
		}
		
	}

}
