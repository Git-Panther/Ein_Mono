package ein.mono.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;
import ein.mono.partners.model.vo.PartnersVo;

public class MemberDao {
	
	public int joinMember(Connection con, MemberVo member, String mRank) {
		//회원가입
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		switch(mRank) {
		
		case "1":
			query = "INSERT INTO MEMBER "+
							"VALUES('B'||SEQ_MEMBER_CODE.NEXTVAL, '일반회원', ?,?,?,?,?,?,?,DEFAULT,DEFAULT, ?, 0)";
			break;
			
		case "2":
			query = "INSERT INTO MEMBER "+
					"VALUES('C'||SEQ_MEMBER_CODE.NEXTVAL, '미승인업체', ?,?,?,?,?,?,?,DEFAULT,DEFAULT, ?, 0)";
	break;
		}
		
		
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberEmail());
			pstmt.setString(5, member.getMemberAddress());
			pstmt.setString(6, member.getMemberTel());
			pstmt.setString(7, member.getMemberNname());
			pstmt.setString(8, member.getLicence());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public MemberVo loginMember(Connection con, String id) {
		MemberVo result = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
			
			String query = "SELECT *\r\n" + 
					"FROM MEMBER\r\n" + 
					"WHERE MEMBER_ID = '"+id+"'";
			
			rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				
				
				result = new MemberVo();
				result.setMemberCode(rs.getString("MEMBER_CODE"));
				result.setMemberRank(rs.getString("MEMBER_RANK"));
				result.setMemberId(id);
				result.setMemberPwd(rs.getString("MEMBER_PWD"));
				result.setMemberName(rs.getString("MEMBER_NAME"));
				result.setMemberEmail(rs.getString("MEMBER_EMAIL"));
				result.setMemberAddress(rs.getString("MEMBER_ADDRESS"));
				result.setMemberTel(rs.getString("MEMBER_TEL"));
				result.setMemberNname(rs.getString("MEMBER_NNAME"));
				result.setMemberJdate(rs.getDate("MEMBER_JDATE"));
				result.setDelflag(rs.getString("DELFLAG"));
				
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
	

	public int updateMember(Connection con, MemberVo member) {
		
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "UPDATE MEMBER SET "+
		"MEMBER_PWD = ? WHERE MEMBER_ID = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, member.getMemberPwd());
			pstmt.setString(2, member.getMemberId());
			
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;//회원정보수정
	}

	public int deleteMember(Connection con, MemberVo member) {
		
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "UPDATE MEMBER SET DELFLAG = 'Y' WHERE MEMBER_ID = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;//회원삭제
		
		
	}

	public MemberVo selectMember(Connection con, String member_code) {
		MemberVo result = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = "SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, BAN_STARTDATE, BAN_ENDDATE "
								+ "FROM MEMBER M, REPORT R "
								+ "WHERE M.MEMBER_CODE = R.REPORTED_CODE(+) " 
								+ "AND MEMBER_CODE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, member_code);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				result = new MemberVo();
				result.setMemberCode(member_code);
				result.setMemberId(rs.getString("member_id"));
				result.setMemberRank(rs.getString("member_rank"));
				result.setMemberName(rs.getString("member_name"));
				result.setMemberEmail(rs.getString("member_email"));
				result.setMemberAddress(rs.getString("member_address"));
				result.setMemberTel(rs.getString("member_tel"));
				result.setMemberNname(rs.getString("member_nname"));
				result.setMemberJdate(rs.getDate("member_jdate"));	
				result.setBan_startdate(rs.getDate("ban_startdate"));
				result.setBan_enddate(rs.getDate("ban_enddate"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int selectMemberTotalCount(Connection con, int option) {
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT COUNT(*) AS COUNT FROM MEMBER WHERE MEMBER_CODE LIKE ";
		switch(option){
		case 1:
			query += "'B%'";
			break;
		case 2:
			query += "'C%'";
			break;
		}
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		
		return result;
	}

	public ArrayList<MemberVo> selectMemberList(Connection con, int currentPage, int limit, int selectOption) {
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = null;
		
		try {
			switch(selectOption) {
			case 1:
				query = "SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT ROWNUM AS RNUM, MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM MEMBER M ORDER BY SUBSTR(MEMBER_CODE, 1, 1) ASC, TO_NUMBER(SUBSTR(MEMBER_CODE, 2)) ASC)) WHERE RNUM BETWEEN ? AND ?";
				break;
			case 2:
				query = "SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT ROWNUM AS RNUM, MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM MEMBER M WHERE MEMBER_CODE LIKE 'B%' ORDER BY SUBSTR(MEMBER_CODE, 1, 1) ASC, TO_NUMBER(SUBSTR(MEMBER_CODE, 2)) ASC)) WHERE RNUM BETWEEN ? AND ?";
				break;
			case 3:
				query = "SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT ROWNUM AS RNUM, MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM MEMBER M WHERE MEMBER_CODE LIKE 'C%' ORDER BY SUBSTR(MEMBER_CODE, 1, 1) ASC, TO_NUMBER(SUBSTR(MEMBER_CODE, 2)) ASC)) WHERE RNUM BETWEEN ? AND ?";
				break;
			}

			pstmt = con.prepareStatement(query);
			
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			MemberVo temp = null;
			while(rs.next()){
				temp = new MemberVo();
				
				temp.setMemberCode(rs.getString("member_code"));
				temp.setMemberRank(rs.getString("member_rank"));
				temp.setMemberId(rs.getString("member_id"));
				temp.setMemberName(rs.getString("member_name"));
				temp.setMemberAddress(rs.getString("member_address"));
				temp.setMemberTel(rs.getString("member_tel"));
				temp.setMemberEmail(rs.getString("member_email"));
				temp.setMemberNname(rs.getString("member_nname"));
				temp.setMemberJdate(rs.getDate("member_jdate"));
				temp.setDelflag(rs.getString("delflag"));
				
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public int updateMemberRank(Connection con, String member_code, String member_rank) {
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "UPDATE MEMBER SET MEMBER_RANK = ? WHERE MEMBER_CODE = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, member_rank);
			pstmt.setString(2, member_code);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int selectMemberTotalCount(Connection con, int condition, String searchContent) {
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT COUNT(*) AS COUNT FROM MEMBER WHERE ";
		
		switch(condition){
		case 1:
			query += "MEMBER_ID LIKE '%" + searchContent + "%'";
			break;
		case 2:
			query += "MEMBER_NAME LIKE '%" + searchContent + "%'";
			break;
		}
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		
		return result;
	}

	public ArrayList<MemberVo> selectMemberList(Connection con, int currentPage, int limit, int condition,
			String searchContent) {
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = null;
		
		try {
			switch(condition) {
			case 1:
				query = "SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT ROWNUM AS RNUM, MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM MEMBER M WHERE MEMBER_ID LIKE '%" + searchContent + "%' ORDER BY SUBSTR(MEMBER_CODE, 1, 1) ASC, TO_NUMBER(SUBSTR(MEMBER_CODE, 2)) ASC)) WHERE RNUM BETWEEN ? AND ?";
				break;
			case 2:
				query = "SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT ROWNUM AS RNUM, MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM (SELECT MEMBER_CODE, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG FROM MEMBER M WHERE MEMBER_NAME LIKE '%" + searchContent + "%' ORDER BY SUBSTR(MEMBER_CODE, 1, 1) ASC, TO_NUMBER(SUBSTR(MEMBER_CODE, 2)) ASC)) WHERE RNUM BETWEEN ? AND ?";
				break;
			}

			pstmt = con.prepareStatement(query);
			
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			MemberVo temp = null;
			while(rs.next()){
				temp = new MemberVo();
				
				temp.setMemberCode(rs.getString("member_code"));
				temp.setMemberRank(rs.getString("member_rank"));
				temp.setMemberId(rs.getString("member_id"));
				temp.setMemberName(rs.getString("member_name"));
				temp.setMemberAddress(rs.getString("member_address"));
				temp.setMemberTel(rs.getString("member_tel"));
				temp.setMemberEmail(rs.getString("member_email"));
				temp.setMemberNname(rs.getString("member_nname"));
				temp.setMemberJdate(rs.getDate("member_jdate"));
				temp.setDelflag(rs.getString("delflag"));
				
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public String selectMemberCode(Connection con, String ptnName) {
		String ptnCode = "";
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT MEMBER_CODE FROM MEMBER "
				+ "WHERE MEMBER_NAME = '" + ptnName +"'";
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				ptnCode = rs.getString("member_code");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		
		return ptnCode;
	}


	public int pwdChangeMember(Connection con,String id, String memberPwd) {
		int pwdChange = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "UPDATE MEMBER SET MEMBER_PWD = ? WHERE MEMBER_ID = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memberPwd);
			pstmt.setString(2, id);
			
			pwdChange = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		
		return pwdChange;
	}

	public MemberVo findcode(Connection con, MemberVo member) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		MemberVo findcode = null;
		
		query = "SELECT MEMBER_CODE, LICENCE FROM MEMBER WHERE MEMBER_ID = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				findcode = new MemberVo();
				findcode.setMemberCode(rs.getString("MEMBER_CODE"));
				findcode.setLicence(rs.getString("LICENCE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
	
		
		return findcode;
	}

	public int insertPtn(Connection con, MemberVo findcode) {
		int ptnResult = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "INSERT INTO PARTNER VALUES(?,'유지상',DEFAULT,?,NULL,'D','평택','모던','NULL',NULL,NULL,NULL,'d',NULL,NULL)";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, findcode.getMemberCode());
			pstmt.setString(2, findcode.getLicence());
			
			ptnResult = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
	
	
		return ptnResult;
	}

	public MemberVo findid(Connection con, String email) {
		MemberVo memberfindid = null;
		Statement stmt = null;
		ResultSet rs = null;
		
	
			try {
				stmt = con.createStatement();
				String query = "SELECT MEMBER_ID\r\n" + 
						"FROM MEMBER\r\n" + 
						"WHERE MEMBER_EMAIL = '"+email+"'";
				
				rs = stmt.executeQuery(query);
				
				if(rs.next()) {
					memberfindid = new MemberVo();
					memberfindid.setMemberId(rs.getString("MEMBER_ID"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(rs);
				JDBCTemplate.close(stmt);					
			}
			
			
			
	
		return memberfindid;

}

	public MemberVo findpwd(Connection con, String email, String id) {
		MemberVo memberfindpwd = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
			String query = "SELECT MEMBER_PWD FROM MEMBER WHERE MEMBER_ID = '"+id+"' AND MEMBER_EMAIL = '"+email+"'";
			
			rs = stmt.executeQuery(query);
			if(rs.next()) {
				memberfindpwd = new MemberVo();
				memberfindpwd.setMemberPwd(rs.getString("MEMBER_PWD"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);					
		}
		
		return memberfindpwd;
	}

	public int updateEmailCheck(Connection con, String userEmail) {
		int result = -1;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "UPDATE MEMBER SET EMAIL_CHECK = 'Y' WHERE MEMBER_EMAIL = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userEmail);
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
