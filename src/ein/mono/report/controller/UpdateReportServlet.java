package ein.mono.report.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.report.model.service.ReportService;

import ein.mono.report.model.vo.ReportVo;

/**
 * Servlet implementation class reportUpdateServlet
 */
@WebServlet("/updateReport.do")
public class UpdateReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//디테일리스트에서 내용확인후 관리자가 등급변경(관리자)
		
		/*ReportVo report = new ReportVo();
		
		int result = new ReportService().updateReport(report);*/
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		
		
		
		int reportNo = Integer.parseInt(request.getParameter("reportNo"));
		String stopdate = request.getParameter("stopDate");
		ReportVo report = new ReportVo();
		
		
		int result = new ReportService().updateReport(stopdate, reportNo);
		
		RequestDispatcher view = null;
		
		if(0<result) {
			request.setAttribute("msg", "등급변경이 완료되었습니다");
			view = request.getRequestDispatcher("views/common/successPage.jsp");
		}else {
			request.setAttribute("msg", "등급변경에 실패했습니다");
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
		}
		view.forward(request, response);
		
		
	}

}
