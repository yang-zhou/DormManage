package com.lero.model;

public class DormRoomRating {
	private String id;
	private String dormBuildId;
	private String dormBuildName;
	private String dormRoomNumber;
	private String scoreClean;
	private String scoreCulture;
	private String scoreObey;
	private String ratingDate;
	private String remark;
	
	public DormRoomRating() {
		super();
	}
	public DormRoomRating(String dormBuildId, String dormRoomNumber, String scoreClean,
			String scoreCulture, String scoreObey, String remark) {
		this.dormBuildId = dormBuildId;
		this.dormRoomNumber = dormRoomNumber;
		this.scoreClean = scoreClean;
		this.scoreCulture = scoreCulture;
		this.scoreObey = scoreObey;
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
	public String getScoreClean() {
		return scoreClean;
	}
	public void setScoreClean(String scoreClean) {
		this.scoreClean = scoreClean;
	}
	public String getScoreCulture() {
		return scoreCulture;
	}
	public void setScoreCulture(String scoreCulture) {
		this.scoreCulture = scoreCulture;
	}
	public String getScoreObey() {
		return scoreObey;
	}
	public void setScoreObey(String scoreObey) {
		this.scoreObey = scoreObey;
	}
	public String getRatingDate() {
		return ratingDate;
	}
	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
