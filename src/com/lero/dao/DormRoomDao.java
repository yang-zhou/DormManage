package com.lero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lero.model.DormBuild;
import com.lero.model.DormManager;
import com.lero.model.DormRoom;
import com.lero.model.PageBean;
import com.lero.model.Student;
import com.lero.util.StringUtil;

public class DormRoomDao {
	public List<DormRoom> dormRoomList(Connection con, PageBean pageBean, DormRoom dormRoom)throws Exception {
		List<DormRoom> dormRoomList = new ArrayList<DormRoom>();
		StringBuffer sb = new StringBuffer("select * from t_dorm_room t1");
		if(StringUtil.isNotEmpty(dormRoom.getDormBuildId())) {
			sb.append(" and t1.dorm_build_id="+dormRoom.getDormBuildId());
		}
		if(pageBean != null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		System.out.println("查询语句：" + sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DormRoom tempDormRoom=new DormRoom();
			tempDormRoom.setId(rs.getString("id"));
			tempDormRoom.setDormBuildId(rs.getString("dorm_build_id"));
			tempDormRoom.setDormBuildName(rs.getString("dorm_build_name"));
			tempDormRoom.setDormRoomNumber(rs.getString("dorm_room_number"));
			tempDormRoom.setDormRoomName(rs.getString("dorm_room_name"));
			tempDormRoom.setDormRoomTel(rs.getString("dorm_room_tel"));
			tempDormRoom.setDormRoomMax(rs.getString("dorm_room_max"));
			tempDormRoom.setRemark(rs.getString("remark"));
			dormRoomList.add(tempDormRoom);
		}
		return dormRoomList;
	}

	public static String dormRoomName(Connection con, String dormRoomNumber)throws Exception {
		String sql = "select * from t_dorm_room where dorm_room_number=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, dormRoomNumber);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getString("dorm_room_name");
		}
		return null;
	}
	
	public int dormRoomCount(Connection con, DormRoom dormRoom)throws Exception {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_dorm_room t1");
		/*if(StringUtil.isNotEmpty(dormRoom.getDormBuildName())) {
			sb.append(" where t1.dormBuildName like '%"+s_dormBuild.getDormBuildName()+"%'");
		}*/
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}
	
	public int dormRoomAdd(Connection con, DormRoom dormRoom)throws Exception {
		String sql = "insert into t_dorm_room (dorm_build_id,dorm_build_name,dorm_room_number,dorm_room_name,dorm_room_tel,dorm_room_max,remark) values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormRoom.getDormBuildId());
		pstmt.setString(2, dormRoom.getDormBuildName());
		pstmt.setString(3, dormRoom.getDormRoomNumber());
		pstmt.setString(4, dormRoom.getDormRoomName());
		pstmt.setString(5, dormRoom.getDormRoomTel());
		pstmt.setString(6, dormRoom.getDormRoomMax());
		pstmt.setString(7, dormRoom.getRemark());
		return pstmt.executeUpdate();
	}
	
	public int dormRoomDelete(Connection con, String dormRoomNumber)throws Exception {
		String sql = "delete from t_dorm_room where dorm_room_number=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormRoomNumber);
		return pstmt.executeUpdate();
	}

	public DormRoom dormRoomShow(Connection con, String id) throws SQLException {
		String sql = "select * from t_dorm_room t1 where t1.id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs=pstmt.executeQuery();
		DormRoom dormRoom = new DormRoom();
		if(rs.next()) {
			dormRoom.setId(rs.getString("id"));
			dormRoom.setDormBuildId(rs.getString("dorm_build_id"));
			dormRoom.setDormBuildName(rs.getString("dorm_build_name"));
			dormRoom.setDormRoomNumber(rs.getString("dorm_build_number"));
			dormRoom.setDormRoomName(rs.getString("dorm_build_name"));
			dormRoom.setDormRoomTel(rs.getString("dorm_build_tel"));
			dormRoom.setDormRoomMax(rs.getString("dorm_build_max"));
			dormRoom.setRemark(rs.getString("remark"));
		}
		return dormRoom;
	}

	public List<DormRoom> visitorRecordWithBuild(Connection conn, DormRoom dormRoom, int buildId) throws SQLException {
		List<DormRoom> dormRoomList = new ArrayList<DormRoom>();
		StringBuffer sb = new StringBuffer("select * from t_dorm_room t1");
		sb.append(" and t1.dorm_build_id="+buildId);
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DormRoom tempDormRoom=new DormRoom();
			tempDormRoom.setId(rs.getString("id"));
			tempDormRoom.setDormBuildId(rs.getString("dorm_build_id"));
			tempDormRoom.setDormBuildName(rs.getString("dorm_build_name"));
			tempDormRoom.setDormRoomNumber(rs.getString("dorm_room_number"));
			tempDormRoom.setDormRoomName(rs.getString("dorm_room_name"));
			tempDormRoom.setDormRoomTel(rs.getString("dorm_room_tel"));
			tempDormRoom.setDormRoomMax(rs.getString("dorm_room_max"));
			tempDormRoom.setRemark(rs.getString("remark"));
			dormRoomList.add(tempDormRoom);
		}
		return dormRoomList;
	}
	
	/*public int dormRoomUpdate(Connection con, DormRoom dormRoom)throws Exception {
		String sql = "update t_dorm_room set dorm_build_id=?,dorm_room_number=?,dorm_room_name=?,dorm_room_tel=?,dorm_room_max where dorm_room_number=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormBuild.getDormBuildName());
		pstmt.setString(2, dormBuild.getDetail());
		pstmt.setInt(3, dormBuild.getDormBuildId());
		return pstmt.executeUpdate();
	}*/
}
