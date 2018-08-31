package ein.mono.request.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import ein.mono.common.JDBCTemplate;
import ein.mono.member.model.vo.MemberVo;
import ein.mono.request.model.vo.RequestVo;

public class RequestDao {
	
	private Properties prop = new Properties();
	
	public RequestDao() {
		String filename = RequestDao.class.getResource("/request/request_sql.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int insertRequest(Connection con, RequestVo req) {
		// RequestVo 정보 토대로 삽입
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		if(req.getReqType().equals("경매")){
			query = "INSERT INTO REQUEST VALUES('ACT' || SEQ_ACT.NEXTVAL,'경매',?,NULL,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),DEFAULT,SYSDATE + 7,'경매중',NULL,NULL)";			
		}else{
			query = "INSERT INTO REQUEST VALUES('REQ' || SEQ_REQ.NEXTVAL,'업체지정',?,?,?,?,?,?,?,NULL,NULL,NULL,'승인대기',NULL,NULL)";
		}
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, req.getUserCode());
			if(req.getReqType().equals("경매")){
				pstmt.setString(2, req.getConstAddress());
				pstmt.setString(3, req.getReqContent());
				pstmt.setString(4, req.getSamplePhotoUrl1());
				pstmt.setString(5, req.getSamplePhotoUrl2());
				pstmt.setString(6, req.getReqPrice());
				pstmt.setString(7, req.getReqDate());
			}else{
				pstmt.setString(2, req.getPtnCode());
				pstmt.setString(3, req.getConstAddress());
				pstmt.setString(4, req.getReqContent());
				pstmt.setString(5, req.getSamplePhotoUrl1());
				pstmt.setString(6, req.getSamplePhotoUrl2());
				pstmt.setString(7, req.getReqPrice());
				//pstmt.setString(8, req.getReqDate());
			}
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public int insetRequestInfo(Connection con, RequestVo req, String key) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		if(req.getReqType().equals("경매")){
			query = "INSERT INTO REQ_INFO VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}else{
			query = "INSERT INTO REQ_INFO VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		try {
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, key);
			pstmt.setInt(2, req.getAcreage());
			pstmt.setString(3, req.getVeranda());
			pstmt.setString(4, req.getElectrics());
			pstmt.setString(5, req.getFlooring());
			pstmt.setString(6, req.getPapering());
			pstmt.setString(7, req.getCoating());
			pstmt.setString(8, req.getCeiling());
			pstmt.setString(9, req.getMiddleDoor());
			pstmt.setString(10, req.getWindow());
			pstmt.setString(11, req.getTile());
			pstmt.setString(12, req.getBathroom());
			pstmt.setString(13, req.getKitchen());
			pstmt.setString(14, req.getCleaning());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(con);
		}
		System.out.println("result : " + result);
		return result;
	}
	public String selectRequest(Connection con, RequestVo req){	
		String result = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		query = " SELECT REQ_CODE  FROM REQUEST "
				+ "WHERE substr(REQ_CODE, 4) = (SELECT MAX(SUBSTR(REQ_CODE, 4)) FROM REQUEST WHERE REQ_TYPE = ?)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, req.getReqType());
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getString("REQ_CODE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateRequest(Connection con, RequestVo req) {
		// 주의 : Request 테이블 뿐만 아니라 Req_Info 테이블도 수정해줘야 함.
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		query = "UPDATE REQUEST "
				+ "SET CONST_ADDRESS = ?, REQUEST = ?, SAMPLE_PHOTO1 = ?, SAMPLE_PHOTO2 = ?, REQ_PRICE = ? ,REQ_DATE = ? "
				+ "WHERE REQ_CODE = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, req.getConstAddress());
			pstmt.setString(2, req.getReqContent());
			pstmt.setString(3, req.getSamplePhotoUrl1());
			pstmt.setString(4, req.getSamplePhotoUrl2());
			pstmt.setString(6, req.getReqPrice());
			pstmt.setString(7, req.getReqDate());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int deleteRequest(Connection con, String reqCode, RequestVo req) {
		int result = -1;
		Statement stmt = null;
		String query = "";
		try {
			stmt=con.createStatement();
			if(req.getReqType().equals("경매")){
			query = "UPDATE REQUEST "
					+ "SET REQ_CHECK = 경매취소 "
					+ "WHERE REQ_CODE = " + reqCode;
			}else{
			query = "UPDATE REQUEST "
					+ "SET REQ_CHECK = 견적신청취소 "
					+ "WHERE REQ_CODE = " + reqCode;				
			}
			result = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(stmt);
		}
		return result;
	}


	public RequestVo selectRequestDetail(Connection con, String reqCode) {
		RequestVo req = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		query = "SELECT REQ_CODE, REQ_TYPE, USER_CODE, PTN_CODE, CONST_ADDRESS, REQUEST, SAMPLE_PHOTO1, SAMPLE_PHOTO2, "
				+ "REQ_PRICE, REQ_DATE, ACT_STARTDATE, ACT_ENDDATE, REQ_CHECK,CONST_STARTDATE, CONST_ENDDATE, ACREAGE, "
				+ "VERANDA, ELECTRIC, FLOORING, PAPERING, COATING, CEILING, MIDDLEDOOR, WINDOW, TILE, BATHROOM, "
				+ "KITCHEN, CLEANING, USER_NAME, USER_NNAME, PTN_NAME "
				+ "FROM REQ_VIEW "
				+ "WHERE REQ_CODE = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, reqCode);
			rs = pstmt.executeQuery();
			while(rs.next()){
				req = new RequestVo();
				req.setReqCode(reqCode);
				req.setReqType(rs.getString("REQ_TYPE"));
				req.setConstAddress(rs.getString("CONST_ADDRESS"));
				req.setReqContent(rs.getString("REQUEST"));
				req.setSamplePhotoUrl1(rs.getString("SAMPLE_PHOTO1"));
				req.setSamplePhotoUrl2(rs.getString("SAMPLE_PHOTO2"));
				req.setReqPrice(rs.getString("REQ_PRICE"));
				req.setReqDate(rs.getString("REQ_DATE"));
				req.setReqCheck(rs.getString("REQ_CHECK"));
				req.setAcreage(rs.getInt("ACREAGE"));
				req.setVeranda(rs.getString("VERANDA"));
				req.setElectrics(rs.getString("ELECTRIC"));
				req.setFlooring(rs.getString("FLOORING"));
				req.setPapering(rs.getString("PAPERING"));
				req.setCoating(rs.getString("COATING"));
				req.setCeiling(rs.getString("CEILING"));
				req.setMiddleDoor(rs.getString("MIDDLEDOOR"));
				req.setWindow(rs.getString("WINDOW"));
				req.setTile(rs.getString("TILE"));
				req.setBathroom(rs.getString("BATHROOM"));
				req.setKitchen(rs.getString("KITCHEN"));
				req.setCleaning(rs.getString("CLEANING"));
				req.setUserName(rs.getString("USER_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		System.out.println("req_code : " + reqCode);
		return req;
	}

	public ArrayList<RequestVo> selectRequestList(Connection con, MemberVo member, String reqType, String reqCheck) {
		// TODO Auto-generated method stub
		String query = null;
		ArrayList<RequestVo> list = null; // reqType이 뭐냐에 따라 RequestVo, AuctionVo, ConstructionVo가 들어감
		// 1. member.getMemberType()이 고객이냐 업체냐에 따라 query가 달라짐
		// 2. reqType, reqCheck가 all이면 해당 범주 내 모든 리스트, 아니면 해당하는 값으로 조건을 넣어준다.		
		
		return list;
	}

	public ArrayList<RequestVo> selectRequestListByKeyword(Connection con, MemberVo member, String reqType, String reqCheck,
			String condition, String keyword) {
		// TODO Auto-generated method stub
		String query = null;
		ArrayList<RequestVo> list = null; // reqType이 뭐냐에 따라 RequestVo, AuctionVo, ConstructionVo가 들어감
		// 1. member.getMemberType()이 고객이냐 업체냐에 따라 query가 달라짐
		// 2. reqType, reqCheck가 all이면 해당 범주 내 모든 리스트, 아니면 해당하는 값으로 조건을 넣어준다.
		// 3. 검색 조건을 맞춰줌
		
		return list;
	}

	public int updateReqCheck(Connection con, String reqCode, String reqCheck) {
		// TODO Auto-generated method stub
		// reqCheck 수정된 사항 반영
		int result = 0;
		
		return result;
	}

	public int selectRequestTotalCount(Connection con) {
		int result = -1;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "";	
		try {
			stmt = con.createStatement();
			query = "SELECT COUNT(*) as listCount "
					+ "FROM REQ_LIST "
					+ "WHERE REQ_TYPE = '경매' ";
			rs = stmt.executeQuery(query);
			
			while(rs.next()){
				result = rs.getInt("listCount");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		return result;
	}

	public ArrayList<RequestVo> selectRequestListPage(Connection con, int currentPage, int limit) {
		ArrayList<RequestVo> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		try {
			query = "SELECT * "
					+ "FROM (SELECT ROWNUM AS RNUM, R.* "
					+ "FROM (SELECT REQ_CODE, REQ_TYPE, USER_NAME, CONST_ADDRESS, REQ_DATE, REQ_CHECK "
					+ 			"FROM ACT_LIST "
					+ 			"ORDER BY REQ_DATE	ASC) R)"
					+ "WHERE RNUM BETWEEN ? AND ? ";
			
			pstmt = con.prepareStatement(query);
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs=pstmt.executeQuery();
			
			list = new ArrayList<RequestVo>();
			RequestVo temp = null;
			while(rs.next()){
				temp = new RequestVo();
				temp.setReqCode(rs.getString("req_code"));
				temp.setReqType(rs.getString("req_type"));
				temp.setUserName(rs.getString("user_name"));
				temp.setConstAddress(rs.getString("const_address"));
				temp.setReqDate(rs.getString("req_date"));
				temp.setReqCheck(rs.getString("req_check"));
				
				list.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int insertRequestAuction(Connection con, RequestVo req) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		query = "INSERT INTO AUCTION_PTN VALUES(?,?,?,DEFAULT)";
		try {
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, req.getPtnCode());
			pstmt.setString(2, req.getReqCode());
			pstmt.setInt(3, req.getPtnPay());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<RequestVo> selectChoicePtnList(Connection con, String reqCode) {
		ArrayList<RequestVo> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		try {
			query = "SELECT ACTPTN_NAME, PTN_PAY, PTN_CODE, REQ_CODE "
					+ "FROM ACTPTN_VIEW "
					+ "WHERE REQ_CODE = ? "
					+ "ORDER BY PTN_PAY ASC";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, reqCode);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RequestVo>();
			RequestVo temp = null;
			while(rs.next()){
				temp = new RequestVo();
				temp.setPtnName(rs.getString("ACTPTN_NAME"));
				temp.setPtnPay(rs.getInt("PTN_PAY"));
				temp.setPtnCode(rs.getString("PTN_CODE"));
				temp.setReqCode(rs.getString("REQ_CODE"));
				
				list.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}

		return list;
	}

	public int insertChoicePtn(Connection con, RequestVo req) {
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		query = "UPDATE AUCTION_PTN "
				+ "SET BID_CHECK = 'Y' "
				+ "WHERE REQ_CODE = ? AND PTN_CODE = ?";
		try {
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, req.getReqCode());
			pstmt.setString(2, req.getPtnCode());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<RequestVo> selectMyRequestListPage(Connection con, int currentPage, int limit, String mCode) {
		ArrayList<RequestVo> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		try {
			query = "SELECT * "
					+ "FROM (SELECT ROWNUM AS RNUM, R.* "
					+ "FROM (SELECT REQ_TYPE, MEMBER_NAME, CONST_ADDRESS, REQ_CHECK,REQ_CODE,PTN_CODE "
					+ 			"FROM REQUEST "
					+ 			"LEFT OUTER JOIN MEMBER ON (MEMBER_CODE = PTN_CODE) "
					+ 			"WHERE USER_CODE = ? "
					+ 			"ORDER BY REQ_DATE	ASC) R)"
					+ "WHERE RNUM BETWEEN ? AND ? ";
			
			pstmt = con.prepareStatement(query);
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			
			pstmt.setString(1, mCode);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rs=pstmt.executeQuery();
			
			list = new ArrayList<RequestVo>();
			RequestVo temp = null;
			while(rs.next()){
				temp = new RequestVo();
				temp.setReqType(rs.getString("req_type"));
				temp.setUserName(rs.getString("member_name"));
				temp.setConstAddress(rs.getString("const_address"));
				temp.setReqCheck(rs.getString("req_check"));
				temp.setReqCode(rs.getString("req_code"));
				temp.setPtnCode(rs.getString("PTN_CODE"));
				
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}


}
