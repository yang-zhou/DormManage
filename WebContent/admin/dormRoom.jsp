<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function dormRoomDelete(id) {
		if(confirm("您确定要删除这个宿舍吗？")) {
			window.location="dormRoom?action=delete&id="+id;
		}
	}
	$(document).ready(function(){
		$("ul li:eq(4)").addClass("active");
		$("ul li:eq(4)").css("background-color","lightblue");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
			寝室管理
		</div>
		<form name="myForm" class="form-search" method="post" action="dormRoom?action=search" style="padding-bottom: 0px">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='dormRoom?action=preSave'">添加</button>
				<span class="data_search">
					<select id="buildToSelect" name="buildToSelect" style="width: 110px;">
					<option value="">全部宿舍楼</option>
					<c:forEach var="dormBuild" items="${dormBuildList }">
						<option value="${dormBuild.dormBuildId }" ${buildToSelect==dormBuild.dormBuildId?'selected':'' }>${dormBuild.dormBuildName }</option>
					</c:forEach>
					</select>
					<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-striped table-bordered table-hover datatable">
				<thead>
					<tr>
						<th>编号</th>
						<th>宿舍楼</th>
						<th>寝室号</th>
						<th>寝室名</th>
						<th>寝室电话</th>
						<th>简介</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach  varStatus="i" var="dormRoom" items="${dormRoomList }">
					<tr>
						<td>${i.count+(page-1)*pageSize }</td>
						<td>${dormRoom.dormBuildName }</td>
						<td>${dormRoom.dormRoomNumber }</td>
						<td>${dormRoom.dormRoomName }</td>
						<td>${dormRoom.dormRoomTel }</td>
						<%-- <td>${dormRoom.dormRoomMax }</td> --%>
						<td>${dormRoom.remark==null||dormRoom.remark==""?"无":dormRoom.remark }</td>
						<td>
							<button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='dormRoom?action=preSave&id=${dormRoom.id }'">修改</button>&nbsp;
							<button class="btn btn-mini btn-danger" type="button" onclick="dormRoomDelete(${dormRoom.id})">删除</button>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
		<%-- <div class="pagination pagination-centered">
			<ul>
				${pageCode }
			</ul>
		</div> --%>
		<nav aria-label="Page navigation">
		  <ul class="pagination">
		  	${pageCode }
		  </ul>
		</nav>
</div>