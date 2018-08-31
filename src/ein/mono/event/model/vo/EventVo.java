package ein.mono.event.model.vo;

import ein.mono.board.model.vo.PostVo;

public class EventVo extends PostVo{
	private String postCode;
	private String pntCode; // 관리자가 임의로 업체 지정
	private String advBanner;
	private String advPhoto;
	private String advStartDate;
	private String advEndDate;
	private int advLevel;
	
	public EventVo() {
		super();
	}

	public EventVo(String postCode, String pntCode, String advBanner, String advPhoto, String advStartDate,
			String advEndDate) {
		super();
		this.postCode = postCode;
		this.pntCode = pntCode;
		this.advBanner = advBanner;
		this.advPhoto = advPhoto;
		this.advStartDate = advStartDate;
		this.advEndDate = advEndDate;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPntCode() {
		return pntCode;
	}

	public void setPntCode(String pntCode) {
		this.pntCode = pntCode;
	}

	public String getAdvBanner() {
		return advBanner;
	}

	public void setAdvBanner(String advBanner) {
		this.advBanner = advBanner;
	}

	public String getAdvPhoto() {
		return advPhoto;
	}

	public void setAdvPhoto(String advPhoto) {
		this.advPhoto = advPhoto;
	}

	public String getAdvStartDate() {
		return advStartDate;
	}

	public void setAdvStartDate(String advStartDate) {
		this.advStartDate = advStartDate;
	}

	public String getAdvEndDate() {
		return advEndDate;
	}

	public void setAdvEndDate(String advEndDate) {
		this.advEndDate = advEndDate;
	}

	public int getAdvLevel() {
		return advLevel;
	}

	public void setAdvLevel(int advLevel) {
		this.advLevel = advLevel;
	}

	@Override
	public String toString() {
		return "EventVo [postCode=" + postCode + ", pntCode=" + pntCode + ", advBanner=" + advBanner + ", advPhoto="
				+ advPhoto + ", advStartDate=" + advStartDate + ", advEndDate=" + advEndDate + ", toString()="
				+ super.toString() + "]";
	}
	
}
