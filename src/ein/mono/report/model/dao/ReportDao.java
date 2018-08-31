package ein.mono.report.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.report.model.vo.ReportVo;

public class ReportDao {

	public int reportUpdate(Connection con, String stopdate, int reportNo) {
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		switch(stopdate) {
		
		case "1":
			query = "UPDATE REPORT\r\n" + 
					"SET ADMIN_CHECK = 'Y', BAN_STARTDATE = NULL, BAN_ENDDATE = NULL\r\n" + 
					"WHERE REPORT_CODE = ?";//제제안함
			break;
			
		case "2":
			query = "UPDATE REPORT\r\n" + 
					"SET ADMIN_CHECK = 'Y', BAN_STARTDATE = SYSDATE, BAN_ENDDATE = SYSDATE+3\r\n" + 
					"WHERE REPORT_CODE = ?";//3일
			break;
			
		case "3":
			query = "UPDATE REPORT\r\n" + 
					"SET ADMIN_CHECK = 'Y', BAN_STARTDATE = SYSDATE, BAN_ENDDATE = SYSDATE+7\r\n" + 
					"WHERE REPORT_CODE = ?";//7일
			break;
			
		case "4":
			query = "UPDATE REPORT\r\n" + 
					"SET ADMIN_CHECK = 'Y', BAN_STARTDATE = SYSDATE, BAN_ENDDATE = SYSDATE+30\r\n" + 
					"WHERE REPORT_CODE = ?";//30일
			break;
			
		case "5":
			query = "UPDATE REPORT\r\n" + 
					"SET ADMIN_CHECK = 'Y', BAN_STARTDATE = SYSDATE, BAN_ENDDATE = SYSDATE+9000\r\n" + 
					"WHERE REPORT_CODE = ?";//영구정지
			break;
		}
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, reportNo);
			
			result = pstmt.executeUpdate();
			//작성
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
			return result;
	}

	public ArrayList<ReportVo> selectReportList(Connection con, int currentPage, int limit) {
		
		ArrayList<ReportVo> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		try {
			query = "SELECT REPORT_CODE, REPORTER_CODE, REPORTED_CODE, POST_CODE, REPLY_CODE, REPORT_REASON,ADMIN_CHECK, REPORT_DATE, MEMBER_ID\r\n" + 
					"FROM (SELECT ROWNUM RNUM,P.*\r\n" + 
					"FROM(SELECT REPORT_CODE, REPORTER_CODE, REPORTED_CODE, POST_CODE, REPLY_CODE, REPORT_REASON,ADMIN_CHECK, REPORT_DATE, MEMBER_ID\r\n" + 
					"FROM REPORT R, MEMBER M\r\n" + 
					"WHERE R.REPORTED_CODE = M.MEMBER_CODE\r\n" + 
					"AND ADMIN_CHECK = 'F'\r\n" + 
					"ORDER BY REPORT_CODE DESC) P)\r\n" + 
					"WHERE RNUM BETWEEN ? AND ?";
						
			pstmt = con.prepareStatement(query);
			
			int stratRow = (currentPage-1)*limit+1;  
			int endRow = stratRow+limit-1;
			
			pstmt.setInt(1, stratRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			
			
			list = new ArrayList<ReportVo>();
			
			ReportVo temp = null;
			
			while(rs.next()) {
				temp = new ReportVo();
				temp.setReportCode(rs.getString("REPORT_CODE"));
				temp.setReporterCode(rs.getString("REPORTER_CODE"));
				temp.setReportedCode(rs.getString("REPORTED_CODE"));
				temp.setPostCode(rs.getString("POST_CODE"));
				temp.setReplyCode(rs.getString("REPLY_CODE"));
				temp.setReportReason(rs.getString("REPORT_REASON"));
				temp.setAdminCheck(rs.getString("ADMIN_CHECK"));
				temp.setReportDate(rs.getDate("REPORT_DATE"));
				temp.setMemberId(rs.getString("MEMBER_ID"));
				
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

	public int reportInsert(Connection con, ReportVo reportvo) {
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "INSERT INTO REPORT\r\n" + 
				"VALUES(SEQ_RCODE.NEXTVAL,?,?,?,?,?,DEFAULT,DEFAULT,NULL,NULL)";
					//FK에 해당되는 PK값이 설정되지 않아서 실행되지않음 데이터 넣고실행하기
		
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, reportvo.getReporterCode());
			pstmt.setString(2, reportvo.getReportedCode());
			pstmt.setString(3, reportvo.getPostCode());
			pstmt.setString(4, reportvo.getReplyCode());
			pstmt.setString(5, reportvo.getReportReason());
			
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
					"FROM REPORT\r\n" + 
					"WHERE ADMIN_CHECK != 'Y'";
			
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

	public ReportVo detailReport(Connection con, String reportNo) {
		ReportVo report = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		query = "SELECT REPORT_CODE, REPORTER_CODE, REPORTED_CODE, POST_CODE, REPLY_CODE, REPORT_REASON,ADMIN_CHECK, REPORT_DATE, MEMBER_ID\r\n" + 
				"FROM REPORT R, MEMBER M\r\n" + 
				"WHERE R.REPORTED_CODE = M.MEMBER_CODE\r\n" + 
				"AND REPORT_CODE = ?";
		
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, reportNo);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					report = new ReportVo();
					report.setReportCode(reportNo);
					report.setReporterCode(rs.getString("REPORTER_CODE"));
					report.setReportedCode(rs.getString("REPORTED_CODE"));
					report.setPostCode(rs.getString("POST_CODE"));
					report.setReplyCode(rs.getString("REPLY_CODE"));
					report.setReportReason(rs.getString("REPORT_REASON"));
					report.setAdminCheck(rs.getString("ADMIN_CHECK"));
					report.setReportDate(rs.getDate("REPORT_DATE"));
					report.setMemberId(rs.getString("MEMBER_ID"));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(rs);
				JDBCTemplate.close(pstmt);
			}
		return report;
	}

	public ReportVo findDate(Connection con, String mCode) {
		ReportVo report = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		query = "SELECT BAN_STARTDATE, BAN_ENDDATE, SYSDATE\r\n" + 
				"FROM REPORT R, MEMBER M\r\n" + 
				"WHERE R.REPORTED_CODE = M.MEMBER_CODE\r\n" + 
				"AND ADMIN_CHECK = 'Y'\r\n" + 
				"AND M.MEMBER_CODE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, mCode);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				report = new ReportVo();
				report.setBanstartDate(rs.getDate("BAN_STARTDATE"));
				report.setBanendDate(rs.getDate("BAN_ENDDATE"));
				report.setSysdate(rs.getDate("SYSDATE"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return report;
	}

}
