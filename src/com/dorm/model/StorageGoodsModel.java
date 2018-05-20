package com.dorm.model;

public class StorageGoodsModel {
	private String id;
	private String stuNum;
	private String stuName;
	private String inTime;
	private String outTime;
	private String status;
	private String remark;
	
	public StorageGoodsModel() {
		super();
	}
	public StorageGoodsModel(String _stuNum, String _stuName, String _inTime, String _outTime, String _status, String _remark) {
		this.stuNum = _stuNum;
		this.stuName = _stuName;
		this.inTime = _inTime;
		this.outTime = _outTime;
		this.status = _status;
		this.remark = _remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStuNum() {
		return stuNum;
	}
	public void setStuNum(String stunum) {
		this.stuNum = stunum;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuname) {
		this.stuName = stuname;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String intime) {
		this.inTime = intime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outtime) {
		this.outTime = outtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
