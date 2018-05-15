package com.lero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import com.lero.model.DormRoomRating;
import com.lero.model.PageBean;
import com.lero.model.VisitorRecord;
import com.lero.util.StringUtil;

public class DormRoomRatingDao {
	private DormBuildDao dormBuildDao = new DormBuildDao();
	public List<DormRoomRating> listDormRoomRating(Connection con, PageBean pageBean, DormRoomRating dormRoomRating)throws Exception {
		List<DormRoomRating> dormRoomRatingList = new ArrayList<DormRoomRating>();
		StringBuffer sb = new StringBuffer("select * from t_dorm_room_rating t1");
		if(StringUtil.isNotEmpty(dormRoomRating.getDormBuildId())) {
			sb.append(" and t1.dorm_build_id ="+dormRoomRating.getDormBuildId());
		}
		if(StringUtil.isNotEmpty(dormRoomRating.getDormRoomNumber())) {
			sb.append(" and t1.dorm_room_number like '%"+dormRoomRating.getDormRoomNumber()+"%'");
		}
		if(StringUtil.isNotEmpty(dormRoomRating.getRatingDate())) {
			sb.append(" and t1.ratingdate >= "+"\'"+dormRoomRating.getRatingDate()+"\'");
		}
		if(pageBean != null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		System.out.println("查询sql语句：" + sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DormRoomRating tempDormRoomRating = new DormRoomRating();
			tempDormRoomRating.setId(rs.getString("id"));
			tempDormRoomRating.setDormBuildId(rs.getString("dorm_build_id"));
			tempDormRoomRating.setDormBuildName(rs.getString("dorm_build_name"));
			tempDormRoomRating.setDormRoomNumber(rs.getString("dorm_room_number"));
			tempDormRoomRating.setScoreClean(rs.getString("score_clean"));
			tempDormRoomRating.setScoreCulture(rs.getString("score_culture"));
			tempDormRoomRating.setScoreObey(rs.getString("score_obey"));
			tempDormRoomRating.setRatingDate(rs.getString("rating_date"));
			tempDormRoomRating.setRemark(rs.getString("remark"));
			dormRoomRatingList.add(tempDormRoomRating);
		}
		return dormRoomRatingList;
	}

	public int dormRommRatingCount(Connection conn, DormRoomRating dormRoomRating) throws SQLException {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_dorm_room_rating t1");
		if(StringUtil.isNotEmpty(dormRoomRating.getDormBuildId())) {
            sb.append(" and t1.dorm_build_id ="+dormRoomRating.getDormBuildId());
        }
        if(StringUtil.isNotEmpty(dormRoomRating.getDormRoomNumber())) {
            sb.append(" and t1.dorm_room_number like '%"+dormRoomRating.getDormRoomNumber()+"%'");
        }
        if(StringUtil.isNotEmpty(dormRoomRating.getRatingDate())) {
            sb.append(" and t1.ratingdate >= "+"\'"+dormRoomRating.getRatingDate()+"\'");
        }
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public DormRoomRating dormRoomRatingdShow(Connection con, String id) throws SQLException {
		String sql = "select * from t_dorm_room_rating t1 where t1.id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs=pstmt.executeQuery();
		DormRoomRating tempDormRoomRating = new DormRoomRating();
		if(rs.next()) {
			tempDormRoomRating.setId(rs.getString("id"));
			tempDormRoomRating.setDormBuildId(rs.getString("dorm_build_id"));
			tempDormRoomRating.setDormBuildName(rs.getString("dorm_build_name"));
			tempDormRoomRating.setDormRoomNumber(rs.getString("dorm_room_number"));
			tempDormRoomRating.setScoreClean(rs.getString("score_clean"));
			tempDormRoomRating.setScoreCulture(rs.getString("score_culture"));
			tempDormRoomRating.setScoreObey(rs.getString("score_obey"));
			tempDormRoomRating.setRatingDate(rs.getString("rating_date"));
			tempDormRoomRating.setRemark(rs.getString("remark"));
		}
		return tempDormRoomRating;
	}

	public int dormRoomRatingAdd(Connection con, DormRoomRating dormRoomRating) throws NumberFormatException, Exception {
		String sql = "insert into t_dorm_room_rating (dorm_build_id,dorm_build_name,dorm_room_number,score_clean,score_culture,score_obey,rating_date,remark) values(?,?,?,?,?,?,?,?)";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormRoomRating.getDormBuildId());
		pstmt.setString(2, dormBuildDao.dormBuildName(con, Integer.parseInt(dormRoomRating.getDormBuildId())));
		pstmt.setString(3, dormRoomRating.getDormRoomNumber());
		pstmt.setString(4, dormRoomRating.getScoreClean());
		pstmt.setString(5, dormRoomRating.getScoreCulture());
		pstmt.setString(6, dormRoomRating.getScoreObey());
		pstmt.setString(7, format.format(new Date()));
		pstmt.setString(8, dormRoomRating.getRemark());
		return pstmt.executeUpdate();
	}

	/*public List<VisitorRecord> visitorRecordWithBuild(Connection conn, VisitorRecord visitorRecord, int buildId) throws SQLException {
		List<VisitorRecord> visitorRecordList = new ArrayList<VisitorRecord>();
		StringBuffer sb = new StringBuffer("select * from t_visitor t1");
		if(StringUtil.isNotEmpty(visitorRecord.getVisName())) {
			sb.append(" and t1.vis_name like '%"+visitorRecord.getVisName()+"%'");
		}
		if(StringUtil.isNotEmpty(visitorRecord.getVisDormBuildRoom())) {
			sb.append(" and t1.vis_dorm_build_room like '%"+visitorRecord.getVisDormBuildRoom()+"%'");
		}
		if(StringUtil.isNotEmpty(visitorRecord.getVisInTime())) {
			sb.append(" and t1.vis_in_time >= "+"\'"+visitorRecord.getVisInTime()+"\'");
		}
		if(StringUtil.isNotEmpty(visitorRecord.getVisOutTime())) {
			sb.append(" and t1.vis_out_time <= "+"\'"+visitorRecord.getVisOutTime()+"\'");
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
	}*/

    public int dormRoomRatingDelete(Connection con, String id) throws SQLException {
        String sql = "delete from t_dorm_room_rating where id=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeUpdate();
    }

    public int dormRoomRatingUpdate(Connection con, DormRoomRating dormRoomRating) throws NumberFormatException, Exception {
        String sql = "update t_dorm_room_rating set dorm_build_id=?,dorm_build_name=?,dorm_room_number=?,score_clean=?,score_culture=?,score_obey=?,remark=? where id=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, dormRoomRating.getDormBuildId());
        pstmt.setString(2, dormBuildDao.dormBuildName(con, Integer.parseInt(dormRoomRating.getDormBuildId())));
        pstmt.setString(3, dormRoomRating.getDormRoomNumber());
        pstmt.setString(4, dormRoomRating.getScoreClean());
        pstmt.setString(5, dormRoomRating.getScoreCulture());
        pstmt.setString(6, dormRoomRating.getScoreObey());
        pstmt.setString(7, dormRoomRating.getRemark());
        pstmt.setString(8, dormRoomRating.getId());
        return pstmt.executeUpdate();
    }
}

