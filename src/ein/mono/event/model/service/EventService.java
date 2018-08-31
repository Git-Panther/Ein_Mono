package ein.mono.event.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import ein.mono.board.model.dao.PostDao;
import ein.mono.common.JDBCTemplate;
import ein.mono.event.model.dao.EventDao;
import ein.mono.event.model.vo.EventVo;

public class EventService {

	public ArrayList<EventVo> selectEventListAdmin(int currentPage, int limit) {
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<EventVo> list = new EventDao().selectEventListAdmin(con, currentPage, limit);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public int selectEventTotalCount() {
		Connection con = JDBCTemplate.getConnection();

		int result = new EventDao().selectEventTotalCount(con);
		JDBCTemplate.close(con);
		
		return result;
	}

	public int updateEventLevel(int level, String eCode) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new EventDao().updateEventLevel(con, level, eCode);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int updateSubEventLevel(String[] eCodeList) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new EventDao().updateSubEventLevel(con, eCodeList);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public ArrayList<EventVo> selectMainEventList() {
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<EventVo> list = new EventDao().selecMainEventList(con);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public int insertEvent(String post_code, String ptn_code, EventVo event) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new EventDao().insertEvent(con, post_code, ptn_code, event);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public ArrayList<EventVo> selectEventList(int currentPage, int limit) {
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<EventVo> list = new EventDao().selectEventList(con, currentPage, limit);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public ArrayList<EventVo> selectEventList() {
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<EventVo> list = new EventDao().selectEventList(con);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public EventVo selectEvent(String postCode) {
		Connection con = JDBCTemplate.getConnection();
		
		EventVo event = new EventDao().selectEvent(con, postCode);
		
		JDBCTemplate.close(con);
		
		return event;
	}

}
