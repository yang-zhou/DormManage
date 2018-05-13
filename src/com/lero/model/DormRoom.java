package com.lero.model;

public class DormRoom {
	private String id;
	private String dormBuildId;
	private String dormBuildName;
	private String dormRoomNumber;
	private String dormRoomName;
	private String dormRoomTel;
	private String dormRoomMax;
	private String remark;
	
	
	public DormRoom() {
		super();
	}
	public DormRoom(String dormBuildId, String dormBuildName, String dormRoomNumber, String dormRoomName, String dormRoomTel,
			String dormRoomMax, String remark) {
		this.dormBuildId = dormBuildId;
		this.dormBuildName = dormBuildName;
		this.dormRoomNumber = dormRoomNumber;
		this.dormRoomName = dormRoomName;
		this.dormRoomTel = dormRoomTel;
		this.dormRoomMax = dormRoomMax;
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDormBuildId() {
		return dormBuildId;
	}
	public void setDormBuildId(String dormBuildId) {
		this.dormBuildId = dormBuildId;
	}
	public String getDormBuildName() {
		return dormBuildName;
	}
	public void setDormBuildName(String dormBuildName) {
		this.dormBuildName = dormBuildName;
	}
	public String getDormRoomNumber() {
		return dormRoomNumber;
	}
	public void setDormRoomNumber(String dormRoomNumber) {
		this.dormRoomNumber = dormRoomNumber;
	}
	public String getDormRoomName() {
		return dormRoomName;
	}
	public void setDormRoomName(String dormRoomName) {
		this.dormRoomName = dormRoomName;
	}
	public String getDormRoomTel() {
		return dormRoomTel;
	}
	public void setDormRoomTel(String dormRoomTel) {
		this.dormRoomTel = dormRoomTel;
	}
	public String getDormRoomMax() {
		return dormRoomMax;
	}
	public void setDormRoomMax(String dormRoomMax) {
		this.dormRoomMax = dormRoomMax;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
