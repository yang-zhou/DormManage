package com.lero.web;

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

import com.google.gson.Gson;
import com.lero.dao.DormBuildDao;
import com.lero.dao.DormRoomDao;
import com.lero.dao.StudentDao;
import com.lero.dao.VisitorRecordDao;
import com.lero.model.DormBuild;
import com.lero.model.DormManager;
import com.lero.model.DormRoom;
import com.lero.model.PageBean;
import com.lero.model.Student;
import com.lero.model.VisitorRecord;
import com.lero.util.DbUtil;
import com.lero.util.PropertiesUtil;
import com.lero.util.StringUtil;

public class DormRoomServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	private DormRoomDao dormRoomDao = new DormRoomDao();
	private DormBuildDao dormBuildDao = new DormBuildDao();
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
		response.setCharacterEncoding("UTF-8");    
		response.setContentType("application/json; charset=utf-8");
		HttpSession session = request.getSession();
		
		Object currentUserType = session.getAttribute("currentUserType");
		String s_dormRoomText = request.getParameter("s_dormRoomText");
		String dormBuildId = request.getParameter("buildToSelect");
		String searchType = request.getParameter("searchType");
		String page = request.getParameter("page");
		String action = request.getParameter("action");
		DormRoom dormRoom = new DormRoom();
		Connection conn = null;
		if("preSave".equals(action)) {
			dormRoomPreSave(request, response);
			return;
		} else if("save".equals(action)){
			dormRoomSave(request, response);
			return;
		} else if("list".equals(action)) {
		
		} else if("search".equals(action)) {
			/*if(StringUtil.isNotEmpty(s_visitorRecordText)) {
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
			}*/
			if(StringUtil.isNotEmpty(dormBuildId)) {
				dormRoom.setDormBuildId(dormBuildId);
				session.setAttribute("buildToSelect", dormBuildId);
			}else {
				session.removeAttribute("buildToSelect");
			}
		} else if("nopagesearch".equals(action)) {
			if(StringUtil.isNotEmpty(dormBuildId)) {
				dormRoom.setDormBuildId(dormBuildId);
				session.setAttribute("buildToSelect", dormBuildId);
			}else {
				session.removeAttribute("buildToSelect");
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
				List<DormRoom> dormRoomList = dormRoomDao.dormRoomList(conn, pageBean,dormRoom);
				int total=dormRoomDao.dormRoomCount(conn, dormRoom);
				String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
				request.setAttribute("pageCode", pageCode);
				request.setAttribute("dormRoomList", dormRoomList);
				request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
				request.setAttribute("mainPage", "admin/dormRoom.jsp");
				if("nopagesearch".equals(action)) {  
					PrintWriter writer = response.getWriter();
					writer.write(new Gson().toJson(dormRoomList));
				}else if("search".equals(action) || "list".equals(action) || Integer.parseInt(page) > 0) {
					request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
				}
			} else if("dormManager".equals((String)currentUserType)) {
				DormManager manager = (DormManager)(session.getAttribute("currentUser"));
				int buildId = manager.getDormBuildId();
				String buildName = DormBuildDao.dormBuildName(conn, buildId);
				List<DormRoom> dormRoomList = dormRoomDao.visitorRecordWithBuild(conn, dormRoom, buildId);
				request.setAttribute("dormBuildName", buildName);
				request.setAttribute("dormRoomList", dormRoomList);
				request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
				request.setAttribute("mainPage", "dormManager/dormRoom.jsp");
				if("nopagesearch".equals(action)) {  
					PrintWriter writer = response.getWriter();
					writer.write(new Gson().toJson(dormRoomList));
				}else if("search".equals(action) || "list".equals(action)) {
					request.getRequestDispatcher("mainManager.jsp").forward(request, response);
				}
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
	
	private void dormRoomSave(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String dormBuildId = request.getParameter("dormBuildId");
		String dormBuildName = request.getParameter("dormBuildName");
		String dormRoomNumber = request.getParameter("dormRoomNumber");
		String dormRoomName = request.getParameter("dormRoomName");
		String dormRoomTel = request.getParameter("dormRoomTel");
		String dormRoomMax = request.getParameter("dormRoomMax");
		String remark = request.getParameter("remark");
		
		DormRoom dormRoom = new DormRoom(dormBuildId,dormBuildName, dormRoomNumber, dormRoomName, dormRoomTel, dormRoomMax, remark);
		if(StringUtil.isNotEmpty(id)) {
			dormRoom.setId(id);
		}
		Connection con = null;		
		try {
			con = dbUtil.getCon();
			int saveNum = 0;
			if(StringUtil.isNotEmpty(dormBuildId)) {
				DormBuild dormBuild = dormBuildDao.dormBuildShow(con, dormBuildId);
				dormBuildName = dormBuild.getDormBuildName();
				dormRoom.setDormBuildName(dormBuildName);
			}
			if(StringUtil.isNotEmpty(id)) {
				//saveNum = studentDao.studentUpdate(con, student);
			} else {
				saveNum = dormRoomDao.dormRoomAdd(con, dormRoom);
			}
			if(saveNum > 0) {
				request.getRequestDispatcher("dormRoom?action=list").forward(request, response);
			} else {
				request.setAttribute("dormRoom", dormRoom);
				request.setAttribute("error", "保存异常！");
				request.setAttribute("mainPage", "admin/dormRoomSave.jsp");
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
	private void dormRoomPreSave(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		Object currentUserType = session.getAttribute("currentUserType");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			request.setAttribute("dormBuildList", studentDao.dormBuildList(con));
			if (StringUtil.isNotEmpty(id)) {
				DormRoom dormRoom = dormRoomDao.dormRoomShow(con, id);
				request.setAttribute("dormRoom", dormRoom);
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
			request.setAttribute("mainPage", "admin/dormRoomSave.jsp");
			request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
		}else if("dormManager".equals((String)currentUserType)) {
			request.setAttribute("mainPage", "dormManager/dormRoomSave.jsp");
			request.getRequestDispatcher("mainManager.jsp").forward(request, response);
		}
		
	}
	
	private String genPagation(int totalNum, int currentPage, int pageSize){
		int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='dormRoom?page=1'>首页</a></li>");
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'><</a></li>");
		}else {
			pageCode.append("<li><a href='dormRoom?page="+(currentPage-1)+"'><</a></li>");
		}
		for(int i=currentPage-2;i<=currentPage+2;i++) {
			if(i<1||i>totalPage) {
				continue;
			}
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			} else {
				pageCode.append("<li><a href='dormRoom?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>></a></li>");
		} else {
			pageCode.append("<li><a href='dormRoom?page="+(currentPage+1)+"'>></a></li>");
		}
		pageCode.append("<li><a href='dormRoom?page="+totalPage+"'>末页</a></li>");
		return pageCode.toString();
	}
	
}
