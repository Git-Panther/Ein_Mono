package ein.mono.request.model.vo;

import java.sql.Date;

/**
 * @author xosle
 *
 */
public class RequestVo { // 신청 공통 사항
	private String reqCode; // 신청 코드
	private String userCode; // 신청자의 코드 *
	private String userName; // 신청자의 이름
	private String ptnCode; // 시공을 담당할 업체 *
	private String ptnName; // 시공을 담당할 업체의 이름
	private String constAddress; // 시공지 주소 *
	private String reqContent; // 상세한 시공 요청 사항 *
	private String samplePhotoUrl1; // 희망하는 시공 사진(2매까지) *
	private String samplePhotoUrl2;
	private String reqPrice; // 경매희망가 *
	private String reqDate; // 시공희망일 *
	private Date startDate; // 신청한 날짜. 경매는 끝나는 날짜도 있다.
	private String reqCheck; // 주문 상태를 표시(경매중 등등)
	private int acreage; // 평수 *
	private String veranda; // 베란다 여부 *
	private String electrics; // 전기 조명 여부 *
	private String flooring; // 바닥재 *
	private String papering; // 도배 *
	private String coating; // 도장재 *
	//private String innerConst; // 내부 시공 항목 체크사항들(천장, 중문, 창호, 타일, 욕실, 주방) *
	private String ceiling;
	private String middleDoor;
	private String window;
	private String tile;
	private String bathroom;
	private String kitchen;
	private String cleaning; //기타-입주 청소 *
	private String reqType; // 주문 분류 *
	private int ptnPay;
	private String bidCheck; 
	
	
	public String getBidCheck() {
		return bidCheck;
	}


	public void setBidCheck(String bidCheck) {
		this.bidCheck = bidCheck;
	}


	public RequestVo() {}
	
	
	public RequestVo(String reqCode) {
		this.reqCode = reqCode;
	}
	
	
	
	
	public RequestVo(String ptnCode, String reqCode) {
		this.ptnCode = ptnCode;
		this.reqCode = reqCode;
	}


public RequestVo(String reqCode, String ptnCode, int ptnPay) {
		super();
		this.reqCode = reqCode;
		this.ptnCode = ptnCode;
		this.ptnPay = ptnPay;
	}


public RequestVo(String userCode, String ptnCode,
			String constAddress, String reqContent, String samplePhotoUrl1, String samplePhotoUrl2, String reqPrice,
			String reqDate, int acreage, String veranda, String electrics,
			String flooring, String papering, String coating, String ceiling, String middleDoor, String window,
			String tile, String bathroom, String kitchen, String cleaning, String reqType) {
		this.userCode = userCode;
		this.ptnCode = ptnCode;
		this.constAddress = constAddress;
		this.reqContent = reqContent;
		this.samplePhotoUrl1 = samplePhotoUrl1;
		this.samplePhotoUrl2 = samplePhotoUrl2;
		this.reqPrice = reqPrice;
		this.reqDate = reqDate;
		this.acreage = acreage;
		this.veranda = veranda;
		this.electrics = electrics;
		this.flooring = flooring;
		this.papering = papering;
		this.coating = coating;
		this.ceiling = ceiling;
		this.middleDoor = middleDoor;
		this.window = window;
		this.tile = tile;
		this.bathroom = bathroom;
		this.kitchen = kitchen;
		this.cleaning = cleaning;
		this.reqType = reqType;
	}


/*
	public RequestVo(String userCode, String ptnCode, String constAddress, String reqContent,
			String samplePhotoUrl1,String samplePhotoUrl2, String reqPrice, String reqDate, int acreage, String veranda, String electrics,
			String flooring, String papering, String coating, String innerConst, String cleaning, String reqType) {
		this.userCode = userCode;
		this.ptnCode = ptnCode;
		this.constAddress = constAddress;
		this.reqContent = reqContent;
		this.samplePhotoUrl1 = samplePhotoUrl1;
		this.samplePhotoUrl2 = samplePhotoUrl2;
		this.reqPrice = reqPrice;
		this.reqDate = reqDate;
		this.acreage = acreage;
		this.veranda = veranda;
		this.electrics = electrics;
		this.flooring = flooring;
		this.papering = papering;
		this.coating = coating;
		this.innerConst = innerConst;
		this.cleaning = cleaning;
		this.reqType = reqType;
	}
*/

