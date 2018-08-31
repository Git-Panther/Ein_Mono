package ein.mono.request.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ein.mono.common.JDBCTemplate;
import ein.mono.event.model.vo.EventVo;
import ein.mono.member.model.vo.MemberVo;
import ein.mono.request.model.dao.RequestDao;
import ein.mono.request.model.vo.RequestVo;

public class RequestService {

	public int insertRequest(RequestVo req) {
		Connection con = JDBCTemplate.getConnection();
		// 각종 연결을 실행
		
		// sql 실행
		int result1 = new RequestDao().insertRequest(con, req);
		//발생한 키 값 조회 쿼리

		String key = new RequestDao().selectRequest(con, req);
		int result2 = new RequestDao().insetRequestInfo(con, req, key);
		System.out.println("req_info result : " + result2);
		if(0 < result1){
			JDBCTemplate.commit(con);
		}else{
			JDBCTemplate.rollback(con);
		}
		JDBCTemplate.close(con);
		int result = result1 + result2;
		return result;
	}
/*	public int insertRequestInfo(RequestVo req) {
		Connection con = JDBCTemplate.getConnection();
		// 각종 연결을 실행
		
		// sql 실행
		int result = new RequestDao().insetRequestInfo(con, req);
		if(0 < result){
			JDBCTemplate.commit(con);
		}else{
			JDBCTemplate.rollback(con);
		}
		JDBCTemplate.close(con);
		return result;
	}	
*/
	public int updateRequest(RequestVo req) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new RequestDao().updateRequest(con, req);
		if(0 < result){
			JDBCTemplate.commit(con);
		}else{
			JDBCTemplate.rollback(con);
		}
		JDBCTemplate.close(con); // 자원 반납
		return result;
	}

	public int deleteRequest(String reqCode, RequestVo req) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		
		int result = new RequestDao().deleteRequest(con, reqCode, req);
		if(0 < result){
			JDBCTemplate.commit(con);
		}else{
			JDBCTemplate.rollback(con);
		}		
		JDBCTemplate.close(con); // 자원 반납
		
		return result;
	}


	public RequestVo selectRequestDetail(String reqCode) {
		Connection con = JDBCTemplate.getConnection();

		RequestVo req = new RequestDao().selectRequestDetail(con, reqCode);

		JDBCTemplate.close(con); // 자원 반납
		System.out.println("ser req : " + req);
		return req;
	}

	public ArrayList<RequestVo> selectRequestList(MemberVo member, String reqType, String reqCheck) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<RequestVo> list = new RequestDao().selectRequestList(con, member, reqType, reqCheck);
		JDBCTemplate.close(con); // 자원 반납

		return list;
	}

	public ArrayList<RequestVo> selectRequestListByKeyword(MemberVo member, String reqType, String reqCheck, String condition,
			String keyword) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();

		ArrayList<RequestVo> list = new RequestDao().selectRequestListByKeyword(con, member, reqType, reqCheck, condition,
				keyword);
		JDBCTemplate.close(con); // 자원 반납

		return list;
	}

	public int updateReqCheck(String reqCode, String reqCheck) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		
		int result = new RequestDao().updateReqCheck(con, reqCode, reqCheck);
		JDBCTemplate.close(con); // 자원 반납

		return result;
	}
	public int selectRequestTotalCount() {
		Connection con = JDBCTemplate.getConnection();
		int listCount = new RequestDao().selectRequestTotalCount(con);
		JDBCTemplate.close(con);
		
		return listCount;
	}
	public ArrayList<RequestVo> selectRequestListPage(int currentPage, int limit) {
		Connection con = JDBCTemplate.getConnection();
		ArrayList<RequestVo> list = new RequestDao().selectRequestListPage(con, currentPage, limit);
		JDBCTemplate.close(con);
		return list;
	}
	public int insertRequestAuction(RequestVo req) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new RequestDao().insertRequestAuction(con, req);
		if(0 < result){
			JDBCTemplate.commit(con);
		}else{
			JDBCTemplate.rollback(con);
		}
		JDBCTemplate.close(con); // 자원 반납
		return result;
	}
	public List<RequestVo> selectChoicePtnList(String reqCode) {
		//커넥션을 맺는 역할 -> 서비스
		// -> 트랜젝션 관리를 해야하기 때문
		Connection con = JDBCTemplate.getConnection();
		ArrayList<RequestVo> list = new RequestDao().selectChoicePtnList(con, reqCode);
		
		JDBCTemplate.close(con);
		return list;
	}
	public int insertChoicePtn(RequestVo req) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new RequestDao().insertChoicePtn(con, req);
		if(0 < result){
			JDBCTemplate.commit(con);
		}else{
			JDBCTemplate.rollback(con);
		}
		JDBCTemplate.close(con); // 자원 반납
		return result;
	}
	public ArrayList<RequestVo> selectMyRequestListPage(int currentPage, int limit, String mCode) {
		Connection con = JDBCTemplate.getConnection();
		ArrayList<RequestVo> list = new RequestDao().selectMyRequestListPage(con, currentPage, limit, mCode);
		JDBCTemplate.close(con);
		return list;
	}


}
