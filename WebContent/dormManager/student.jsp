<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
function studentCancel(studentId) {
	if(confirm("您确定要注销这个学生吗？")) {
		window.location="student?action=delete&studentId="+studentId;
	}
}
$(document).ready(function(){
	$("ul li:eq(1)").addClass("active");
	$("ul li:eq(1)").css("background-color","lightblue");
});

window.onload = function(){ 
	$("#DataTables_Table_0_wrapper .row-fluid").remove();
};
</script>
<style type="text/css">
	.span6 {
		width:0px;
		height: 0px;
		padding-top: 0px;
		padding-bottom: 0px;
		margin-top: 0px;
		margin-bottom: 0px;
	}

</style>
<div class="data_list">
		<div class="data_list_title">
			学生管理
		</div>
		<form name="myForm" class="form-search" method="post" action="student?action=search" style="padding-bottom: 0px">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='student?action=preSave'">添加</button>
				<span class="data_search">
					<span><font style="font-family: '黑体'; font-style: 'bold'; font-size: 20px" color="blue">${dormBuildName }&nbsp;&nbsp;</font></span>
					<select id="searchType" name="searchType" style="width: 80px;">
					<option value="name">姓名</option>
					<option value="number" ${searchType eq "number"?'selected':'' }>学号</option>
					<option value="dorm" ${searchType eq "dorm"?'selected':'' }>寝室</option>
					</select>
					&nbsp;<input id="s_studentText" name="s_studentText" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_studentText }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-striped table-bordered table-hover datatable">
				<thead>
					<tr>
					<th>编号</th>
					<th>学号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>宿舍楼</th>
					<th>寝室</th>
					<th>电话</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach  varStatus="i" var="student" items="${studentList }">
					<tr>
						<td>${i.count+(page-1)*pageSize }</td>
						<td>${student.userName }</td>
						<td>${student.name }</td>
						<td>${student.sex }</td>
						<td>${student.dormBuildName==null?"无":student.dormBuildName }</td>
						<td>${student.dormName }</td>
						<td>${student.tel }</td>
						<td>
							<button class="btn btn-mini btn-warning" type="button" onclick="studentCancel(${student.studentId })">注销</button></td>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
		<nav aria-label="Page navigation">
		  <ul class="pagination">
		  	${pageCode }
		  </ul>
		</nav>
</div>