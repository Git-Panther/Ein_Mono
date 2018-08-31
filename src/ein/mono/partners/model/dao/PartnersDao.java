package ein.mono.partners.model.dao;

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
import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;
import ein.mono.partners.model.vo.PartnersVo;
import ein.mono.request.model.dao.RequestDao;

public class PartnersDao {

	private Properties prop = new Properties();
	
	public PartnersDao() {
		String filename = RequestDao.class.getResource("/partners/partners_sql.properties").getPath();
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
	
	public ArrayList<PartnersVo> selectPartnersListMain(Connection con, String type) {
		// TODO Auto-generated method stub
		ArrayList<PartnersVo> list = null;
		// 쿼리는 우수 업체 TOP 3, 내 지역에 가까운 업체 이름 오름차순 TOP 3, 전체 리스트 중 이름 오름차순 TOP 3(스타일 TOP 3 할수도) 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			
			//2. 쿼리 작성
			if(type.equals("best")) {
				query = prop.getProperty("selectPartnersListBest");
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, "우수업체");
			} else if(type.equals("all")) {
				query = prop.getProperty("selectPartnersListAll");
				pstmt = con.prepareStatement(query);
			}
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing)
			list = new ArrayList<PartnersVo>();
			PartnersVo temp = null;
			
			while(rs.next()){
				temp = new PartnersVo();
				temp.setPartnerCode(rs.getString("PTN_CODE"));
				temp.setMemberName(rs.getString("PTN_NAME"));
				temp.setMemberRank(rs.getString("PTN_RANK"));
				temp.setPartnerLocation(rs.getString("PTN_LOCATION"));
				temp.setMetascore(rs.getDouble("METASCORE"));
				temp.setPtnPhotos(rs.getString("PTN_MAIN_PHOTO"));
				temp.setPartnerStyles(rs.getString("PTN_STYLES"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return list;
	}
	
	

	public ArrayList<PartnersVo> selectPartnersList(Connection con, String category, int currentPage, int limit) {
		// TODO Auto-generated method stub
		ArrayList<PartnersVo> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			query = prop.getProperty("selectPartnersList");
			switch(category){
			case "우수업체":
				query = query.replaceAll("C1", "WHERE PTN_RANK = '우수업체' ORDER BY METASCORE DESC");
				break;
			default:
				query = query.replaceAll("C1", "ORDER BY MEMBER_JDATE DESC");
			}	
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			int startRow = (currentPage - 1) * limit + 1; 
			int endRow = startRow + limit - 1;	
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing)
			list = new ArrayList<PartnersVo>();
			PartnersVo temp = null;
			while(rs.next()){
				temp = new PartnersVo();
				temp.setPartnerCode(rs.getString("PTN_CODE"));
				temp.setMemberName(rs.getString("PTN_NAME"));
				temp.setMemberRank(rs.getString("PTN_RANK"));
				temp.setPartnerLocation(rs.getString("PTN_LOCATION"));
				temp.setMetascore(rs.getDouble("METASCORE"));
				temp.setPtnPhotos(rs.getString("PTN_MAIN_PHOTO"));
				temp.setPartnerStyles(rs.getString("PTN_STYLES"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return list;
	}

	public ArrayList<PartnersVo> selectPartnersListByKeyword(Connection con, String condition, String keyword, int currentPage, int limit) {
		// TODO Auto-generated method stub
		ArrayList<PartnersVo> list = null;
		// 쿼리는 우수 업체 TOP 3, 내 지역에 가까운 업체 이름 오름차순 TOP 3, 전체 리스트 중 이름 오름차순 TOP 3(스타일 TOP 3 할수도) 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			query = prop.getProperty("selectPartnersListByKeyword");
			query = query.replaceAll("C1", condition); // 지역, 스타일, 업체명 정도만 하는걸로
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성 : 이제 키워드로 상세 정보, 인트로 찾을 수 있다.
			pstmt.setString(1, "%" + keyword.toLowerCase() + "%");
			int startRow = (currentPage - 1) * limit + 1; 
			int endRow = startRow + limit - 1;	
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing)
			list = new ArrayList<PartnersVo>();
			PartnersVo temp = null;
			while(rs.next()){
				temp = new PartnersVo();
				temp.setPartnerCode(rs.getString("PTN_CODE"));
				temp.setMemberName(rs.getString("PTN_NAME"));
				temp.setMemberRank(rs.getString("PTN_RANK"));
				temp.setPartnerLocation(rs.getString("PTN_LOCATION"));
				temp.setMetascore(rs.getDouble("METASCORE"));
				temp.setPtnPhotos(rs.getString("PTN_MAIN_PHOTO"));
				temp.setPartnerStyles(rs.getString("PTN_STYLES"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return list;
	}
	
	public ArrayList<PartnersVo> selectJoinRequestPartnersList(Connection con, int currentPage, int limit) {
		ArrayList<PartnersVo> list = new ArrayList<PartnersVo>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = null;
		
		try {
			query = "SELECT MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG, PTN_CODE, PTN_OWNER, PTN_LICENSE, PTN_LOGO, PTN_LOCATION, PTN_STYLES, PTN_CHECK, WEEKDAYS_START, WEEKDAYS_END, WEEKEND_START, WEEKEND_END, PTN_INTRO FROM (SELECT ROWNUM AS RNUM, MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG, PTN_CODE, PTN_OWNER, PTN_LICENSE, PTN_LOGO, PTN_LOCATION, PTN_STYLES, PTN_CHECK, WEEKDAYS_START, WEEKDAYS_END, WEEKEND_START, WEEKEND_END, PTN_INTRO FROM (SELECT MEMBER_RANK, MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL, MEMBER_NNAME, MEMBER_JDATE, DELFLAG, PTN_CODE, PTN_OWNER, PTN_LICENSE, PTN_LOGO, PTN_LOCATION, PTN_STYLES, PTN_CHECK, WEEKDAYS_START, WEEKDAYS_END, WEEKEND_START, WEEKEND_END, PTN_INTRO FROM MEMBER M, PARTNER P WHERE M.MEMBER_CODE = P.PTN_CODE AND MEMBER_RANK = '미승인업체' AND PTN_CHECK = 'N' ORDER BY TO_NUMBER(SUBSTR(PTN_CODE, 2)) ASC)) WHERE RNUM BETWEEN ? AND ?";
			
			pstmt = con.prepareStatement(query);
			
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			PartnersVo temp = null;
			while(rs.next()){
				temp = new PartnersVo();
				
				String ptn_code = rs.getString("ptn_code");
				temp.setPartnerCode(ptn_code);
				temp.setPartnerOwner(rs.getString("ptn_owner"));
				temp.setPartnerCheck(rs.getString("ptn_check"));
				temp.setPartnerLicense(rs.getString("ptn_license"));
				temp.setPartnerLogo(rs.getString("ptn_logo"));
				temp.setPartnerLocation(rs.getString("ptn_location"));
				temp.setPartnerStyles(rs.getString("ptn_styles"));
				temp.setPartnerIntro(rs.getString("ptn_intro"));
				temp.setWeekdaysEnd(rs.getString("weekdays_start"));
				temp.setWeekdaysEnd(rs.getString("weekdays_end"));
				temp.setWeekdaysStart(rs.getString("weekend_start"));
				temp.setWeekdaysEnd(rs.getString("weekend_end"));
				temp.setMemberName(rs.getString("member_name"));
				temp.setMemberRank(rs.getString("member_rank"));
				temp.setMemberId(rs.getString("member_id"));
				temp.setMemberEmail(rs.getString("member_email"));
				temp.setMemberAddress(rs.getString("member_address"));
				temp.setMemberTel(rs.getString("member_tel"));
				temp.setMemberNname(rs.getString("member_nname"));
				temp.setMemberJdate(rs.getDate("member_jdate"));
				
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
	
	public int selectJoinRequestPtnTotalCount(Connection con) {
		int result = -1;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "";
		
		try {
			stmt = con.createStatement();
			
			query = "SELECT COUNT(*) AS LISTCOUNT FROM PARTNER WHERE PTN_CHECK = 'N'";
			
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				result = rs.getInt("listcount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		
		return result;
	}

	public PartnersVo selectPartner(Connection con, String ptnCode) {
		PartnersVo ptnProfile = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			query = prop.getProperty("selectPartner");
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, ptnCode);
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing). 참고로 한 가지의 결과만 나온다.
			while(rs.next()){
				ptnProfile = new PartnersVo();
				ptnProfile.setPartnerLogo(rs.getString("PTN_LOGO"));
				ptnProfile.setPartnerCode(rs.getString("PTN_CODE"));
				ptnProfile.setMetascore(rs.getDouble("METASCORE"));
				ptnProfile.setFavorites(rs.getInt("FAVCOUNT"));
				ptnProfile.setMemberName(rs.getString("PTN_NAME"));
				ptnProfile.setMemberRank(rs.getString("PTN_RANK"));
				ptnProfile.setPartnerLocation(rs.getString("PTN_LOCATION"));
				ptnProfile.setPartnerStyles(rs.getString("PTN_STYLES"));
				ptnProfile.setPartnerIntro(rs.getString("PTN_INTRO"));
				ptnProfile.setWeekdaysStart(rs.getString("WEEKDAYS_START"));
				ptnProfile.setWeekdaysEnd(rs.getString("WEEKDAYS_END"));
				ptnProfile.setWeekendStart(rs.getString("WEEKEND_START"));
				ptnProfile.setWeekendEnd(rs.getString("WEEKEND_END"));
				ptnProfile.setPtnContacts(rs.getString("PTN_CONTACTS"));
				ptnProfile.setPtnUpdates(rs.getString("PTN_UPDATES"));
				ptnProfile.setPtnPhotos(rs.getString("PTN_PHOTO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return ptnProfile;
	}
	public PartnersVo selectPartnerAdmin(Connection con, String ptnCode) {
		PartnersVo ptnProfile = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			query = prop.getProperty("selectPartnerAdmin");
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, ptnCode);
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing). 참고로 한 가지의 결과만 나온다.
			while(rs.next()){
				ptnProfile = new PartnersVo();
				ptnProfile.setPartnerLogo(rs.getString("PTN_LOGO"));
				ptnProfile.setPartnerCode(rs.getString("PTN_CODE"));
				ptnProfile.setMetascore(rs.getDouble("METASCORE"));
				ptnProfile.setFavorites(rs.getInt("FAVCOUNT"));
				ptnProfile.setMemberName(rs.getString("PTN_NAME"));
				ptnProfile.setMemberRank(rs.getString("PTN_RANK"));
				ptnProfile.setPartnerLocation(rs.getString("PTN_LOCATION"));
				ptnProfile.setPartnerStyles(rs.getString("PTN_STYLES"));
				ptnProfile.setPartnerIntro(rs.getString("PTN_INTRO"));
				ptnProfile.setWeekdaysStart(rs.getString("WEEKDAYS_START"));
				ptnProfile.setWeekdaysEnd(rs.getString("WEEKDAYS_END"));
				ptnProfile.setWeekendStart(rs.getString("WEEKEND_START"));
				ptnProfile.setWeekendEnd(rs.getString("WEEKEND_END"));
				ptnProfile.setPtnContacts(rs.getString("PTN_CONTACTS"));
				ptnProfile.setPtnUpdates(rs.getString("PTN_UPDATES"));
				ptnProfile.setPtnPhotos(rs.getString("PTN_PHOTO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return ptnProfile;
	}
	

	public int updatePtnCheck(Connection con, PartnersVo ptn) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "UPDATE PARTNER SET PTN_CHECK = 'Y' WHERE PTN_CODE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, ptn.getPartnerCode());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}		
		
		return result;
	}
	
	public int updateMemberRank(Connection con, PartnersVo ptn) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = "UPDATE MEMBER SET MEMBER_RANK = '일반업체' WHERE MEMBER_CODE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, ptn.getPartnerCode());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}		
		
		return result;
	}

	public int selectPartnersTotalCount(Connection con, String category) {
		// TODO Auto-generated method stub
		int partnerCount = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			query = prop.getProperty("selectPartnersTotalCount");
			switch(category){
			case "우수업체":
				query = query.replaceAll("C1", "WHERE PTN_RANK = '우수업체'");
				break;
			default:
				query = query.replaceAll("C1", "");
			}
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()){
				partnerCount = rs.getInt("PARTNERCOUNT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}		
		return partnerCount;
	}

	public int selectPartnersTotalCount(Connection con, String condition, String keyword) {
		// TODO Auto-generated method stub
		int partnerCount = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			query = prop.getProperty("selectPartnersTotalCount");
			query = query.replaceAll("C1", "WHERE " + condition.toUpperCase() + " LIKE ?"); // 지역, 스타일, 업체명 정도만 하는걸로
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				partnerCount = rs.getInt("PARTNERCOUNT");
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}		
		return partnerCount;
	}

	public int selectFavPtnCount(Connection con, String memberCode, String partnerCode) {
		// TODO Auto-generated method stub
		int favPtnCount = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			query = prop.getProperty("selectFavPtnCount");
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, memberCode);
			pstmt.setString(2, partnerCode);
			pstmt.setString(3, "즐겨찾기");
			rs = pstmt.executeQuery();
			while(rs.next()){
				favPtnCount = rs.getInt("FAVPTNCOUNT");
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}		
		return favPtnCount;
	}

	public int updatePartner(Connection con, PartnersVo ptnProfile) {
		// TODO Auto-generated method stub
		int result = 0;
		PreparedStatement pstmt = null;
		String query = null;
		try {
			query = prop.getProperty("updatePartner");
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, ptnProfile.getPartnerLocation());
			pstmt.setString(2, ptnProfile.getPartnerStyles());
			pstmt.setString(3, ptnProfile.getWeekdaysStart());
			pstmt.setString(4, ptnProfile.getWeekdaysEnd());
			pstmt.setString(5, ptnProfile.getWeekendStart());
			pstmt.setString(6, ptnProfile.getWeekendEnd());
			pstmt.setString(7, ptnProfile.getPtnContacts());
			pstmt.setString(8, ptnProfile.getPtnUpdates());
			pstmt.setString(9, ptnProfile.getPartnerIntro());
			pstmt.setString(10, ptnProfile.getPartnerLogo());
			pstmt.setString(11, ptnProfile.getPtnPhotos());
			pstmt.setString(12, ptnProfile.getPartnerCode());	
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);
		}		
		return result;
	}

	public String selectPartnerName(Connection con, String partnerCode) {
		// TODO Auto-generated method stub
		String name = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			query = prop.getProperty("selectPartnerName");
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, partnerCode);
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing). 참고로 한 가지의 결과만 나온다.
			while(rs.next()){
				name = rs.getString("PTN_NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return name;
	}
	
}
