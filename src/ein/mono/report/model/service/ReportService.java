package ein.mono.report.model.service;

import java.sql.Connection;
import java.util.ArrayList;



import ein.mono.common.JDBCTemplate;

import ein.mono.report.model.dao.ReportDao;
import ein.mono.report.model.vo.ReportVo;

public class ReportService {

	public int updateReport(String stopdate, int reportNo) {
		Connection con = JDBCTemplate.getConnection();
		int result = new ReportDao().reportUpdate(con, stopdate, reportNo);//관리자가 n에서 y로 바꾸기
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public ArrayList<ReportVo> selectReportList(int currentPage, int limit ) {//신고리스트 조회
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<ReportVo> list = new ReportDao().selectReportList(con, currentPage, limit );
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public int reportInsert(ReportVo reportvo) {//회원이 신고하기
		Connection con = JDBCTemplate.getConnection();
		
	int result = new ReportDao().reportInsert(con, reportvo);
	
	if(0<result) {
			JDBCTemplate.commit(con);
	}else {
		JDBCTemplate.rollback(con);
	}
		//자원반납
	JDBCTemplate.close(con);
		//결과 반환
	
	return result;
	}



	public int selectreportTotalCount() {//페이지관련
		Connection con = JDBCTemplate.getConnection(); 
		int listCount = new ReportDao().selectreportTotalCount(con);
		JDBCTemplate.close(con);
		return listCount;
	}

	public ReportVo detailReport(String reportNo) {
		Connection con = JDBCTemplate.getConnection();
		
		ReportVo report = new ReportDao().detailReport(con, reportNo);
		
		JDBCTemplate.close(con);
		
		return report;
	}

	public ReportVo findDate(String mCode) {
		Connection con = JDBCTemplate.getConnection();
		ReportVo report = new ReportDao().findDate(con, mCode);
		JDBCTemplate.close(con);
		return report;
	}
}