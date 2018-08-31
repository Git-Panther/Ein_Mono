package ein.mono.favblock.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import ein.mono.common.JDBCTemplate;
import ein.mono.favblock.model.vo.FBVo;
import ein.mono.member.model.vo.MemberVo;
import ein.mono.request.model.dao.RequestDao;

public class FBDao {

	private Properties prop = new Properties();
	
	public FBDao() {
		String filename = RequestDao.class.getResource("/favor_block/fb_sql.properties").getPath();
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

	public ArrayList<FBVo> selectLBList(Connection con, String userCode) {//리스트 조회는 페이징 처리
		
		ArrayList<FBVo> list = null;
		return list;
		
	}

	public int deleteFB(Connection con, String id) {
		int result = 0;
		return result;
	}

	public int insertFavorBlock(Connection con, FBVo fb) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = null;
		PreparedStatement pstmt = null;
		try {
			query = prop.getProperty("insertFavorBlock");
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, fb.getMemberCode());
			pstmt.setString(2, fb.getTargetCode());
			pstmt.setString(3, fb.getFbType());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);
		}		
		return result;
	}

	public int deleteFavorBlock(Connection con, FBVo fb) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = null;
		PreparedStatement pstmt = null;
		try {
			query = prop.getProperty("deleteFavorBlock");
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, fb.getMemberCode());
			pstmt.setString(2, fb.getTargetCode());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);
		}		
		return result;
	}

	public int selectFavorCount(Connection con, String partnerCode) {
		// TODO Auto-generated method stub
		int favorCount = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			query = prop.getProperty("selectFavorCount");
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, partnerCode);
			rs = pstmt.executeQuery();
			while(rs.next()){
				favorCount = rs.getInt("FAVOR_COUNT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}		
		return favorCount;
	}



	public int insertFav(Connection con, FBVo LB) {
		int result = 0;
		return result;
	}

	public int updateBlock(Connection con, FBVo LB) {
		int result = 0;
		return result;
	}

	public ArrayList<MemberVo> selectFBList(Connection con, String member_code, String fb_type) {//리스트 조회는 페이징 처리
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = "SELECT MEMBER_CODE, MEMBER_NAME FROM MEMBER "
								+ "WHERE MEMBER_CODE IN(SELECT TARGET_CODE "
								+ "FROM FAVOR_BLOCK, MEMBER "
								+ "WHERE MEMBER_CODE = USER_CODE "
								+ "AND MEMBER_CODE = ? "
								+ "AND FB_TYPE = ?)";
						
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, member_code);
			pstmt.setString(2, fb_type);
			
			rs = pstmt.executeQuery();
			MemberVo temp = null;
			while(rs.next()) {
				temp = new MemberVo();
				temp.setMemberCode(rs.getString("member_code"));
				temp.setMemberName(rs.getString("member_name"));
				
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

	public int deleteFB(Connection con, String member_code, String target_code) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "DELETE FROM FAVOR_BLOCK WHERE USER_CODE = ? AND TARGET_CODE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, member_code);
			pstmt.setString(2, target_code);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public ArrayList<FBVo> selectFBList2(Connection con,String mCode,String fb_type){
		ArrayList<FBVo> list = new ArrayList<FBVo>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = "SELECT FB_TYPE , TARGET_CODE,M1.MEMBER_NAME AS MYID,M2.MEMBER_NAME AS TARGETID "
				+"FROM FAVOR_BLOCK F "
				+"JOIN MEMBER M1 ON(F.USER_CODE = M1.MEMBER_CODE) "
				+"JOIN MEMBER M2 ON(F.USER_CODE = M2.MEMBER_CODE) "
				+"WHERE F.USER_CODE = ? AND F.FB_TYPE=? ";
						
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, mCode);
			pstmt.setString(2, fb_type);
			
			rs = pstmt.executeQuery();
			FBVo temp = null;
			while(rs.next()) {
				temp = new FBVo();
				temp.setTargetCode(rs.getString("TARGET_CODE"));
				temp.setFbType(rs.getString("FB_TYPE"));
				temp.setMyId(rs.getString("MYID"));
				temp.setTargetId(rs.getString("TARGETID"));
				
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
}
