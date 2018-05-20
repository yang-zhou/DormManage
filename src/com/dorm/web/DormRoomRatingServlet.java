package com.dorm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.dorm.dao.DormBuildDao;
import com.dorm.dao.DormRoomRatingDao;
import com.dorm.dao.StudentDao;
import com.dorm.dao.VisitorRecordDao;
import com.dorm.model.DormBuild;
import com.dorm.model.DormManager;
import com.dorm.model.DormRoomRating;
import com.dorm.model.PageBean;
import com.dorm.model.Student;
import com.dorm.model.VisitorRecord;
import com.dorm.util.DbUtil;
import com.dorm.util.PropertiesUtil;
import com.dorm.util.StringUtil;
import com.google.gson.Gson;

public class DormRoomRatingServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	private VisitorRecordDao visitorRecordDao = new VisitorRecordDao();
	private DormRoomRatingDao dormRoomRatingDao = new DormRoomRatingDao();
	private StudentDao studentDao = new StudentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Object currentUserType = session.getAttribute("currentUserType");
		String ratingDate = request.getParameter("ratingDate");
		String s_dormRoomRatingText = request.getParameter("s_dormRoomRatingText");
		String dormBuildId = request.getParameter("buildToSelect");
		String searchType = request.getParameter("searchType");
		String page = request.getParameter("page");
		String action = request.getParameter("action");
		VisitorRecord visitorRecord = new VisitorRecord();
		DormRoomRating dormRoomRating = new DormRoomRating();
		Connection conn = null;
		if("preSave".equals(action)) {
			dormRoomRatingPreSave(request, response);
			return;
		} else if("save".equals(action)){
			dormRoomRatingSave(request, response);
			return;
		} else if("delete".equals(action)){
		    dormRoomRatingDelete(request, response);
            return;
        }else if("list".equals(action)) {
		
		} else if("search".equals(action)) {
			if(StringUtil.isNotEmpty(s_dormRoomRatingText)) {
				if("dormRoom".equals(searchType)) {
					dormRoomRating.setDormRoomNumber(s_dormRoomRatingText);
				}
				session.setAttribute("s_dormRoomRatingText", s_dormRoomRatingText);
				session.setAttribute("searchType", searchType);
			} else {
				session.removeAttribute("s_dormRoomRatingText");
				session.removeAttribute("searchType");
			}
			if(StringUtil.isNotEmpty(dormBuildId)) {
			    dormRoomRating.setDormBuildId(dormBuildId);
				session.setAttribute("buildToSelect", dormBuildId);
			}else {
				session.removeAttribute("buildToSelect");
			}
			if(StringUtil.isNotEmpty(ratingDate)){
			    dormRoomRating.setRatingDate(ratingDate);
			}
		}	
		if(StringUtil.isEmpty(page)) {
			page="1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		request.setAttribute("pageSize", pageBean.getPageSize());
		request.setAttribute("page", pageBean.getPage());
		try {
			conn = dbUtil.getCon();
			if("admin".equals((String)currentUserType)) {
				List<DormRoomRating> DormRoomRatingList = dormRoomRatingDao.listDormRoomRating(conn, pageBean, dormRoomRating);
				int total=dormRoomRatingDao.dormRommRatingCount(conn, dormRoomRating);
				String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
				request.setAttribute("pageCode", pageCode);
				request.setAttribute("DormRoomRatingList", DormRoomRatingList);
				request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
				request.setAttribute("mainPage", "admin/dormRoomRating.jsp");
				request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
			} else if("dormManager".equals((String)currentUserType)) {
				DormManager manager = (DormManager)(session.getAttribute("currentUser"));
				int buildId = manager.getDormBuildId();
				dormRoomRating.setDormBuildId(buildId+"");
				String buildName = DormBuildDao.dormBuildName(conn, buildId);
				List<DormRoomRating> dormRoomRatingList = dormRoomRatingDao.listDormRoomRating(conn, pageBean, dormRoomRating);
				request.setAttribute("dormBuildName", buildName);
				request.setAttribute("dormRoomRatingList", dormRoomRatingList);
				request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
				request.setAttribute("mainPage", "dormManager/dormRoomRating.jsp");
				request.getRequestDispatcher("mainManager.jsp").forward(request, response);
			} else if("student".equals((String)currentUserType)) {
                Student student = (Student)(session.getAttribute("currentUser"));
                int buildId = student.getDormBuildId();
                String dormRoomNumber = student.getDormName();
                dormRoomRating.setDormBuildId(buildId+"");
                dormRoomRating.setDormRoomNumber(dormRoomNumber);
                String buildName = DormBuildDao.dormBuildName(conn, buildId);
                List<DormRoomRating> dormRoomRatingList = dormRoomRatingDao.listDormRoomRating(conn, pageBean, dormRoomRating);
                request.setAttribute("dormBuildName", buildName);
                request.setAttribute("dormRoomNumber", dormRoomNumber);
                request.setAttribute("dormRoomRatingList", dormRoomRatingList);
                request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
                request.setAttribute("mainPage", "student/dormRoomRating.jsp");
                request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
            }
			
			
			
			/*response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out =null ;  
			out =response.getWriter() ;  
			out.write(new Gson().toJson(visitorRecordList));  
			out.close();*/ 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dormRoomRatingDelete(HttpServletRequest request, HttpServletResponse response) {
	    String id = request.getParameter("id");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            dormRoomRatingDao.dormRoomRatingDelete(con, id);
            request.getRequestDispatcher("dormRoomRating?action=list").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dormRoomRatingSave(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String dormBuildId = request.getParameter("dormBuildId");
		String dormRoomNumber = request.getParameter("dormRoomNumber");
		String scoreClean = request.getParameter("scoreClean");
		String scoreCulture = request.getParameter("scoreCulture");
		String scoreObey = request.getParameter("scoreObey");
		String remark = request.getParameter("remark");
		
		DormRoomRating dormRoomRating = new DormRoomRating(dormBuildId, dormRoomNumber, scoreClean, scoreCulture, scoreObey, remark);
		if(StringUtil.isNotEmpty(id)) {
			dormRoomRating.setId(id);
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int saveNum = 0;
			if(StringUtil.isNotEmpty(id)) {
				saveNum = dormRoomRatingDao.dormRoomRatingUpdate(con, dormRoomRating);
			} else {
				saveNum = dormRoomRatingDao.dormRoomRatingAdd(con, dormRoomRating);
			}
			if(saveNum > 0) {
				request.getRequestDispatcher("dormRoomRating?action=list").forward(request, response);
			} else {
				request.setAttribute("visitorRecord", dormRoomRating);
				request.setAttribute("error", "新增寝室评比失败！");
				request.setAttribute("mainPage", "admin/dormRoomRatingSave.jsp");
				request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//跳转新增访问记录
	private void dormRoomRatingPreSave(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		Object currentUserType = session.getAttribute("currentUserType");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			request.setAttribute("dormBuildList", studentDao.dormBuildList(con));
			if (StringUtil.isNotEmpty(id)) {
				DormRoomRating dormRoomRating = dormRoomRatingDao.dormRoomRatingdShow(con, id);
				request.setAttribute("dormRoomRating", dormRoomRating);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if("admin".equals((String)currentUserType)) {
			request.setAttribute("mainPage", "admin/dormRoomRatingSave.jsp");
			request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
		}else if("dormManager".equals((String)currentUserType)) {
			request.setAttribute("mainPage", "dormManager/dormRoomRatingSave.jsp");
			request.getRequestDispatcher("mainManager.jsp").forward(request, response);
		}
		
	}
	
	private String genPagation(int totalNum, int currentPage, int pageSize){
		int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='dormRoomRating?page=1'>首页</a></li>");
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'><</a></li>");
		}else {
			pageCode.append("<li><a href='dormRoomRating?page="+(currentPage-1)+"'><</a></li>");
		}
		for(int i=currentPage-2;i<=currentPage+2;i++) {
			if(i<1||i>totalPage) {
				continue;
			}
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			} else {
				pageCode.append("<li><a href='dormRoomRating?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>></a></li>");
		} else {
			pageCode.append("<li><a href='dormRoomRating?page="+(currentPage+1)+"'>></a></li>");
		}
		pageCode.append("<li><a href='dormRoomRating?page="+totalPage+"'>末页</a></li>");
		return pageCode.toString();
	}
	
}
