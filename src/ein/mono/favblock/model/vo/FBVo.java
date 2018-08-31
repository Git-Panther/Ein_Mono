package ein.mono.favblock.model.vo;

import ein.mono.member.model.vo.MemberVo;

public class FBVo extends MemberVo{
	private String targetCode; // 관계의 대상이 된 계정의 코드
	private String fbType; // 관계유형
	public String getTargetCode() {
		return targetCode;
	}
	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
	public String getFbType() {
		return fbType;
	}
	public void setFbType(String fbType) {
		this.fbType = fbType;
	}
	
	
}
