package com.lero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lero.model.PageBean;
import com.lero.model.Record;
import com.lero.model.Student;
import com.lero.model.VisitorRecord;
import com.lero.util.StringUtil;

public class VisitorRecordDao {
	public List<VisitorRecord> listVisitorRecord(Connection con, PageBean pageBean)throws Exception {
		List<VisitorRecord> visitorRecordList = new ArrayList<VisitorRecord>();
		StringBuffer sb = new StringBuffer("select * from t_visitor t1");
		if(pageBean != null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			VisitorRecord visitorRecord = new VisitorRecord();
			visitorRecord.setId(rs.getString("id"));
			visitorRecord.setVisName(rs.getString("vis_name"));
			visitorRecord.setVisSex(rs.getString("vis_sex"));
			visitorRecord.setVisPhone(rs.getString("vis_phone"));
			visitorRecord.setVisInTime(rs.getString("vis_in_time"));
			visitorRecord.setVisOutTime(rs.getString("vis_out_time"));
			visitorRecord.setCheckedId(rs.getString("checked_id"));
			visitorRecord.setRemark(rs.getString("remark"));
			visitorRecord.setVisDormBuild(rs.getString("vis_dorm_build"));
			visitorRecord.setVisDormBuildName(rs.getString("vis_dorm_build_name"));
			visitorRecord.setVisDormBuildRoom(rs.getString("vis_dorm_build_room"));
			visitorRecord.setVisIdCard(rs.getString("vis_id_card"));
			visitorRecordList.add(visitorRecord);
		}
		return visitorRecordList;
	}

	public int visitorRecordCount(Connection conn, VisitorRecord visitorRecord) throws SQLException {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_visitor t1");
		/*if(StringUtil.isNotEmpty(visitorRecord.getName())) {
			sb.append(" and t1.name like '%"+s_student.getName()+"%'");
		} else if(StringUtil.isNotEmpty(s_student.getStuNumber())) {
			sb.append(" and t1.stuNum like '%"+s_student.getStuNumber()+"%'");
		} else if(StringUtil.isNotEmpty(s_student.getDormName())) {
			sb.append(" and t1.dormName like '%"+s_student.getDormName()+"%'");
		}
		if(s_student.getDormBuildId()!=0) {
			sb.append(" and t1.dormBuildId="+s_student.getDormBuildId());
		}*/
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public VisitorRecord visitorRecordShow(Connection con, String id) throws SQLException {
		String sql = "select * from t_visitor t1 where t1.id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs=pstmt.executeQuery();
		VisitorRecord visitorRecord = new VisitorRecord();
		if(rs.next()) {
			visitorRecord.setId(rs.getString("id"));
			visitorRecord.setVisName(rs.getString("vis_name"));
			visitorRecord.setVisSex(rs.getString("vis_sex"));
			visitorRecord.setVisPhone(rs.getString("vis_phone"));
			visitorRecord.setVisDormBuild(rs.getString("vis_dorm_build"));
			visitorRecord.setVisDormBuildName(rs.getString("vis_dorm_build_name"));
			visitorRecord.setVisDormBuildRoom(rs.getString("vis_dorm_build_room"));
			visitorRecord.setVisInTime(rs.getString("vis_in_time"));
			visitorRecord.setVisOutTime(rs.getString("vis_out_time"));
			visitorRecord.setCheckedId(rs.getString("checked_id"));
			visitorRecord.setRemark(rs.getString("remark"));
			visitorRecord.setVisIdCard(rs.getString("vis_id_card"));
		}
		return visitorRecord;
	}

	public int visitorRecordAdd(Connection con, VisitorRecord visitorRecord) throws SQLException {
		String sql = "insert into t_visitor (vis_name,vis_sex,vis_id_card,vis_phone,vis_dorm_build,vis_dorm_build_name,vis_dorm_build_room,vis_in_time,vis_out_time,checked_id,remark) values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, visitorRecord.getVisName());
		pstmt.setString(2, visitorRecord.getVisSex());
		pstmt.setString(3, visitorRecord.getVisIdCard());
		pstmt.setString(4, visitorRecord.getVisPhone());
		pstmt.setString(5, visitorRecord.getVisDormBuild());
		pstmt.setString(6, visitorRecord.getVisDormBuildName());
		pstmt.setString(7, visitorRecord.getVisDormBuildRoom());
		pstmt.setString(8, visitorRecord.getVisInTime());
		pstmt.setString(9, visitorRecord.getVisOutTime());
		pstmt.setString(10, visitorRecord.getCheckedId());
		pstmt.setString(11, visitorRecord.getRemark());
		return pstmt.executeUpdate();
	}

	public List<VisitorRecord> visitorRecordWithBuild(Connection conn, VisitorRecord visitorRecord, int buildId) throws SQLException {
		List<VisitorRecord> visitorRecordList = new ArrayList<VisitorRecord>();
		StringBuffer sb = new StringBuffer("select * from t_visitor t1");
		if(StringUtil.isNotEmpty(visitorRecord.getVisName())) {
			sb.append(" and t1.vis_name like '%"+visitorRecord.getVisName()+"%'");
		} else if(StringUtil.isNotEmpty(visitorRecord.getVisDormBuild())) {
			sb.append(" and t1.vis_dorm_build like '%"+visitorRecord.getVisDormBuild()+"%'");
		} else if(StringUtil.isNotEmpty(visitorRecord.getVisInTime())) {
			sb.append(" and "+visitorRecord.getVisInTime()+" < t1.vis_in_time");
		}else if(StringUtil.isNotEmpty(visitorRecord.getVisOutTime())) {
			sb.append(" and "+visitorRecord.getVisOutTime()+" > t1.vis_out_time");
		}
		sb.append(" and t1.vis_dorm_build="+buildId);
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			VisitorRecord tempVisitorRecord=new VisitorRecord();
			tempVisitorRecord.setId(rs.getString("id"));
			tempVisitorRecord.setVisName(rs.getString("vis_name"));
			tempVisitorRecord.setVisSex(rs.getString("vis_sex"));
			tempVisitorRecord.setVisPhone(rs.getString("vis_phone"));
			tempVisitorRecord.setVisDormBuild(rs.getString("vis_dorm_build"));
			tempVisitorRecord.setVisDormBuildName(rs.getString("vis_dorm_build_name"));
			tempVisitorRecord.setVisDormBuildRoom(rs.getString("vis_dorm_build_room"));
			tempVisitorRecord.setVisInTime(rs.getString("vis_in_time"));
			tempVisitorRecord.setVisOutTime(rs.getString("vis_out_time"));
			tempVisitorRecord.setCheckedId(rs.getString("checked_id"));
			tempVisitorRecord.setRemark(rs.getString("remark"));
			tempVisitorRecord.setVisIdCard(rs.getString("vis_id_card"));
			visitorRecordList.add(tempVisitorRecord);
		}
		return visitorRecordList;
	}
}