	public String getReqCode() {
		return reqCode;
	}

	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}


	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPtnCode() {
		return ptnCode;
	}

	public void setPtnCode(String ptnCode) {
		this.ptnCode = ptnCode;
	}

	public String getConstAddress() {
		return constAddress;
	}

	public void setConstAddress(String constAddress) {
		this.constAddress = constAddress;
	}

	public String getReqContent() {
		return reqContent;
	}

	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}

	

	public String getSamplePhotoUrl1() {
		return samplePhotoUrl1;
	}


	public void setSamplePhotoUrl1(String samplePhotoUrl1) {
		this.samplePhotoUrl1 = samplePhotoUrl1;
	}


	public String getSamplePhotoUrl2() {
		return samplePhotoUrl2;
	}


	public void setSamplePhotoUrl2(String samplePhotoUrl2) {
		this.samplePhotoUrl2 = samplePhotoUrl2;
	}


	public String getReqPrice() {
		return reqPrice;
	}

	public void setReqPrice(String reqPrice) {
		this.reqPrice = reqPrice;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getReqCheck() {
		return reqCheck;
	}

	public void setReqCheck(String reqCheck) {
		this.reqCheck = reqCheck;
	}

	public int getAcreage() {
		return acreage;
	}

	public void setAcreage(int acreage) {
		this.acreage = acreage;
	}

	public String getVeranda() {
		return veranda;
	}

	public void setVeranda(String veranda) {
		this.veranda = veranda;
	}

	public String getElectrics() {
		return electrics;
	}

	public void setElectrics(String electrics) {
		this.electrics = electrics;
	}

	public String getFlooring() {
		return flooring;
	}

	public void setFlooring(String flooring) {
		this.flooring = flooring;
	}

	public String getPapering() {
		return papering;
	}

	public void setPapering(String papering) {
		this.papering = papering;
	}

	public String getCoating() {
		return coating;
	}

	public void setCoating(String coating) {
		this.coating = coating;
	}
/*
	public String getInnerConst() {
		return innerConst;
	}

	public void setInnerConst(String innerConst) {
		this.innerConst = innerConst;
	}
*/
	
	public String getUserName() {
		return userName;
	}

	public String getCeiling() {
		return ceiling;
	}


	public void setCeiling(String ceiling) {
		this.ceiling = ceiling;
	}


	public String getMiddleDoor() {
		return middleDoor;
	}


	public void setMiddleDoor(String middleDoor) {
		this.middleDoor = middleDoor;
	}


	public String getWindow() {
		return window;
	}


	public void setWindow(String window) {
		this.window = window;
	}


	public String getTile() {
		return tile;
	}


	public void setTile(String tile) {
		this.tile = tile;
	}


	public String getBathroom() {
		return bathroom;
	}


	public void setBathroom(String bathroom) {
		this.bathroom = bathroom;
	}


	public String getKitchen() {
		return kitchen;
	}


	public void setKitchen(String kitchen) {
		this.kitchen = kitchen;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPtnName() {
		return ptnName;
	}

	public void setPtnName(String ptnName) {
		this.ptnName = ptnName;
	}
	
	public String getCleaning() {
		return cleaning;
	}

	public void setCleaning(String cleaning) {
		this.cleaning = cleaning;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	
	

	public int getPtnPay() {
		return ptnPay;
	}


	public void setPtnPay(int ptnPay) {
		this.ptnPay = ptnPay;
	}


	@Override
	public String toString() {
		return "RequestVo [reqCode=" + reqCode + ", userCode=" + userCode + ", userName=" + userName + ", ptnCode="
				+ ptnCode + ", ptnName=" + ptnName + ", constAddress=" + constAddress + ", reqContent=" + reqContent
				+ ", samplePhotoUrl1=" + samplePhotoUrl1 + ", samplePhotoUrl2=" + samplePhotoUrl2 + ", reqPrice="
				+ reqPrice + ", reqDate=" + reqDate + ", startDate=" + startDate + ", reqCheck=" + reqCheck
				+ ", acreage=" + acreage + ", veranda=" + veranda + ", electrics=" + electrics + ", flooring="
				+ flooring + ", papering=" + papering + ", coating=" + coating + ", ceiling=" + ceiling
				+ ", middleDoor=" + middleDoor + ", window=" + window + ", tile=" + tile + ", bathroom=" + bathroom
				+ ", kitchen=" + kitchen + ", cleaning=" + cleaning + ", reqType=" + reqType + "]";
	}
	
	
	
}
