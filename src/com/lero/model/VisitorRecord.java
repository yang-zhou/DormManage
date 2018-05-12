package com.lero.model;

public class VisitorRecord {
	private String id;
	private String visName;
	private String visSex;
	private String visIdCard;
	private String visPhone;
	private String visDormBuild;
	private String visDormBuildName;
	private String visDormBuildRoom;
	private String visInTime;
	private String visOutTime;
	private String checkedId;
	private String remark;
	
	public VisitorRecord() {
		super();
	}
	public VisitorRecord(String visName, String visSex, String visIdCard, String visPhone, String visDormBuild,
			String visDormBuildName, String visDormBuildRoom, String visInTime, String visOutTime, String checkedId, String remark) {
		this.visName = visName;
		this.visSex = visSex;
		this.visIdCard = visIdCard;
		this.visPhone = visPhone;
		this.visDormBuild = visDormBuild;
		this.visDormBuildName = visDormBuildName;
		this.visDormBuildRoom = visDormBuildRoom;
		this.visInTime = visInTime;
		this.visOutTime = visOutTime;
		this.checkedId = checkedId;
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVisName() {
		return visName;
	}
	public void setVisName(String visName) {
		this.visName = visName;
	}
	public String getVisSex() {
		return visSex;
	}
	public void setVisSex(String visSex) {
		this.visSex = visSex;
	}
	public String getVisIdCard() {
		return visIdCard;
	}
	public void setVisIdCard(String visIdCard) {
		this.visIdCard = visIdCard;
	}
	public String getVisPhone() {
		return visPhone;
	}
	public void setVisPhone(String visPhone) {
		this.visPhone = visPhone;
	}
	public String getVisDormBuild() {
		return visDormBuild;
	}
	public void setVisDormBuild(String visDormBuild) {
		this.visDormBuild = visDormBuild;
	}
	public String getVisDormBuildName() {
		return visDormBuildName;
	}
	public void setVisDormBuildName(String visDormBuildName) {
		this.visDormBuildName = visDormBuildName;
	}
	public String getVisDormBuildRoom() {
		return visDormBuildRoom;
	}
	public void setVisDormBuildRoom(String visDormBuildRoom) {
		this.visDormBuildRoom = visDormBuildRoom;
	}
	public String getVisInTime() {
		return visInTime;
	}
	public void setVisInTime(String visInTime) {
		this.visInTime = visInTime;
	}
	public String getVisOutTime() {
		return visOutTime;
	}
	public void setVisOutTime(String visOutTime) {
		this.visOutTime = visOutTime;
	}
	public String getCheckedId() {
		return checkedId;
	}
	public void setCheckedId(String checkedId) {
		this.checkedId = checkedId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
