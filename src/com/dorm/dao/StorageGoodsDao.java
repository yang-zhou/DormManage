package com.dorm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.dorm.model.PageBean;
import com.dorm.model.Record;
import com.dorm.model.StorageGoodsModel;
import com.dorm.model.Student;
import com.dorm.model.VisitorRecord;
import com.dorm.util.StringUtil;

public class StorageGoodsDao {
	public List<StorageGoodsModel> listStorageGoodsModel(Connection con, PageBean pageBean, StorageGoodsModel storageGoodsModel)throws Exception {
		List<StorageGoodsModel> storageGoodsModelList = new ArrayList<StorageGoodsModel>();
		StringBuffer sb = new StringBuffer("select * from t_stored_item t1, t_student t2 where t1.stu_num = t2.stuNum");
		if(StringUtil.isNotEmpty(storageGoodsModel.getStuNum())) {
			sb.append(" and t1.stu_num like '%"+storageGoodsModel.getStuNum()+"%'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getStuName())) {
			sb.append(" and t2.name like '%"+storageGoodsModel.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getInTime())) {
			sb.append(" and t1.strored_in_time >="+"\'"+storageGoodsModel.getInTime()+"\'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getOutTime())) {
			sb.append(" and t1.strored_out_time <= "+"\'"+storageGoodsModel.getOutTime()+"\'");
		}
		sb.append(" and strored_status = 0");
		if(pageBean != null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		System.out.println("查询sql语句：" + sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			StorageGoodsModel tempStorageGoodsModel = new StorageGoodsModel();
			tempStorageGoodsModel.setId(rs.getString("id"));
			tempStorageGoodsModel.setStuNum(rs.getString("stu_num"));
			tempStorageGoodsModel.setStuName(rs.getString("name"));
			tempStorageGoodsModel.setInTime(rs.getString("strored_in_time"));
			tempStorageGoodsModel.setOutTime(rs.getString("strored_out_time"));
			tempStorageGoodsModel.setStatus(rs.getString("strored_status"));
			tempStorageGoodsModel.setRemark(rs.getString("stored_remark"));
			storageGoodsModelList.add(tempStorageGoodsModel);
		}
		return storageGoodsModelList;
	}

	public int storageGoodsCount(Connection conn, StorageGoodsModel storageGoodsModel) throws SQLException {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_stored_item t1");
		if(StringUtil.isNotEmpty(storageGoodsModel.getStuNum())) {
			sb.append(" and t1.stu_num like '%"+storageGoodsModel.getStuNum()+"%'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getInTime())) {
			sb.append(" and t1.strored_status like '%"+storageGoodsModel.getInTime()+"%'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getOutTime())) {
			sb.append(" and t1.strored_in_time >= "+"\'"+storageGoodsModel.getOutTime()+"\'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getStatus())) {
			sb.append(" and t1.strored_out_time <= "+"\'"+storageGoodsModel.getStatus()+"\'");
		}
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public StorageGoodsModel storageGoodsShow(Connection con, String id) throws SQLException {
		String sql = "select * from t_stored_item t1 where t1.id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs=pstmt.executeQuery();
		StorageGoodsModel tempStorageGoodsModel = new StorageGoodsModel();
		if(rs.next()) {
			tempStorageGoodsModel.setId(rs.getString("id"));
			tempStorageGoodsModel.setStuNum(rs.getString("stu_num"));
			tempStorageGoodsModel.setInTime(rs.getString("strored_in_time"));
			tempStorageGoodsModel.setOutTime(rs.getString("strored_out_time"));
			tempStorageGoodsModel.setStatus(rs.getString("strored_status"));
			tempStorageGoodsModel.setRemark(rs.getString("stored_remark"));
		}
		return tempStorageGoodsModel;
	}

	public int storageGoodsAdd(Connection con, StorageGoodsModel storageGoodsModel) throws SQLException {
		String sql = "insert into t_stored_item (stu_num,strored_in_time,strored_out_time,strored_status,stored_remark) values(?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, storageGoodsModel.getStuNum());
		pstmt.setString(2, storageGoodsModel.getInTime());
		pstmt.setString(3, storageGoodsModel.getOutTime());
		pstmt.setString(4, storageGoodsModel.getStatus());
		pstmt.setString(5, storageGoodsModel.getRemark());
		pstmt.executeUpdate();
		return 1;
	}

	public List<StorageGoodsModel> storageGoodsWithBuild(Connection conn, StorageGoodsModel storageGoodsModel, int buildId) throws SQLException {
		List<StorageGoodsModel> storageGoodsModelList = new ArrayList<StorageGoodsModel>();
		StringBuffer sb = new StringBuffer("select * from t_stored_item t1");
		if(StringUtil.isNotEmpty(storageGoodsModel.getStuNum())) {
			sb.append(" and t1.stu_num like '%"+storageGoodsModel.getStuNum()+"%'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getInTime())) {
			sb.append(" and t1.strored_status like '%"+storageGoodsModel.getInTime()+"%'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getOutTime())) {
			sb.append(" and t1.strored_in_time >= "+"\'"+storageGoodsModel.getOutTime()+"\'");
		}
		if(StringUtil.isNotEmpty(storageGoodsModel.getStatus())) {
			sb.append(" and t1.strored_out_time <= "+"\'"+storageGoodsModel.getStatus()+"\'");
		}
		sb.append(" and t1.vis_dorm_build="+buildId);
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			StorageGoodsModel tempStorageGoodsModel = new StorageGoodsModel();
			tempStorageGoodsModel.setId(rs.getString("id"));
			tempStorageGoodsModel.setStuNum(rs.getString("stu_num"));
			tempStorageGoodsModel.setInTime(rs.getString("strored_in_time"));
			tempStorageGoodsModel.setOutTime(rs.getString("strored_out_time"));
			tempStorageGoodsModel.setStatus(rs.getString("strored_status"));
			tempStorageGoodsModel.setRemark(rs.getString("stored_remark"));
			storageGoodsModelList.add(tempStorageGoodsModel);
		}
		return storageGoodsModelList;
	}
	public int storageGoodsUpdate(Connection con, StorageGoodsModel storageGoodsModel)throws Exception {
		String sql = "update t_stored_item set stu_num=?,strored_in_time=?,strored_out_time=?,strored_status=?,stored_remark=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, storageGoodsModel.getStuNum());
		pstmt.setString(2, storageGoodsModel.getInTime());
		pstmt.setString(3, storageGoodsModel.getOutTime());
		pstmt.setString(4, storageGoodsModel.getStatus());
		pstmt.setString(5, storageGoodsModel.getRemark());
		pstmt.setString(6, storageGoodsModel.getId());
		return pstmt.executeUpdate();
	}
}
