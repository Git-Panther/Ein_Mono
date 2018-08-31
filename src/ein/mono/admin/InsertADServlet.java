package ein.mono.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import ein.mono.board.model.service.PostService;
import ein.mono.board.model.vo.PostVo;
import ein.mono.common.MyRenamePolicy;
import ein.mono.event.model.service.EventService;
import ein.mono.event.model.vo.EventVo;
import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

@WebServlet("/insertEvent.do")
public class InsertADServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertADServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int maxSize = 1024 * 1024 * 10; // 10mb
		// 2. 파일 전송 시 enctype 정상적으로 설정되었는지 확인
		RequestDispatcher view = null;
		if(! ServletFileUpload.isMultipartContent(request)) {
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "전송 데이터의 타입을 확인하십시오!!");
			view.forward(request, response);
		}
		// 3. 파일 저장 경로 설정
		String root = request.getServletContext().getRealPath("/");
		String path = root + "upload/ad/";
		
		// 4. request -> multipartrequest 객체로 변환
		// fileRenamePolicy 설정 : FileRenamePolicy 인터페이스 구현해서 rename 메소드를 override
		MultipartRequest mRequest = 
				new MultipartRequest(request, path, maxSize, "UTF-8", new MyRenamePolicy());
		
		// 전송 값을 변수에 저장
		// 객체 2개 -> 게시판에 추가할 객체, attachment 추가할 객체(list)
		HttpSession session = request.getSession();
		MemberVo member = (MemberVo) session.getAttribute("LoginMember");
		
		String writerCode = member.getMemberCode();
		String ptnName = mRequest.getParameter("ptn_name");
		String startDate =  mRequest.getParameter("startDate");
		String endDate = mRequest.getParameter("endDate");
		String title = mRequest.getParameter("title");
		String content = mRequest.getParameter("content");
		
		PostVo post = new PostVo();
		post.setPost_type("ADV");
		post.setTitle(title);
		post.setContent(content);
		post.setWriter_code(writerCode);
		
		ArrayList<String> oldNames = new ArrayList<String>();
		ArrayList<String> newNames = new ArrayList<String>();
		Enumeration<String> fileNameEnum = mRequest.getFileNames();
		for(int i=0; fileNameEnum.hasMoreElements(); i++) {
			fileNameEnum.nextElement();
			newNames.add(mRequest.getFilesystemName("eventImg" + (i + 1)));
			oldNames.add(mRequest.getOriginalFileName("eventImg" + (i + 1)));
		}

		EventVo event = new EventVo();
		for(int i=0; i < newNames.size(); i++) {
			switch(i) {
			case 0:
				event.setAdvBanner(newNames.get(i));
				break;
			case 1:
				event.setAdvPhoto(newNames.get(i));
				break;
			}
			//temp.setPath(path);
		}
		event.setMember_name(member.getMemberName());
		event.setAdvStartDate(startDate);
		event.setAdvEndDate(endDate);
		
		int result = new PostService().insertPost(post);
		if(0 < result) {
			String post_code = new PostService().selectPostCode();
			String ptn_code = new MemberService().selectMemberCode(ptnName);
			result += new EventService().insertEvent(post_code, ptn_code, event);
		}
		String url = "";
		if(1 < result) {
			response.sendRedirect("selectADListAdmin.do");
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "광고 작성 실패");
			view = request.getRequestDispatcher(url);
			view.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
