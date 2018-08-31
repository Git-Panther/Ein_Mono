package ein.mono.profile.model.dao;

import java.sql.Connection;
import java.util.ArrayList;

import ein.mono.partners.model.vo.PartnersVo;
import ein.mono.profile.model.vo.ProfileVo;

public class ProfileDao {

	
	
	public ArrayList<ProfileVo> selectProfileList(Connection con, int pCode) {
		
		ArrayList<ProfileVo> list = null;
		return list;
	}

	public int updatePartner(Connection con, PartnersVo ptnProfile) {
		int result = 0;
		
		
		
		return result;
	}

}
