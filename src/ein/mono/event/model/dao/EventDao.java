package ein.mono.event.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.event.model.vo.EventVo;

public class EventDao {

	public ArrayList<EventVo> selectEventListAdmin(Connection con, int currentPage, int limit) {
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet rs = null;
		ArrayList<EventVo> list = new ArrayList<EventVo>();
		try {
			query = "SELECT POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, DELFLAG, PTN_CODE, ADV_BANNER, ADV_PHOTO, ADV_STARTDATE, ADV_ENDDATE, MEMBER_NAME "
					+ "FROM (SELECT ROWNUM AS RNUM, POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, DELFLAG, PTN_CODE, ADV_BANNER, ADV_PHOTO, ADV_STARTDATE, ADV_ENDDATE, MEMBER_NAME "
					+ "			FROM (SELECT A.POST_CODE, P.POST_TYPE, P.WRITER_CODE, P.TITLE, P.CONTENT, P.VIEWS_COUNT, P.WRITTEN_DATE, P.DELFLAG, A.PTN_CODE, A.ADV_BANNER, A.ADV_PHOTO, A.ADV_STARTDATE, A.ADV_ENDDATE, M.MEMBER_NAME "
					+ "						FROM POST P, ADVERTISING A, MEMBER M  "
					+ "						WHERE P.POST_CODE = A.POST_CODE "
					+ "						AND A.PTN_CODE = M.MEMBER_CODE "
					+ "						AND P.POST_TYPE = 'ADV' "
					+ "						AND P.DELFLAG = 'N' "
					+ "						ORDER BY TO_NUMBER(SUBSTR(P.POST_CODE, 4)) DESC)) "
					+ "WHERE RNUM BETWEEN ? AND ?";

			pstmt = con.prepareStatement(query);
			
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			// 결과 처리(select -> resultSet)
			EventVo temp = null;
			while(rs.next()) {
				temp = new EventVo();
				
				temp.setPost_code(rs.getString("post_code"));
				temp.setPost_type(rs.getString("post_type"));
				temp.setAdvBanner(rs.getString("adv_banner"));
				temp.setAdvPhoto(rs.getString("adv_photo"));
				temp.setAdvStartDate(rs.getDate("adv_startdate").toString());
				temp.setAdvEndDate(rs.getDate("adv_enddate").toString());
				temp.setWriter_code(rs.getString("writer_code"));
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setViews_count(rs.getInt("views_count"));
				temp.setWritten_date(rs.getDate("written_date"));
				temp.setDel_flag(rs.getString("delflag"));
				temp.setMember_name(rs.getString("member_name"));
				
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close 처리
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int selectEventTotalCount(Connection con) {
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT COUNT(*) AS COUNT "
				+ "FROM ADVERTISING A, POST P "
				+ "WHERE A.POST_CODE = P.POST_CODE";
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(stmt);
		}
		
		return result;
	}

	public int updateEventLevel(Connection con, int level, String eCode) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "UPDATE ADVERTISING SET ADV_LEVEL = ? "
				+ "WHERE POST_CODE = ?";
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, level);
			pstmt.setString(2, eCode);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int updateSubEventLevel(Connection con, String[] eCodeList) {
		int result = 0;
		Statement stmt = null;
		String query = "UPDATE ADVERTISING SET ADV_LEVEL = 5 "
				+ "WHERE POST_CODE NOT IN ('" + eCodeList[0] + "', '" + eCodeList[1] + "', '" + eCodeList[2] + "', '" + eCodeList[3] + "')";
		
		try {
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(stmt);
		}
		
		return result;
	}

	public ArrayList<EventVo> selecMainEventList(Connection con) {
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet rs = null;
		ArrayList<EventVo> list = new ArrayList<EventVo>();
		
		try {
			query = "SELECT A.POST_CODE, P.POST_TYPE, P.WRITER_CODE, P.TITLE, P.CONTENT, P.VIEWS_COUNT, P.WRITTEN_DATE, P.DELFLAG, A.PTN_CODE, A.ADV_BANNER, A.ADV_PHOTO, A.ADV_STARTDATE, A.ADV_ENDDATE, A.ADV_LEVEL, M.MEMBER_NAME "
					+ "FROM POST P, ADVERTISING A, MEMBER M  "
					+ "WHERE P.POST_CODE = A.POST_CODE "
					+ "	AND A.PTN_CODE = M.MEMBER_CODE "
					+ "	AND P.POST_TYPE = 'ADV' "
					+ "	AND P.DELFLAG = 'N' "
					+ "ORDER BY A.ADV_LEVEL";

			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			EventVo temp = null;
			while(rs.next()) {
				temp = new EventVo();
				
				temp.setPost_code(rs.getString("post_code"));
				temp.setPost_type(rs.getString("post_type"));
				temp.setAdvBanner(rs.getString("adv_banner"));
				temp.setAdvPhoto(rs.getString("adv_photo"));
				temp.setAdvStartDate(rs.getDate("adv_startdate").toString());
				temp.setAdvEndDate(rs.getDate("adv_enddate").toString());
				temp.setWriter_code(rs.getString("writer_code"));
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setViews_count(rs.getInt("views_count"));
				temp.setWritten_date(rs.getDate("written_date"));
				temp.setDel_flag(rs.getString("delflag"));
				temp.setMember_name(rs.getString("member_name"));
				temp.setAdvLevel(rs.getInt("adv_level"));
				
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close 처리
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int insertEvent(Connection con, String post_code, String ptn_code, EventVo event) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "INSERT INTO ADVERTISING VALUES(?, ?, ?, ?, TO_DATE(?, 'yyyyMMdd'), TO_DATE(?, 'yyyyMMdd'), DEFAULT)";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, post_code);
			pstmt.setString(2, ptn_code);
			pstmt.setString(3, event.getAdvBanner());
			pstmt.setString(4, event.getAdvPhoto());
			pstmt.setString(5, event.getAdvStartDate());
			pstmt.setString(6, event.getAdvEndDate());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return result;
	}

	public ArrayList<EventVo> selectEventList(Connection con, int currentPage, int limit) {
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet rs = null;
		ArrayList<EventVo> list = new ArrayList<EventVo>();
		try {
			query = "SELECT POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, DELFLAG, PTN_CODE, ADV_BANNER, ADV_PHOTO, ADV_STARTDATE, ADV_ENDDATE, MEMBER_NAME "
					+ "FROM (SELECT ROWNUM AS RNUM, POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, DELFLAG, PTN_CODE, ADV_BANNER, ADV_PHOTO, ADV_STARTDATE, ADV_ENDDATE, MEMBER_NAME "
					+ "			FROM (SELECT A.POST_CODE, P.POST_TYPE, P.WRITER_CODE, P.TITLE, P.CONTENT, P.VIEWS_COUNT, P.WRITTEN_DATE, P.DELFLAG, A.PTN_CODE, A.ADV_BANNER, A.ADV_PHOTO, A.ADV_STARTDATE, A.ADV_ENDDATE, M.MEMBER_NAME "
					+ "						FROM POST P, ADVERTISING A, MEMBER M  "
					+ "						WHERE P.POST_CODE = A.POST_CODE "
					+ "						AND A.PTN_CODE = M.MEMBER_CODE "
					+ "						AND P.POST_TYPE = 'ADV' "
					+ "						AND P.DELFLAG = 'N' "
					+ "						ORDER BY TO_NUMBER(SUBSTR(P.POST_CODE, 4)) DESC)) "
					+ "WHERE RNUM BETWEEN ? AND ?";

			pstmt = con.prepareStatement(query);
			
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			// 결과 처리(select -> resultSet)
			EventVo temp = null;
			while(rs.next()) {
				temp = new EventVo();
				
				temp.setPost_code(rs.getString("post_code"));
				temp.setPost_type(rs.getString("post_type"));
				temp.setAdvBanner(rs.getString("adv_banner"));
				temp.setAdvPhoto(rs.getString("adv_photo"));
				temp.setAdvStartDate(rs.getDate("adv_startdate").toString());
				temp.setAdvEndDate(rs.getDate("adv_enddate").toString());
				temp.setWriter_code(rs.getString("writer_code"));
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setViews_count(rs.getInt("views_count"));
				temp.setWritten_date(rs.getDate("written_date"));
				temp.setDel_flag(rs.getString("delflag"));
				temp.setMember_name(rs.getString("member_name"));
				
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close 처리
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public ArrayList<EventVo> selectEventList(Connection con) {
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet rs = null;
		ArrayList<EventVo> list = new ArrayList<EventVo>();
		try {
			query = "SELECT A.POST_CODE, P.POST_TYPE, P.WRITER_CODE, P.TITLE, P.CONTENT, P.VIEWS_COUNT, P.WRITTEN_DATE, P.DELFLAG, A.PTN_CODE, A.ADV_BANNER, A.ADV_PHOTO, A.ADV_STARTDATE, A.ADV_ENDDATE, M.MEMBER_NAME "
					+ "FROM POST P, ADVERTISING A, MEMBER M  "
					+ "WHERE P.POST_CODE = A.POST_CODE "
					+ "AND A.PTN_CODE = M.MEMBER_CODE "
					+ "AND P.POST_TYPE = 'ADV' "
					+ "AND P.DELFLAG = 'N' "
					+ "ORDER BY TO_NUMBER(SUBSTR(P.POST_CODE, 4)) DESC";

			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			// 결과 처리(select -> resultSet)
			EventVo temp = null;
			while(rs.next()) {
				temp = new EventVo();
				
				temp.setPost_code(rs.getString("post_code"));
				temp.setPost_type(rs.getString("post_type"));
				temp.setAdvBanner(rs.getString("adv_banner"));
				temp.setAdvPhoto(rs.getString("adv_photo"));
				temp.setAdvStartDate(rs.getDate("adv_startdate").toString());
				temp.setAdvEndDate(rs.getDate("adv_enddate").toString());
				temp.setWriter_code(rs.getString("writer_code"));
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setViews_count(rs.getInt("views_count"));
				temp.setWritten_date(rs.getDate("written_date"));
				temp.setDel_flag(rs.getString("delflag"));
				temp.setMember_name(rs.getString("member_name"));
				
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close 처리
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public EventVo selectEvent(Connection con, String postCode) {
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet rs = null;
		EventVo event = new EventVo();
		try {
			query = "SELECT A.POST_CODE, P.POST_TYPE, P.WRITER_CODE, P.TITLE, P.CONTENT, P.VIEWS_COUNT, P.WRITTEN_DATE, P.DELFLAG, A.PTN_CODE, A.ADV_BANNER, A.ADV_PHOTO, A.ADV_STARTDATE, A.ADV_ENDDATE, M.MEMBER_NAME "
					+ "FROM POST P, ADVERTISING A, MEMBER M  "
					+ "WHERE P.POST_CODE = A.POST_CODE "
					+ "AND A.PTN_CODE = M.MEMBER_CODE "
					+ "AND P.POST_TYPE = 'ADV' "
					+ "AND P.POST_CODE = ? "
					+ "AND P.DELFLAG = 'N'";

			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, postCode);
			
			rs = pstmt.executeQuery();
			
			// 결과 처리(select -> resultSet)
			while(rs.next()) {
				event.setPost_code(rs.getString("post_code"));
				event.setPost_type(rs.getString("post_type"));
				event.setAdvBanner(rs.getString("adv_banner"));
				event.setAdvPhoto(rs.getString("adv_photo"));
				event.setAdvStartDate(rs.getDate("adv_startdate").toString());
				event.setAdvEndDate(rs.getDate("adv_enddate").toString());
				event.setWriter_code(rs.getString("writer_code"));
				event.setTitle(rs.getString("title"));
				event.setContent(rs.getString("content"));
				event.setViews_count(rs.getInt("views_count"));
				event.setWritten_date(rs.getDate("written_date"));
				event.setDel_flag(rs.getString("delflag"));
				event.setMember_name(rs.getString("member_name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close 처리
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return event;
	}

}
