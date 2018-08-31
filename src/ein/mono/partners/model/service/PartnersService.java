package ein.mono.partners.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.member.model.vo.MemberVo;
import ein.mono.partners.model.dao.PartnersDao;
import ein.mono.partners.model.vo.PartnersVo;

public class PartnersService {

	private PartnersDao partnerDao = new PartnersDao();
	
	public ArrayList<PartnersVo> selectPartnersListMain(String type) { // 업체 지정 메인 페이지 한정
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		ArrayList<PartnersVo> list = partnerDao.selectPartnersListMain(con, type);
		JDBCTemplate.close(con); // 자원 반납
		return list;
	}

	public ArrayList<PartnersVo> selectPartnersList(String category, int currentPage, int limit) { // 업체 지정 중 기준에 따라 페이지만큼 떼서 가져옴
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		ArrayList<PartnersVo> list = partnerDao.selectPartnersList(con, category, currentPage, limit);
		JDBCTemplate.close(con); // 자원 반납
		return list;
	}

	public ArrayList<PartnersVo> selectPartnersListByKeyword(String condition, String keyword, int currentPage, int limit) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		ArrayList<PartnersVo> list = partnerDao.selectPartnersListByKeyword(con, condition, keyword, currentPage, limit);
		JDBCTemplate.close(con); // 자원 반납
		return list;
	}
	
	public int selectPartnersTotalCount(String category) {
		// TODO Auto-generated method stub
		//커넥션 생성
		Connection con = JDBCTemplate.getConnection();
		//비지니스 로직 호출
		int listCount = partnerDao.selectPartnersTotalCount(con, category);
		//자원 반납(close)
		JDBCTemplate.close(con);
		//결과 반환
		return listCount;
	}
	
	public int selectPartnersTotalCount(String condition, String keyword) {
		// TODO Auto-generated method stub
		//커넥션 생성
		Connection con = JDBCTemplate.getConnection();
		//비지니스 로직 호출
		int listCount = partnerDao.selectPartnersTotalCount(con, condition, keyword);
		//자원 반납(close)
		JDBCTemplate.close(con);
		//결과 반환
		return listCount;
	}

	public ArrayList<PartnersVo> selectJoinRequestPartnersList(int currentPage, int limit) {
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<PartnersVo> list = new PartnersDao().selectJoinRequestPartnersList(con, currentPage, limit);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public int selectJoinRequestPartenersTotalCount() {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new PartnersDao().selectJoinRequestPtnTotalCount(con);
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public PartnersVo selectPartner(String user_code) {
		Connection con = JDBCTemplate.getConnection();
		
		PartnersVo partner = new PartnersDao().selectPartner(con, user_code);
		
		JDBCTemplate.close(con);
		
		return partner;
	}
	
	public PartnersVo selectPartnerAdmin(String user_code) {
		Connection con = JDBCTemplate.getConnection();
		
		PartnersVo partner = new PartnersDao().selectPartnerAdmin(con, user_code);
		
		JDBCTemplate.close(con);
		
		return partner;
	}

	public int updatePtnCheck(PartnersVo ptn, int flag) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new PartnersDao().updatePtnCheck(con, ptn);
		
		if(0 < result && flag == 1) {
			int result2 = new PartnersDao().updateMemberRank(con, ptn);
			if(0 < result2) {
				JDBCTemplate.commit(con);
			} else {
				JDBCTemplate.rollback(con);
			}
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public boolean hasFavPtn(String memberCode, String partnerCode) {
		// TODO Auto-generated method stub
		//커넥션 생성
		Connection con = JDBCTemplate.getConnection();
		//비지니스 로직 호출
		int favPtnCount = partnerDao.selectFavPtnCount(con, memberCode, partnerCode);
		//자원 반납(close)
		JDBCTemplate.close(con);
		//결과 반환
		if(favPtnCount == 1) return true;
		else return false;
	}

	public int updatePartner(PartnersVo ptnProfile) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		int result = partnerDao.updatePartner(con, ptnProfile);
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}
	
	public String selectPartnerName(String partnerCode) {
		Connection con = JDBCTemplate.getConnection();
		String name = partnerDao.selectPartnerName(con, partnerCode);
		JDBCTemplate.close(con);
		return name;
	}

}
