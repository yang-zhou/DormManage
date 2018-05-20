<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.dorm.model.Admin" %>
<%@ page import="com.dorm.model.DormManager" %>
<%@ page import="com.dorm.model.Student" %>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>宿舍管理系统</title>
<link href="${pageContext.request.contextPath}/source/style/dorm.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/source/plugs/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/source/plugs/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/source/plugs/bootstrap-table/bootstrap-table.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/source/plugs/laydate/theme/default/laydate.css" rel="stylesheet">

<script type="text/javascript" src="${pageContext.request.contextPath}/source/plugs/jquery/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/plugs/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/plugs/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/plugs/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/plugs/laydate/laydate.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
   	 	$("#DataTables_Table_0_wrapper .row-fluid").remove();
	});
</script>
<style type="text/css">
	  .bs-docs-sidenav {
    background-color: #fff;
    border-radius: 6px;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.067);
    padding: 0;
    width: 228px;
}

.bs-docs-sidenav > li > a {
    border: 1px solid #e5e5e5;
    display: block;
    padding: 8px 14px;
    margin: 0 0 -1px;
}
.bs-docs-sidenav > li:first-child > a {
    border-radius: 6px 6px 0 0;
}
.bs-docs-sidenav > li:last-child > a {
    border-radius: 0 0 6px 6px;
}
.bs-docs-sidenav > .active > a {
    border: 0 none;
    box-shadow: 1px 0 0 rgba(0, 0, 0, 0.1) inset, -1px 0 0 rgba(0, 0, 0, 0.1) inset;
    padding: 9px 15px;
    position: relative;
    text-shadow: 0 1px 0 rgba(0, 0, 0, 0.15);
    z-index: 2;
}
.bs-docs-sidenav .icon-chevron-right {
    float: right;
    margin-right: -6px;
    margin-top: 2px;
    opacity: 0.25;
}
.bs-docs-sidenav > li > a:hover {
    background-color: #f5f5f5;
}
.bs-docs-sidenav a:hover .icon-chevron-right {
    opacity: 0.5;
}
.bs-docs-sidenav .active .icon-chevron-right, .bs-docs-sidenav .active a:hover .icon-chevron-right {
    opacity: 1;
}
.nav{
	margin-top: 50px
}
.col-sm-10{
	margin-top: 50px
}
</style>

</head>
<body>
<div class="container-fluid" style="padding-right: 0px;padding-left: 0px;">
	<div style="height: 100px;background-image: url('source/images/bg.jpg')">
		<div align="left" style="width: 80%;height:100px ;float: left;padding-top: 40px;padding-left: 30px;" ><font color="white" size="6" >宿舍管理系统</font></div>
		<div style="padding-top: 70px;padding-right: 20px;"><font size="4px" color="aliceblue" >当前用户：&nbsp;${currentUser.userName }</font></div>
	</div>
</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2 bs-docs-sidebar" >
				<ul class="nav nav-list bs-docs-sidenav">
					<li><a href="blank"><i class="icon-chevron-right"></i>首页</a></li>
					<li><a href="student?action=list"><i class="icon-chevron-right"></i>学生管理</a></li>
					<li><a href="dormRoom?action=list"><i class="icon-chevron-right"></i>寝室管理</a></li>
					<li><a href="dormRoomRating?action=list"><i class="icon-chevron-right"></i>寝室评比</a></li>
					<li><a href="visitorRecord?action=list"><i class="icon-chevron-right"></i>外来访问记录</a></li>
					<li><a href="storageGoodsWeb?action=list"><i class="icon-chevron-right"></i>物品寄放管理</a></li>
					<!-- <li><a href="record?action=list"><i class="icon-chevron-right"></i>缺勤记录</a></li> -->
					<li><a href="password?action=preChange"><i class="icon-chevron-right"></i>修改密码</a></li>
					<li><a href="login.jsp"><i class="icon-chevron-right"></i>退出系统</a></li>
				</ul>
			</div>
					<div class="col-sm-10">
						<jsp:include page="${mainPage==null?'admin/blank.jsp':mainPage}"></jsp:include>
					</div>
			</div>
		</div>
</body>
</html>