package com.dorm.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dorm.dao.DormBuildDao;
import com.dorm.dao.StorageGoodsDao;
import com.dorm.dao.StudentDao;
import com.dorm.dao.VisitorRecordDao;
import com.dorm.model.DormManager;
import com.dorm.model.PageBean;
import com.dorm.model.StorageGoodsModel;
import com.dorm.model.VisitorRecord;
import com.dorm.util.DbUtil;
import com.dorm.util.PropertiesUtil;
import com.dorm.util.StringUtil;

public class StorageGoodsServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	private StorageGoodsDao mStorageGoodsDao = new StorageGoodsDao();
	private StudentDao studentDao = new StudentDao();

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		//HttpSession session = request.getSession();
		Object currentUserType = session.getAttribute("currentUserType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String s_visitorRecordText = request.getParameter("s_visitorRecordText");
		String dormBuildId = request.getParameter("buildToSelect");
		String searchType = request.getParameter("searchType");
		String page = request.getParameter("page");
		String action = request.getParameter("action");
		StorageGoodsModel storageGoodsModel = new StorageGoodsModel();
		Connection conn = null;
		if("preSave".equals(action)) {
			storageGoodsPreSave(request, response);
			return;
		} else if("save".equals(action)){
			storageGoodsSave(request, response);
			return;
		} else if("change".equals(action)) {
			storageGoodsChange(request, response);
			return;
		} else if("list".equals(action)) {
		
		} else if("search".equals(action)) {
			if(StringUtil.isNotEmpty(s_visitorRecordText)) {
				if("name".equals(searchType)) {
					storageGoodsModel.setStuName(s_visitorRecordText);
				} else if("stuNum".equals(searchType)) {
					storageGoodsModel.setStuNum(s_visitorRecordText);
				}
				session.setAttribute("s_visitorRecordText", s_visitorRecordText);
				session.setAttribute("searchType", searchType);
			} else {
				session.removeAttribute("s_visitorRecordText");
				session.removeAttribute("searchType");
			}
			if(StringUtil.isNotEmpty(startDate)) {
				storageGoodsModel.setInTime(startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				storageGoodsModel.setOutTime(endDate);
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
				List<StorageGoodsModel> storageGoodsList = mStorageGoodsDao.listStorageGoodsModel(conn, pageBean, storageGoodsModel);
				int total=mStorageGoodsDao.storageGoodsCount(conn, storageGoodsModel);
				String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
				request.setAttribute("pageCode", pageCode);
				request.setAttribute("storageGoodsList", storageGoodsList);
				request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
				request.setAttribute("mainPage", "admin/storageGoodsWeb.jsp");
				request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
			} else if("dormManager".equals((String)currentUserType)) {
				List<StorageGoodsModel> storageGoodsList = mStorageGoodsDao.listStorageGoodsModel(conn, pageBean, storageGoodsModel);
				int total=mStorageGoodsDao.storageGoodsCount(conn, storageGoodsModel);
				String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
				request.setAttribute("pageCode", pageCode);
				request.setAttribute("storageGoodsList", storageGoodsList);
				request.setAttribute("dormBuildList", studentDao.dormBuildList(conn));
				request.setAttribute("mainPage", "dormManager/storageGoodsWeb.jsp");
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
	private void storageGoodsChange(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			StorageGoodsModel storageGood = mStorageGoodsDao.storageGoodsShow(conn, id);
			storageGood.setStatus("1");
			mStorageGoodsDao.storageGoodsUpdate(conn, storageGood);
			request.getRequestDispatcher("storageGoodsWeb?action=list").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void storageGoodsPreSave(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		Object currentUserType = session.getAttribute("currentUserType");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			request.setAttribute("dormBuildList", studentDao.dormBuildList(con));
			if (StringUtil.isNotEmpty(id)) {
				StorageGoodsModel storageGoodsModel = mStorageGoodsDao.storageGoodsShow(con, id);
				request.setAttribute("visitorRecord", storageGoodsModel);
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
			request.setAttribute("mainPage", "admin/storageGoodsAdd.jsp");
			request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
		}else if("dormManager".equals((String)currentUserType)) {
			request.setAttribute("mainPage", "dormManager/storageGoodsAdd.jsp");
			request.getRequestDispatcher("mainManager.jsp").forward(request, response);
		}
	}
	private void storageGoodsSave(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String stuName = request.getParameter("stuName");
		String stuNum = request.getParameter("stuNum");
		String inTime = request.getParameter("inTime");
		String outTime = request.getParameter("outTime");
		String status = request.getParameter("status");
		String remark = request.getParameter("remark");
		StorageGoodsModel storageGoodsModel = new StorageGoodsModel(stuNum, stuName, inTime, outTime, status,
				remark);
		if(StringUtil.isNotEmpty(id)) {
			storageGoodsModel.setId(id);
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int saveNum = 0;
			if(StringUtil.isNotEmpty(id)) {
				//saveNum = studentDao.studentUpdate(con, student);
			} else {
				if(StringUtil.isNotEmpty(stuNum)) {
					boolean hadStundent = studentDao.haveNameByNumber(con, stuNum);
					if(hadStundent) {
						saveNum = mStorageGoodsDao.storageGoodsAdd(con, storageGoodsModel);	
						if(saveNum > 0) {
							request.getRequestDispatcher("storageGoodsWeb?action=list").forward(request, response);
						} else {
							request.setAttribute("visitorRecord", storageGoodsModel);
							request.setAttribute("error", "保存异常！");
							request.setAttribute("mainPage", "admin/storageGoodsAdd.jsp");
							request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
						}
					}else {
						request.setAttribute("error", "该学生不存在，请检查！");
						request.setAttribute("mainPage", "admin/storageGoodsAdd.jsp");
						request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
					}
				}
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
	private String genPagation(int totalNum, int currentPage, int pageSize){
		
		int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='storageGoodsWeb?page=1'>首页</a></li>");
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'><</a></li>");
		}else {
			pageCode.append("<li><a href='storageGoodsWeb?page="+(currentPage-1)+"'><</a></li>");
		}
		for(int i=currentPage-2;i<=currentPage+2;i++) {
			if(i<1||i>totalPage) {
				continue;
			}
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			} else {
				pageCode.append("<li><a href='storageGoodsWeb?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>></a></li>");
		} else {
			pageCode.append("<li><a href='storageGoodsWeb?page="+(currentPage+1)+"'>></a></li>");
		}
		pageCode.append("<li><a href='storageGoodsWeb?page="+totalPage+"'>末页</a></li>");
		//System.out.println("zhan chaolin ：" + pageCode.toString());
		return pageCode.toString();
	}

	
}
