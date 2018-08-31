package ein.mono.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.notice.model.dao.NoticeDao;
import ein.mono.notice.model.vo.NoticeVo;
import ein.mono.report.model.dao.ReportDao;
import ein.mono.report.model.vo.ReportVo;


public class NoticeService {
	public ArrayList<NoticeVo> getNoticeList(){
		return null;
	}
	public NoticeVo getNotice(int noticeNo){
		return null;
	}
	public int writeNotice(NoticeVo notice) {
		Connection con = JDBCTemplate.getConnection();
		int result = new NoticeDao().writeNotice(con, notice);
		if(0<result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		JDBCTemplate.close(con);
		return result;
	}
	public NoticeVo getNoticeFormData(String noticeNo) {
		Connection con = JDBCTemplate.getConnection();		
		NoticeVo notice = new NoticeDao().selectNotice(con, noticeNo);								
		JDBCTemplate.close(con);
		return notice;
	}
	public int modifyNotice(NoticeVo notice){
		return 0;
	}
	public int removeNotice(int noticeNo) {
		return 0;
	}
	public int selectreportTotalCount() {
		Connection con = JDBCTemplate.getConnection(); 
		int listCount = new NoticeDao().selectreportTotalCount(con);
		JDBCTemplate.close(con);
		return listCount;
	}
	public ArrayList<NoticeVo> selectNoticeList(int currentPage, int limit) {
Connection con = JDBCTemplate.getConnection();
		
		ArrayList<NoticeVo> list = new NoticeDao().selectReportList(con, currentPage, limit );
		
		JDBCTemplate.close(con);
		
		return list;
	}
	public NoticeVo detailNotice(String noticeNo) {
		Connection con = JDBCTemplate.getConnection();
		
		NoticeVo notice = new NoticeDao().detailNotice(con, noticeNo);
		
		if(null != notice) {
			int result = new NoticeDao().updateNoticeCount(con, noticeNo);
			
			if(0<result) {
				JDBCTemplate.commit(con);
			}else {
				JDBCTemplate.rollback(con);
			}
		}
		
		JDBCTemplate.close(con);	
		return notice;
	}
	public int deleteNotice(String noticeNo) {
		Connection con = JDBCTemplate.getConnection();
		int result = new NoticeDao().deleteNotice(con, noticeNo);
		
		if(0<result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		JDBCTemplate.close(con);
		
		return result;
	}
	public int rewriteNotice(NoticeVo notice, String noticeno) {
		Connection con = JDBCTemplate.getConnection();
		int result = new NoticeDao().rewirteNotice(con, notice, noticeno);
		if(0<result) {
			JDBCTemplate.commit(con);
		}else {
		JDBCTemplate.rollback(con);
		}
		return result;
	}

}
