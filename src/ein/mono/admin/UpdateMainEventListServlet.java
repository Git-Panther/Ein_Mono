package ein.mono.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.event.model.service.EventService;

@WebServlet("/updateMainEventList.do")
public class UpdateMainEventListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateMainEventListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println(eCodeList); // ,로 구분되어 옴. 파싱해줘야 함.
		String[] eCodeList = request.getParameter("eCodeList").split(",");
		
		/*for(String s : eCodeList) {
			System.out.println(s);
		}*/
		
		int result = 0;
		for(int i=0; i<eCodeList.length; i++) {
			// 메인이벤트 4개의 level 업데이트 해준 후
			switch(i) {
			case 0:
				result += new EventService().updateEventLevel(i+1, eCodeList[i]);
				break;
			case 1:
				result += new EventService().updateEventLevel(i+1, eCodeList[i]);
				break;
			case 2:
				result += new EventService().updateEventLevel(i+1, eCodeList[i]);
				break;
			case 3:
				result += new EventService().updateEventLevel(i+1, eCodeList[i]);
				break;
			}
			// 나머지 이벤트의 level 2로 업데이트 해줘야 함.
			if(eCodeList.length > 4) {
				result += new EventService().updateSubEventLevel(eCodeList);
			} else {
				result = 5;
			}
		}

		String url = "";
		if(4 < result) {
			url = "selectADListAdmin.do?eCodeList=" + request.getParameter("eCodeList");
			//request.setAttribute("eCodeList", eCodeList);
		}  else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "메인 광고 업데이트 오류");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
