package ein.mono.profile.model.vo;

public class PtnPhoto {
	private String oldName; // 업로드한 사진의 원래 이름
	private String newName; // 업로드한 사진에게 새로 붙은 이름
	private int photoIndex; // 업로드한 사진의 우선순위
	public PtnPhoto(String oldName, String newName, int photoIndex) {
		super();
		this.oldName = oldName;
		this.newName = newName;
		this.photoIndex = photoIndex;
	}
	public PtnPhoto() {
		super();
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public int getPhotoIndex() {
		return photoIndex;
	}
	public void setPhotoIndex(int photoIndex) {
		this.photoIndex = photoIndex;
	}
	@Override
	public String toString() {
		return "PtnPhoto [oldName=" + oldName + ", newName=" + newName + ", photoIndex=" + photoIndex + "]";
	}	
}
