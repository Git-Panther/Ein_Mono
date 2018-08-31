package ein.mono.partners.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.partners.model.service.PartnersService;
import ein.mono.partners.model.vo.PartnersVo;

/**
 * Servlet implementation class SelectPartnerServlet
 */
@WebServlet("/selectPartner.do")
public class SelectPartnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectPartnerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String ptnCode = request.getParameter("partnerCode");
		PartnersVo ptnProfile = new PartnersService().selectPartner(ptnCode);
		//String photoIndex = request.getParameter("photoIndex") != null ? request.getParameter("photoIndex") : "1";
		String url = null;
		boolean isUpdate = null != request.getParameter("isUpdate") ? true : false;
		
		if(null != ptnProfile) {
			if(isUpdate) url = "/views/mypage/updatePtnInfoForm.jsp"; // 수정하려고 온 거면
			else url = "/views/partners/partnerProfile.jsp";
			// 여기서 경로 조정까지 해주면 완-벽
			request.setAttribute("ptnProfile", ptnProfile);
		}else {
			url = "/views/partners/partnerProfile.jsp";
			request.setAttribute("msg", "failed");
			//request.setAttribute("photoIndex", photoIndex);
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}
}
