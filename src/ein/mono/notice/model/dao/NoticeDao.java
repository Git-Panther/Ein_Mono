package ein.mono.notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.notice.model.vo.NoticeVo;
import ein.mono.report.model.vo.ReportVo;

public class NoticeDao {
	
	public NoticeVo selectNotice(Connection con, String noticeNo) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		NoticeVo notice = null;
		
		query = "SELECT POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, MEMBER_NAME " + 
				"FROM POST P, MEMBER M " + 
				"WHERE P.WRITER_CODE = M.MEMBER_CODE " + 
				"AND POST_CODE = ? ";
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, noticeNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				notice = new NoticeVo();
				notice.setPostCode(noticeNo);
				notice.setPostType(rs.getString("POST_TYPE"));
				notice.setWriterCode(rs.getString("WRITER_CODE"));
				notice.setTitle(rs.getString("TITLE"));
				notice.setContent(rs.getString("CONTENT"));
				notice.setViewsCount(rs.getInt("VIEWS_COUNT"));
				notice.setWrittenDate(rs.getDate("WRITTEN_DATE"));
				notice.setMemberName(rs.getString("MEMBER_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return notice;
	}

	public int updateNoticeCount(Connection con, String noticeNo) {
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "UPDATE POST SET VIEWS_COUNT = VIEWS_COUNT+1 WHERE POST_CODE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, noticeNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
		


	}
	
	
	
	public int selectreportTotalCount(Connection con) {
		int result = -1;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "";
		
		
		try {
			stmt = con.createStatement();
			query = "SELECT COUNT(*) AS LISTCOUNT\r\n" + 
					"FROM POST\r\n" + 
					"WHERE DELFLAG != 'Y' AND POST_TYPE = 'NOT' ";
			
			rs=  stmt.executeQuery(query);
			
			while(rs.next()) {
				result = rs.getInt("listcount");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		return result;
	}
	public ArrayList<NoticeVo> selectReportList(Connection con, int currentPage, int limit) {
		ArrayList<NoticeVo> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		try {
			query = "SELECT POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, DELFLAG, MEMBER_NAME\r\n" + 
					"FROM (SELECT ROWNUM RNUM,N.*\r\n" + 
					"FROM(SELECT POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, P.DELFLAG, MEMBER_NAME\r\n" + 
					"FROM POST P, MEMBER M\r\n" + 
					"WHERE P.WRITER_CODE = M.MEMBER_CODE\r\n" + 
					"AND P.DELFLAG = 'N' AND POST_TYPE = 'NOT' " + 
					"ORDER BY POST_CODE DESC) N)\r\n" + 
					"WHERE RNUM BETWEEN ?AND ?";
						
			pstmt = con.prepareStatement(query);
			
			int stratRow = (currentPage-1)*limit+1;  
			int endRow = stratRow+limit-1;
			
			pstmt.setInt(1, stratRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			
			
			list = new ArrayList<NoticeVo>();
			
			NoticeVo temp = null;
			
			while(rs.next()) {
				temp = new NoticeVo();
				temp.setPostCode(rs.getString("POST_CODE"));
				temp.setPostType(rs.getString("POST_TYPE"));
				temp.setWriterCode(rs.getString("WRITER_CODE"));
				temp.setTitle(rs.getString("TITLE"));
				temp.setContent(rs.getString("CONTENT"));
				temp.setViewsCount(rs.getInt("VIEWS_COUNT"));
				temp.setWrittenDate(rs.getDate("WRITTEN_DATE"));
				temp.setDelflag(rs.getString("DELFLAG"));
				temp.setMemberName(rs.getString("MEMBER_NAME"));
				
				
				list.add(temp);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		
		return list;
	}
	public NoticeVo detailNotice(Connection con, String noticeNo) {
		NoticeVo notice = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		query = "SELECT POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, MEMBER_NAME\r\n" + 
				"FROM POST P, MEMBER M\r\n" + 
				"WHERE P.WRITER_CODE = M.MEMBER_CODE\r\n" + 
				"AND POST_CODE = ? ";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, noticeNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				notice = new NoticeVo();
				notice.setPostCode(noticeNo);
				notice.setPostType(rs.getString("POST_TYPE"));
				notice.setWriterCode(rs.getString("WRITER_CODE"));
				notice.setTitle(rs.getString("TITLE"));
				notice.setContent(rs.getString("CONTENT"));
				notice.setViewsCount(rs.getInt("VIEWS_COUNT"));
				notice.setWrittenDate(rs.getDate("WRITTEN_DATE"));
				notice.setMemberName(rs.getString("MEMBER_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
	}
	
		return notice;
	}
	public int writeNotice(Connection con, NoticeVo notice) {

		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "INSERT INTO POST VALUES(SEQ_PNO.NEXTVAL,'NOT',?,?,?,DEFAULT,DEFAULT,DEFAULT)";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, notice.getWriterCode());
			pstmt.setString(2, notice.getTitle());
			pstmt.setString(3, notice.getContent());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	public int deleteNotice(Connection con, String noticeNo) {
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = " UPDATE POST SET DELFLAG = 'Y' WHERE POST_CODE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, noticeNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	public int rewirteNotice(Connection con, NoticeVo notice, String noticeno) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "UPDATE POST SET TITLE = ?, CONTENT = ? WHERE  POST_CODE = ?";
		
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1,notice.getTitle());
			pstmt.setString(2,notice.getContent());
			pstmt.setString(3,noticeno);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
	
		
		return result;
	}
}
