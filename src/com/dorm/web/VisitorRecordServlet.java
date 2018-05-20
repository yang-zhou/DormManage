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
import com.dorm.dao.StudentDao;
import com.dorm.dao.VisitorRecordDao;
import com.dorm.model.DormBuild;
import com.dorm.model.DormManager;
import com.dorm.model.PageBean;
import com.dorm.model.Student;
import com.dorm.model.VisitorRecord;
import com.dorm.util.DbUtil;
import com.dorm.util.PropertiesUtil;
import com.dorm.util.StringUtil;
import com.google.gson.Gson;

public class VisitorRecordServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	private VisitorRecordDao visitorRecordDao = new VisitorRecordDao();
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
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String s_visitorRecordText = request.getParameter("s_visitorRecordText");
		String dormBuildId = request.getParameter("buildToSelect");
		String searchType = request.getParameter("searchType");
		String page = request.getParameter("page");
		String action = request.getParameter("action");
		VisitorRecord visitorRecord = new VisitorRecord();
		Connection conn = null;
		if("preSave".equals(action)) {
			visitorRecordPreSave(request, response);
			return;
		} else if("save".equals(action)){
			visitorRecordSave(request, response);
			return;
		} else if("delete".equals(action)){
		    visitorRecordDelete(request, response);
            return;
        }  else if("list".equals(action)) {
		
		} else if("search".equals(action)) {
			if(StringUtil.isNotEmpty(s_visitorRecordText)) {
				if("name".equals(searchType)) {
					visitorRecord.setVisName(s_visitorRecordText);
				} else if("dormRoom".equals(searchType)) {
					visitorRecord.setVisDormBuildRoom(s_visitorRecordText);
				}
				session.setAttribute("s_visitorRecordText", s_visitorRecordText);
				session.setAttribute("searchType", searchType);
			} else {
				session.removeAttribute("s_visitorRecordText");
				session.removeAttribute("searchType");
			}
			if(StringUtil.isNotEmpty(dormBuildId)) {
				visitorRecord.setVisDormBuild(dormBuildId);
				session.setAttribute("buildToSelect", dormBuildId);
			}else {
				session.removeAttribute("buildToSelect");
			}
			if(StringUtil.isNotEmpty(startDate)) {
				visitorRecord.setVisInTime(startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				visitorRecord.setVisOutTime(endDate);
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
				List<VisitorRecord> visitorRecordList = visitorRecordDao.listVisitorRecord(conn, pageBean, visitorRecord);
				int total=visitorRecordDao.visitorRecordCount(conn, visitorRecord);
				String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
				request.setAttribute("pageCode", pageCode);
				request.setAttribute("visitorRecordList", visitorRecordList);
				request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
				request.setAttribute("mainPage", "admin/visitorRecord.jsp");
				request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
			} else if("dormManager".equals((String)currentUserType)) {
				DormManager manager = (DormManager)(session.getAttribute("currentUser"));
				int buildId = manager.getDormBuildId();
				String buildName = DormBuildDao.dormBuildName(conn, buildId);
				visitorRecord.setVisDormBuild(buildId+"");
				//List<VisitorRecord> visitorRecordList = visitorRecordDao.visitorRecordWithBuild(conn, visitorRecord, buildId);
				List<VisitorRecord> visitorRecordList = visitorRecordDao.listVisitorRecord(conn, pageBean, visitorRecord);
				int total=visitorRecordDao.visitorRecordCount(conn, visitorRecord);
                String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
                request.setAttribute("pageCode", pageCode);
				request.setAttribute("dormBuildName", buildName);
				request.setAttribute("visitorRecordList", visitorRecordList);
				request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
				request.setAttribute("mainPage", "dormManager/visitorRecord.jsp");
				request.getRequestDispatcher("mainManager.jsp").forward(request, response);
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
	
	private void visitorRecordDelete(HttpServletRequest request, HttpServletResponse response) {
	    String id = request.getParameter("id");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            visitorRecordDao.visitorRecordDelete(con, id);
            request.getRequestDispatcher("visitorRecord?action=list").forward(request, response);
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

    private void visitorRecordSave(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String visName = request.getParameter("visName");
		String visSex = request.getParameter("visSex");
		String visIdCard = request.getParameter("visIdCard");
		String visPhone = request.getParameter("visPhone");
		String visDormBuild = request.getParameter("visDormBuild");
		String visDormBuildName = request.getParameter("visDormBuildName");
		String visDormBuildRoom = request.getParameter("visDormBuildRoom");
		String visInTime = request.getParameter("visInTime");
		String visOutTime = request.getParameter("visOutTime");
		String checkedId = request.getParameter("checkedId");
		String remark = request.getParameter("remark");
		
		VisitorRecord visitorRecord = new VisitorRecord(visName, visSex, visIdCard, visPhone, visDormBuild,
				visDormBuildName, visDormBuildRoom, visInTime, visOutTime, checkedId, remark);
		if(StringUtil.isNotEmpty(id)) {
			visitorRecord.setId(id);
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int saveNum = 0;
			if(StringUtil.isNotEmpty(id)) {
				//saveNum = studentDao.studentUpdate(con, student);
			} else {
				saveNum = visitorRecordDao.visitorRecordAdd(con, visitorRecord);
			}
			if(saveNum > 0) {
				request.getRequestDispatcher("visitorRecord?action=list").forward(request, response);
			} else {
				request.setAttribute("visitorRecord", visitorRecord);
				request.setAttribute("error", "新增访问记录失败！");
				request.setAttribute("mainPage", "admin/visitorRecordSave.jsp");
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
	private void visitorRecordPreSave(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		Object currentUserType = session.getAttribute("currentUserType");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			request.setAttribute("dormBuildList", studentDao.dormBuildList(con));
			if (StringUtil.isNotEmpty(id)) {
				VisitorRecord visitorRecord = visitorRecordDao.visitorRecordShow(con, id);
				request.setAttribute("visitorRecord", visitorRecord);
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
			request.setAttribute("mainPage", "admin/visitorRecordSave.jsp");
			request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
		}else if("dormManager".equals((String)currentUserType)) {
			request.setAttribute("mainPage", "dormManager/visitorRecordSave.jsp");
			request.getRequestDispatcher("mainManager.jsp").forward(request, response);
		}
		
	}
	
	private String genPagation(int totalNum, int currentPage, int pageSize){
		int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='visitorRecord?page=1'>首页</a></li>");
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'><</a></li>");
		}else {
			pageCode.append("<li><a href='visitorRecord?page="+(currentPage-1)+"'><</a></li>");
		}
		for(int i=currentPage-2;i<=currentPage+2;i++) {
			if(i<1||i>totalPage) {
				continue;
			}
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			} else {
				pageCode.append("<li><a href='visitorRecord?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>></a></li>");
		} else {
			pageCode.append("<li><a href='visitorRecord?page="+(currentPage+1)+"'>></a></li>");
		}
		pageCode.append("<li><a href='visitorRecord?page="+totalPage+"'>末页</a></li>");
		return pageCode.toString();
	}
	
}
